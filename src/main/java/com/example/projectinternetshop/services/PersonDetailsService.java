package com.example.projectinternetshop.services;

import com.example.projectinternetshop.repositories.PersonRepository;
import com.example.projectinternetshop.models.Person;
import com.example.projectinternetshop.security.PersonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonDetailsService implements UserDetailsService {

    private final PersonRepository personRepository;
    @Autowired
    public PersonDetailsService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }
    // получить пльзователя для дальнейшей проверки
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // получаем пользователя из таблицы peron по логину с формы аутентификации
        Optional<Person> person =personRepository.findByLogin(username);

        if (person.isEmpty()) {
            // если пользователь не найден то пробрасываем в ошибку
            throw new UsernameNotFoundException("Пользователь не найден");
        }
        // Возращаем объект пользователя
        return new PersonDetails(person.get());

    }
}
