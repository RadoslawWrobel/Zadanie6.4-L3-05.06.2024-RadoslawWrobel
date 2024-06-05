import java.util.Collection;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Service {

    public void addStudent(Student student) throws IOException {
        var f = new FileWriter("db.txt", true);
        var b = new BufferedWriter(f);
        b.append(student.toString());
        b.newLine();
        b.close();
    }

    public Collection<Student> getStudents() throws IOException {
        var ret = new ArrayList<Student>();
        var f = new FileReader("db.txt");
        var reader = new BufferedReader(f);
        String line = "";
        while (true) {
            line = reader.readLine();
            if(line == null)
                break;
            ret.add(Student.parse(line));
        }
        reader.close();
        return ret;
    }

    public Student findStudentByName(String name) throws IOException {
        var students = this.getStudents();
        for(Student current : students) {
            if(current.getName().equals(name))
                return current;
        }
        return null;
    }
}
