package Project1.dao;

import Project1.Mappers.BookMapper;
import Project1.Mappers.PersonMapper;
import Project1.models.Book;
import Project1.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> showBooks() {
        return jdbcTemplate.query("SELECT * FROM Book ORDER BY book_id", new BookMapper());
    }

    public Book showBook(int id) {
        return jdbcTemplate.query("SELECT * FROM Book WHERE book_id = ?", new Object[]{id}, new BookMapper())
                .stream().findAny().orElse(null);
    }

    public void addBook(Book book) {
        jdbcTemplate.update("INSERT INTO Book(person_id, name, author, year) VALUES(null, ?, ?, ?)", book.getName(),
                book.getAuthor(), book.getYear());
    }

    public void editBook(Book book, int id) {
        jdbcTemplate.update("UPDATE Book SET name = ?, author = ?, year = ? WHERE book_id = ?", book.getName(),
                book.getAuthor(), book.getYear(), id);
    }

    public Person checkBookOwner(int id) {
        return jdbcTemplate.query("SELECT * FROM Person JOIN Book ON person.person_id = book.person_id WHERE book.book_id = ?",
                new Object[]{id}, new PersonMapper()).stream().findAny().orElse(null);
    }

    public void deleteBook(int id) {
        jdbcTemplate.update("DELETE FROM Book WHERE book_id = ?", id);
    }

    public void deleteBookOwner(int id) {
        jdbcTemplate.update("UPDATE book set person_id = null where book_id = ?", id);
    }

    public List<Person> getPeople() {
        return jdbcTemplate.query("SELECT * FROM person ORDER BY person_id", new PersonMapper());
    }

    public void assignBookToPerson(int person_id, int book_id) {
        jdbcTemplate.update("UPDATE book SET person_id = ? where book_id = ?", person_id, book_id);
    }
}
