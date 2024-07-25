package dasturlash.uz;

import dasturlash.uz.DTO.StudentDTO;
import dasturlash.uz.config.ApplicationConfig;
import dasturlash.uz.repository.StudentRepository;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDateTime;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApplicationConfig.class);
        StudentRepository studentRepository = context.getBean(StudentRepository.class);
        studentRepository.createTabel();

        // Save some students
        StudentDTO student1 = new StudentDTO("Ali", "Aliyev", LocalDateTime.now());
        StudentDTO student2 = new StudentDTO("Vali", "Valiyev", LocalDateTime.now());
        studentRepository.save(student1);
        studentRepository.save(student2);

        // List all students
        List<StudentDTO> studentList = studentRepository.getStudentList();
        studentList.forEach(System.out::println);

        // Update a student
        StudentDTO updatedStudent = new StudentDTO("Ramziddin", "Rustamov", LocalDateTime.now());
        studentRepository.updateStudent(1, updatedStudent);

        // List all students after update
        List<StudentDTO> updatedList = studentRepository.getStudentList();
        updatedList.forEach(System.out::println);
        // delete student worked
        studentRepository.deleteStudent(1);

        // get counted students List
        Long counted =  studentRepository.getStudentCount();
        System.out.println("All of the students  " + counted);

        Long names =  studentRepository.getStudentCountWhereNameEquals("Ramziddin");
        System.out.println("All of the students  " + names);



    }
}
