package ru.rinat.springHiberJpaApp.dao;

import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.rinat.springHiberJpaApp.models.Person;

import java.util.List;

@Component
public class PersonDAO {
    private final EntityManager em;

    @Autowired
    public PersonDAO(EntityManager em) {
        this.em = em;
    }

    @Transactional(readOnly = true)
    public void testNPlus1() {
        Session session = em.unwrap(Session.class);

//        1 query
//        List<Person> people = session.createQuery("select p from Person p", Person.class).getResultList();

//        N queries to DB
//        for (Person person : people) {
//            System.out.println("Person: " + person.getName() + " has: " + person.getItems());
//        }

//        Solution with join tables
        List<Person> people =
                session.createQuery("select p from Person p left join fetch p.items", Person.class).getResultList();

        for (Person person : people) {
            System.out.println("Person: " + person.getName() + " has: " + person.getItems());
        }
    }
}
