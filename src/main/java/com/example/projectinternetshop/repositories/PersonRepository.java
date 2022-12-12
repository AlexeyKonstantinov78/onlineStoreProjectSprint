package com.example.projectinternetshop.repositories;

import com.example.projectinternetshop.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    // получаем запись из БД по логину
    Optional<Person> findByLogin(String login);
}
