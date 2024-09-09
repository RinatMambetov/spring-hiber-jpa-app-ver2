package ru.rinat.springHiberJpaApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rinat.springHiberJpaApp.models.Item;
import ru.rinat.springHiberJpaApp.models.Person;

import java.util.List;

@Repository
public interface ItemsRepository extends JpaRepository<Item, Integer> {
    List<Item> findByItemName(String itemName);

    List<Item> findByPerson(Person person);
}
