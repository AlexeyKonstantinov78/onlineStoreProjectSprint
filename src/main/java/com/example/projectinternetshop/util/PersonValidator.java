package com.example.projectinternetshop.util;

import com.example.projectinternetshop.models.Person;
import com.example.projectinternetshop.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class PersonValidator implements Validator {
    private final PersonService personService;
    @Autowired
    public PersonValidator(PersonService personService) {
        this.personService = personService;
    }

    //указываем для кого указан валидатор
    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    //позваляет работать с валидацией
    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;
        // Усли метод вернул не нуль значит логин уже есть
        if(personService.getPersonFindByLogin(person) != null) {
            errors.rejectValue("login", "", "Логин занят");

        }
    }
}
