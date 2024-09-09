package ru.rinat.springHiberJpaApp.controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.rinat.springHiberJpaApp.models.Item;
import ru.rinat.springHiberJpaApp.models.Person;
import ru.rinat.springHiberJpaApp.services.ItemsService;
import ru.rinat.springHiberJpaApp.services.PeopleService;

import java.util.Optional;

@Controller
@RequestMapping("/items")
public class ItemsController {

    private final ItemsService itemsService;
    private final PeopleService peopleService;

    @Autowired
    public ItemsController(ItemsService itemsService, PeopleService peopleService) {
        this.itemsService = itemsService;
        this.peopleService = peopleService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("items", itemsService.findAll());
        return "items/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {
        model.addAttribute("item", itemsService.findOne(id));

        Optional<Person> itemOwner = itemsService.getOwner(id);
        if (itemOwner.isPresent()) {
            model.addAttribute("person", itemOwner.get());
        } else {
            model.addAttribute("people", peopleService.findAll());
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

        itemsService.save(item);
        return "redirect:/items";
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("item", itemsService.findOne(id));
        return "items/edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("item") @Valid Item item,
                         BindingResult bindingResult,
                         @PathVariable("id") int id) {
        if (bindingResult.hasErrors())
            return "items/edit";

        itemsService.update(id, item);
        return "redirect:/items";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id) {
        itemsService.delete(id);
        return "redirect:/items";
    }

    @PatchMapping("/{id}/release")
    public String release(@PathVariable("id") int id) {
        itemsService.release(id);
        return "redirect:/items/" + id;
    }

    @PatchMapping("/{id}/assign")
    public String assign(@PathVariable("id") int id, @ModelAttribute("person") Person person) {
        itemsService.assign(id, person);
        return "redirect:/items/" + id;
    }
}
