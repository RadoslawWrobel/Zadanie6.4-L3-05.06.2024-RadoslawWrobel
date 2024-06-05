/*
Program sprawdza poprawność wpisywanego imienia. W przypadku wystąpienia spacji w imieniu, funkcja wyrzuca zdefiniowany wyjątek WrongStudentName, który jest wyłapywany w pętli głównej Commit6_0.
Poniższe zadania będą się sprowadzały do modyfikacji bazowego kodu. Proces modyfikacji ogólnie może wyglądać następująco:
• Ustalenie jaki błąd chcę się sprawdzić i wyłapać.
• Decyzja, czy użyje się własnej klasy wyjątku, czy wykorzysta już istniejące (np. Exception, IOException).
• Napisanie kodu sprawdzającego daną funkcjonalność. W przypadku warunku błędu wyrzucany będzie wyjątek: throw new WrongStudentName().
• W definicji funkcji, która zawiera kod wyrzucania wyjątku dopisuje się daną nazwę wyjątku, np. public static String ReadName() throws WrongStudentName.
• We wszystkich funkcjach, które wywołują powyższą funkcję także należy dopisać, że one wyrzucają ten wyjątek – inaczej program się nie skompiluje.
• W pętli głównej, w main’ie, w zdefiniowanym już try-catch dopisuje się Nazwę wyjątku i go obsługuje, np. wypisuje w konsoli co się stało.
*/

//Commit6_1. Na podstawie analogii do wyjątku WrongStudentName utwórz i obsłuż wyjątki WrongAge oraz WrongDateOfBirth. 
//Niepoprawny wiek – gdy jest mniejszy od 0 lub większy niż 100. Niepoprawna data urodzenia – gdy nie jest zapisana w formacie DD-MM-YYYY, np. 28-02-2023.

//Commit6_2. Sprawdzanie czy data urodzenia zawiera się w AA-BB-CCCC (dzień 1-31, miesiąc 1-12, rok 1900-2024)

import java.io.IOException;
import java.util.Scanner;
import java.io.IOException;

class WrongStudentName extends Exception { }
class WrongAge extends Exception { }
class WrongDateOfBirth extends Exception { }
class WrongDateFormat extends Exception { }

class Main {
    public static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        while(true) {
            try {
                int ex = menu();
                switch(ex) {
                    case 1: exercise1(); break;
                    case 2: exercise2(); break;
                    case 3: exercise3(); break;
                    default: return;
                }
            } catch(IOException e) {

            } catch(WrongStudentName e) 
                {
                System.out.println("Błędne imie studenta!");
            } catch(WrongAge e) 
                {
                System.out.println("Błędny wiek!");
            } catch(WrongDateOfBirth e) 
                {
                System.out.println("Błędna data urodzenia!");
            } catch(WrongDateFormat e) 
                {
                System.out.println("Błędny format daty! Sprawdź czy na pewno dzień zawiera się w 1-31, miesiąc w 1-12, rok w 1900-2024");
            }
        }
    }

    public static int menu() {
        System.out.println("Wciśnij:");
        System.out.println("1 - aby dodać studenta");
        System.out.println("2 - aby wypisać wszystkich studentów");
        System.out.println("3 - aby wyszukać studenta po imieniu");
        System.out.println("0 - aby wyjść z programu");
        return scan.nextInt();
    }

    public static String ReadName() throws WrongStudentName {
        scan.nextLine();
        System.out.println("Podaj imie: ");
        String name = scan.nextLine();
        if(name.contains(" "))
            throw new WrongStudentName();

        return name;
    }
    public static String ReadDate() throws WrongDateOfBirth, WrongDateFormat {
        scan.nextLine();
        System.out.println("Podaj datę urodzenia DD-MM-YYY");
        var date = scan.nextLine();
        if (!date.matches("\\d{2}-\\d{2}-\\d{4}")) {
            throw new WrongDateOfBirth();
        }
        String[] parts = date.split("-");
        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);

        // Sprawdzenie poprawności roku
        if (year < 1900 || year > 2024) {
            throw new WrongDateFormat();
        }

        // Sprawdzenie poprawności miesiąca
        if (month < 1 || month > 12) {
            throw new WrongDateFormat();
        }

        // Sprawdzenie poprawności dnia w zależności od miesiąca
        if (day < 1 || day > 31) {
            throw new WrongDateFormat();
        }
        if (month == 2 && day > 29) {
            throw new WrongDateFormat();
        }
        if ((month == 4 || month == 6 || month == 9 || month == 11) && day > 30) {
            throw new WrongDateFormat();
        }

        return date;
    }
    public static int ReadAge() throws WrongAge {
        System.out.println("Podaj wiek: ");
        var age = scan.nextInt();
        if (age < 0 || age > 100)
            throw new WrongAge();
        return age;
    }

    public static String SprawdzDate() throws WrongDateOfBirth, WrongDateFormat {
        scan.nextLine();
        System.out.println("Podaj datę urodzenia DD-MM-YYY");
        var date = scan.nextLine();
        if (!date.matches("\\d{2}-\\d{2}-\\d{4}")) {
            throw new WrongDateOfBirth();
        }

        String[] parts = date.split("-");
        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);

        if (year < 1900 || year > 2024 || month < 1 || month > 12 || day < 1 || day > 31) {
            throw new WrongDateFormat();
        }
        return date;
    }

    public static void exercise1() throws IOException, WrongStudentName, WrongAge, WrongDateOfBirth, WrongDateFormat {
        var name = ReadName();
        var age = ReadAge();
        var date = SprawdzDate();
        (new Service()).addStudent(new Student(name, age, date));
    }

    public static void exercise2() throws IOException {
        var students = (new Service()).getStudents();
        for(Student current : students) {
            System.out.println(current.ToString());
        }
    }

    public static void exercise3() throws IOException {
        scan.nextLine();
        System.out.println("Podaj imie: ");
        var name = scan.nextLine();
        var wanted = (new Service()).findStudentByName(name);
        if(wanted == null)
            System.out.println("Nie znaleziono...");
        else {
            System.out.println("Znaleziono: ");
            System.out.println(wanted.ToString());
        }
    }
}