package Project1.controllers;

import Project1.dao.BookDAO;
import Project1.models.Book;
import Project1.models.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/books")
public class ControllerForBooks {
    private final BookDAO bookDAO;

    public ControllerForBooks(BookDAO bookDAO) {
        this.bookDAO = bookDAO;
    }

    @GetMapping("")
    public String showBooks(Model model) {
        model.addAttribute("bookList", bookDAO.showBooks());
        return "/books/showBooks";
    }

    @GetMapping("/new")
    public String addBook(Model model) {
        Book book = new Book();
        model.addAttribute("book", book);
        return "/books/addBook";
    }

    @GetMapping("/{id}")
    public String showBook(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDAO.showBook(id));
        System.out.println(bookDAO.checkBookOwner(id));
        model.addAttribute("person", bookDAO.checkBookOwner(id));
        model.addAttribute("people", bookDAO.getPeople());

        System.out.println(bookDAO.getPeople());

        return "/books/showBook";
    }

    @PostMapping()
    public String postNewBook(@ModelAttribute("book") Book book) {
        bookDAO.addBook(book);
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String editBook(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDAO.showBook(id));
        return "/books/editBook";
    }

    @PatchMapping("/{id}")
    public String patchBook(@ModelAttribute("book") Book book, @PathVariable("id") int id) {
        bookDAO.editBook(book, id);
        return "redirect:/books";
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable("id") int id) {
        bookDAO.deleteBook(id);
        return "redirect:/books";
    }

    @PostMapping("/{id}")
    public String deleteBookOwner(@PathVariable("id") int id) {
        bookDAO.deleteBookOwner(id);
        return "redirect:/books/{id}";
    }

    @PostMapping("/{id}/assign")
    public String assignBook(@PathVariable("id") int book_id, int personId) {
        bookDAO.assignBookToPerson(personId, book_id);
        return "redirect:/books/{id}";
    }
}

