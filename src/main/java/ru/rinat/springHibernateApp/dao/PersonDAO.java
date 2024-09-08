package ru.rinat.springHibernateApp.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.spi.SessionFactoryBuilderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.rinat.springHibernateApp.models.Person;

import java.util.List;

@Component
public class PersonDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public PersonDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Person> index() {
        return sessionFactory.getCurrentSession()
                .createQuery("select p from Person p", Person.class).getResultList();
    }

    @Transactional(readOnly = true)
    public Person show(int id) {
        return sessionFactory.getCurrentSession().get(Person.class, id);
    }

    @Transactional
    public void save(Person person) {
        sessionFactory.getCurrentSession().persist(person);
    }

    @Transactional
    public void update(int id, Person updatedPerson) {
        Person person = show(id);
        person = updatedPerson;
        sessionFactory.getCurrentSession().merge(person);
    }

    @Transactional
    public void delete(int id) {
        Person person = show(id);
        sessionFactory.getCurrentSession().remove(person);
    }
}
