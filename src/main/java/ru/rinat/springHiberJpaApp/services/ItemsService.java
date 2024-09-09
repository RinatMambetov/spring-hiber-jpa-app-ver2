package ru.rinat.springHiberJpaApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rinat.springHiberJpaApp.models.Item;
import ru.rinat.springHiberJpaApp.models.Person;
import ru.rinat.springHiberJpaApp.repositories.ItemsRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ItemsService {
    private final ItemsRepository itemsRepository;

    @Autowired
    public ItemsService(ItemsRepository itemsRepository) {
        this.itemsRepository = itemsRepository;
    }

    public List<Item> findAll() {
        return itemsRepository.findAll();
    }

    public Item findOne(int id) {
        return itemsRepository.findById(id).orElse(null);
    }

    @Transactional
    public Item save(Item item) {
        return itemsRepository.save(item);
    }

    @Transactional
    public void update(int id, Item item) {
        Item oldItem = findOne(id);
        Person person = oldItem.getPerson();
        item.setId(id);
        item.setPerson(person);
        itemsRepository.save(item);
    }

    @Transactional
    public void delete(int id) {
        itemsRepository.deleteById(id);
    }

    public Optional<Person> getOwner(int id) {
        return Optional.ofNullable(findOne(id).getPerson());
    }

    @Transactional
    public void release(int id) {
        Item item = findOne(id);
        Person person = item.getPerson();
        person.getItems().remove(item);
        item.setPerson(null);
        update(id, item);
    }

    @Transactional
    public void assign(int id, Person person) {
        Item item = findOne(id);
        item.setPerson(person);
        List<Item> items = person.getItems();
        if (items == null) {
            items = new ArrayList<>();
        }
        items.add(item);
        update(id, item);
    }

    public List<Item> findByItemName(String itemName) {
        return itemsRepository.findByItemName(itemName);
    }

    public List<Item> findByOwner(Person person) {
        return itemsRepository.findByPerson(person);
    }
}
