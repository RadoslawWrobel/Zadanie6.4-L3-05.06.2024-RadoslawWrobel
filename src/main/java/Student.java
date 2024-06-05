import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Student {

    private String name;
    private int age;
    private String date;

    public Student(String name, int age, String date) {
        this.name = name;
        this.age = age;
        this.date = date;
    }

    public String getName() { return name; }
    public int getAge() { return age; }
    public String getDate() { return date; }

    @Override
    public String toString() {
        return name + " " + age + " " + date;
    }

    public static Student parse(String str) throws WrongAge, WrongDateOfBirth, WrongDateFormat {
        String[] data = str.split(" ");
        if (data.length != 3) {
            throw new IllegalArgumentException("Invalid input format for student data.");
        }
        String name = data[0];
        int age = Integer.parseInt(data[1]);
        if (age < 0 || age > 100) {
            throw new WrongAge();
        }
        String date = data[2];
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
        if (month == 2 && day > 29) {
            throw new WrongDateFormat();
        }
        if ((month == 4 || month == 6 || month == 9 || month == 11) && day > 30) {
            throw new WrongDateFormat();
        }
        return new Student(name, age, date);
    }

    public static void validateStudentsData() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("db.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    parse(line);
                } catch (Exception e) {
                    System.out.println("Błąd danych w rekordzie: " + line);
                    throw new IOException("Błędne dane w pliku db.txt", e);
                }
            }
        }
    }
}
