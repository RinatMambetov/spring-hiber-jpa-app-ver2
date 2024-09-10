package ru.rinat.springHiberJpaApp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.rinat.springHiberJpaApp.models.Item;
import ru.rinat.springHiberJpaApp.models.Mood;
import ru.rinat.springHiberJpaApp.models.Person;
import ru.rinat.springHiberJpaApp.repositories.PeopleRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PeopleService {
    private final PeopleRepository peopleRepository;

    @Autowired
    public PeopleService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    public List<Person> findAll() {
        return peopleRepository.findAll();
    }

    public Person findOne(int id) {
        Optional<Person> person = peopleRepository.findById(id);
//        this because person fetch is lazy
        if (person.isPresent()) {
            List<Item> items = person.get().getItems();
            System.out.println(items);
        }
        return person.orElse(null);
    }

    public List<Person> findByEmail(String name) {
        return peopleRepository.findByEmail(name);
    }

    @Transactional
    public void save(Person person) {
        person.setMood(Mood.HAPPY);
        person.setCreatedAt(new Date());
        peopleRepository.save(person);
    }

    @Transactional
    public void update(int id, Person person) {
        person.setId(id);
        person.setCreatedAt(new Date());
        peopleRepository.save(person);
    }

    @Transactional
    public void delete(int id) {
        peopleRepository.deleteById(id);
    }
}
