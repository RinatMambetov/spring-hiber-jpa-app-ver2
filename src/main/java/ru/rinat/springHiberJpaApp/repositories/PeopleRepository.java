package ru.rinat.springHiberJpaApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.rinat.springHiberJpaApp.models.Person;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
}
