package ru.rinat.springHiberJpaApp.services;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
//        return peopleRepository.findAll();

//        pagination
//        return peopleRepository.findAll(PageRequest.of(0, 3)).getContent();

//        sorting
//        return peopleRepository.findAll(Sort.by("age"));

//        combo
        return peopleRepository.findAll(PageRequest.of(1, 6, Sort.by("age")))
                .getContent();
    }

    public Person findOne(int id) {
        Optional<Person> person = peopleRepository.findById(id);
//        this because person fetch is lazy
        person.ifPresent(value -> Hibernate.initialize(value.getItems()));
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
