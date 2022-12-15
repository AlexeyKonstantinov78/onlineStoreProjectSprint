package com.example.projectinternetshop.repositories;

import com.example.projectinternetshop.models.Person;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    // получаем запись из БД по логину
    Optional<Person> findByLogin(String login);

    @Query(value = "select * from person order by id", nativeQuery = true)
    List<Person> findAllOrderById();

    List<Person> findByOrderByIdAsc();
}
