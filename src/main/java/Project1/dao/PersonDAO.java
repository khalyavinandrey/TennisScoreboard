package Project1.dao;

import Project1.Mappers.BookMapper;
import Project1.Mappers.PersonMapper;
import Project1.models.Book;
import Project1.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> showPeople() {
        return jdbcTemplate.query("SELECT * FROM person ORDER BY person_id", new PersonMapper());
    }

    public Person showPerson(int id) {
        return jdbcTemplate.query("SELECT * FROM person WHERE person_id = ?", new Object[]{id},
                new PersonMapper()).stream().findAny().orElse(null);
    }

    public Optional<Person> showPerson(String name) {
        return jdbcTemplate.query("SELECT * FROM person WHERE full_name = ?", new Object[]{name},
                new PersonMapper()).stream().findAny();
    }

    public void createPerson(Person person) {
        jdbcTemplate.update("INSERT INTO Person(full_name, year_of_birth) VALUES (?, ?)", person.getFullName(), person.getYearOfBirth());
    }

    public void updatePerson(Person person, int id) {
        jdbcTemplate.update("UPDATE Person SET full_name = ?, year_of_birth = ? WHERE person_id = ?",
                person.getFullName(), person.getYearOfBirth(), id);
    }

    public void deletePerson(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE person_id = ?", id);
    }

    public List<Book> getListOfBooks(int id) {
        return jdbcTemplate.query("SELECT * FROM person JOIN book ON person.person_id = book.person_id where person.person_id = ?",
                new Object[]{id}, new BookMapper());
    }

}
