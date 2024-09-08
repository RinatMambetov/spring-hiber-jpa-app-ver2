package ru.rinat.springHibernateApp.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.rinat.springHibernateApp.models.Item;
import ru.rinat.springHibernateApp.models.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ItemDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    ItemDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Item> index() {
        return sessionFactory.getCurrentSession()
                .createQuery("select i from Item i", Item.class).getResultList();
    }

    @Transactional(readOnly = true)
    public Item show(int id) {
        return sessionFactory.getCurrentSession().get(Item.class, id);
    }

    @Transactional
    public void save(Item item) {
        sessionFactory.getCurrentSession().persist(item);
    }

    @Transactional
    public void update(int id, Item item) {
        Item oldItem = show(id);
        oldItem = item;
        sessionFactory.getCurrentSession().merge(oldItem);
    }

    @Transactional
    public void delete(int id) {
        sessionFactory.getCurrentSession().remove(show(id));
    }

    @Transactional(readOnly = true)
    public Optional<Person> getOwner(int id) {
        return Optional.ofNullable(sessionFactory.getCurrentSession().get(Item.class, id).getPerson());
    }

    @Transactional
    public void release(int id) {
        Session session = sessionFactory.getCurrentSession();
        Item item = session.get(Item.class, id);
        Person person = item.getPerson();
        person.getItems().remove(item);
        item.setPerson(null);
    }

    @Transactional
    public void assign(int id, Person person) {
        Session session = sessionFactory.getCurrentSession();
        Item item = session.get(Item.class, id);
        item.setPerson(person);
        List<Item> items = person.getItems();
        if (items == null) {
            items = new ArrayList<>();
        }
        items.add(item);
    }
}
