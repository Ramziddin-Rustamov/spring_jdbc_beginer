package dasturlash.uz.repository;

import dasturlash.uz.DTO.StudentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public class StudentRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;
     @Autowired
     private NamedParameterJdbcTemplate namedParameterJdbcTemplate; // for using :id instead of id = ?

    @Autowired
    private SimpleJdbcInsert simpleJdbcInsert;
    // Save a new student
    public void save(StudentDTO student) {
        String sql = "INSERT INTO student (name, surname, created_date) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, student.getName(), student.getSurname(), student.getCreatedDate());
    }

    public void saveWithSimpleJDBC(StudentDTO student) {


        Map<String, Object> params = new HashMap<>();
        simpleJdbcInsert.withTableName("student");
        simpleJdbcInsert.usingGeneratedKeyColumns("id");
        simpleJdbcInsert.execute(params);

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

    public Integer deleteStudent(Integer id){
        String sql = "DELETE FROM student WHERE id = ?";
       return  jdbcTemplate.update(sql,id);
    }

    public Long getStudentCount(){
        String sql = "SELECT count(*) FROM student";
        return jdbcTemplate.queryForObject(sql,Long.class);
    }

    public Long getStudentCountWhereNameEquals(String name){
        String sql = "SELECT count(*) FROM student WHERE lower(name) like ?";
        return jdbcTemplate.queryForObject(sql,Long.class,"%" + name.toLowerCase()+ "%");
    }


    public List<StudentDTO> all(String name, String surname)
    {
        name.toLowerCase();
        surname.toLowerCase();
        String sql = "SELECT id, name, surname,created_at FROM student where name LIKE ? and surname LIKE ?";
        List<StudentDTO> listOfStuents = jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(StudentDTO.class),name,surname);
        return listOfStuents;
    }

    // working with only namedParamerJdbcTemplate
    public List<StudentDTO> allGetWithNameAndSurname(String name, String surname)
    {

        String sql = "SELECT id, name, surname,created_at FROM student where name LIKE :name and surname LIKE :surname";
        Map<String, Object> params = new HashMap<>();
        params.put("name",name);
        params.put("surname",surname);
        List<StudentDTO> listOfStuents = namedParameterJdbcTemplate.query(sql,params,new BeanPropertyRowMapper<>(StudentDTO.class));
        return listOfStuents;
    }

    public void createTabel(){
        String sql = "CREATE TABEL IF NOT EXISTS student("
                +"id serial primary key,"
                + "name varchar(25),"
                + "surname varchar(25),"
                +"creayed_date timestamp);";
        jdbcTemplate.execute(sql);
    }



}
