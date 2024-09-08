package ru.rinat.springHibernateApp.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.rinat.springHibernateApp.dao.ItemDAO;
import ru.rinat.springHibernateApp.dao.PersonDAO;
import ru.rinat.springHibernateApp.models.Item;
import ru.rinat.springHibernateApp.models.Person;

import java.util.Optional;

@Controller
@RequestMapping("/items")
public class ItemController {

    private final ItemDAO itemDAO;
    private final PersonDAO personDAO;

    @Autowired
    public ItemController(ItemDAO itemDAO, PersonDAO personDAO) {
        this.itemDAO = itemDAO;
        this.personDAO = personDAO;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("items", itemDAO.index());
        return "items/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("item", itemDAO.show(id));

        Optional<Person> itemOwner = itemDAO.getOwner(id);
        if (itemOwner.isPresent()) {
            model.addAttribute("person", itemOwner.get());
        } else {
            model.addAttribute("people", personDAO.index());
        }

        return "items/show";
    }

    @GetMapping("/new")
    public String newItem(@ModelAttribute("item") Item item) {
        return "items/new";
    }

    @PostMapping()
    public String create(@ModelAttribute("item") @Valid Item item,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "items/new";

        itemDAO.save(item);
        return "redirect:/items";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("item", itemDAO.show(id));
        return "items/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("item") @Valid Item item,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "items/edit";

        itemDAO.update(id, item);
        return "redirect:/items";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        itemDAO.delete(id);
        return "redirect:/items";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        itemDAO.release(id);
        return "redirect:/items/" + id;
    }
    
    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
        itemDAO.assign(id, person);
        return "redirect:/items/" + id;
    }
}
