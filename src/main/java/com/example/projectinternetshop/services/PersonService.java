package com.example.projectinternetshop.services;

import com.example.projectinternetshop.repositories.PersonRepository;
import com.example.projectinternetshop.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class PersonService {
    private final PersonRepository personRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public PersonService(PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Person> getAll() {
        return personRepository.findAll();
    }

    public Person getPersonFindByLogin(Person person) {
        Optional<Person> person_db = personRepository.findByLogin(person.getLogin());
        return person_db.orElse(null);
    }

    public Person getPersonFinfById(int id) {
        return personRepository.findById(id).orElse(null);
    }

    @Transactional
    public void register(Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        person.setRole("ROLE_USER");
        personRepository.save(person);
    }

    @Transactional
    public void updatePersonNameAndRole(int id, String name, String role) {
        Person person = getPersonFinfById(id);

        if (name != null) {
            person.setName(name);
        }
        if (role != null) {
            person.setRole(role);
        }
        personRepository.save(person);
    }
}
