package ru.rinat.springHiberJpaApp.util;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.rinat.springHiberJpaApp.models.Person;
import ru.rinat.springHiberJpaApp.services.PeopleService;

@Component
public class PersonValidator implements Validator {
    private final PeopleService peopleService;

    public PersonValidator(PeopleService peopleService) {
        this.peopleService = peopleService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        if (!peopleService.findByEmail(person.getEmail()).isEmpty()) {
            errors.rejectValue("email", "email.exists", "Email already exists");
        }
    }
}
