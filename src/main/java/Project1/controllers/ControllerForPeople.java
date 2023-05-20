package Project1.controllers;

import Project1.dao.PersonDAO;
import Project1.models.Person;
import Project1.utils.PersonValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/people")
public class ControllerForPeople {
    private final PersonDAO personDAO;
    private final PersonValidator personValidator;

    @Autowired
    public ControllerForPeople(PersonDAO personDAO, PersonValidator personValidator) {
        this.personDAO = personDAO;
        this.personValidator = personValidator;
    }

    @GetMapping("")
    public String showPeople(Model model) {
        model.addAttribute("personList", personDAO.showPeople());
        return "/people/showPeople";
    }

    @GetMapping("/{id}")
    public String showPerson(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDAO.showPerson(id));
        model.addAttribute("bookList", personDAO.getListOfBooks(id));
        System.out.println(personDAO.getListOfBooks(id).size() == 0);
        return "/people/showPersonById";
    }

    @GetMapping("/new")
    public String createPerson(Model model) {
        Person person = new Person();
        model.addAttribute("person", person);
        return "/people/createPerson";
    }

    @PostMapping()
    public String postNewPerson(@ModelAttribute("person") @Valid Person person, BindingResult bindingResult) {
        personValidator.validate(person, bindingResult);
        if (bindingResult.hasErrors()) {
            return "redirect:/people/new";
        }
        personDAO.createPerson(person);
        return "redirect:/people";
    }

    @GetMapping("/{id}/edit")
    public String updatePerson(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDAO.showPerson(id));
        return "/people/updatePerson";
    }

    @PatchMapping("/{id}")
    public String patchPerson(@ModelAttribute("person") @Valid Person person, @PathVariable("id") int id,
                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "redirect:/people/{id}";
        }

        personDAO.updatePerson(person, id);
        return "redirect:/people";
    }

    @DeleteMapping("/{id}")
    public String deletePerson(@PathVariable("id") int id) {
        personDAO.deletePerson(id);
        return "redirect:/people";
    }
}
