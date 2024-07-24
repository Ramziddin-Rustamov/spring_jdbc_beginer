package dasturlash.uz.repository;

import dasturlash.uz.DTO.StudentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StudentRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Save a new student
    public void save(StudentDTO student) {
        String sql = "INSERT INTO student (name, surname, created_date) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, student.getName(), student.getSurname(), student.getCreatedDate());
    }

    // Get list of all students
    public List<StudentDTO> getStudentList() {
        String sql = "SELECT * FROM student";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(StudentDTO.class));
    }

    // Update an existing student
    public void updateStudent(Integer id, StudentDTO studentDTO) {
        String sql = "UPDATE student SET name = ?, surname = ? WHERE id = ?";
        jdbcTemplate.update(sql, studentDTO.getName(), studentDTO.getSurname(), id);
    }

    public Integerf deleteStudent(Integer id){
        String sql = "DELETE FROM student WHERE id = ?";
       return  jdbcTemplate.update(sql,id);
    }
}
