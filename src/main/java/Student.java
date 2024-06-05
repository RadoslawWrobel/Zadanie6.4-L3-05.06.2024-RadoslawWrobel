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

    public static Student parse(String str) {
        String[] data = str.split(" ");
        if(data.length != 3) {
            throw new IllegalArgumentException("Invalid input format for student data.");
        }
        return new Student(data[0], Integer.parseInt(data[1]), data[2]);
    }
}
