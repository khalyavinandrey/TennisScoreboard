package Project1.Mappers;

import Project1.models.Person;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
        Person person = new Person();

        person.setPersonId(rs.getInt("person_id"));
        person.setFullName(rs.getString("full_name"));
        person.setYearOfBirth(rs.getInt("year_of_birth"));

        return person;
    }
}
