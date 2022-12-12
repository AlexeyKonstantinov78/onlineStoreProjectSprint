package com.example.projectinternetshop.repositories;

import com.example.projectinternetshop.models.Order;
import com.example.projectinternetshop.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByPerson(Person person);

    @Query(value = "select number from orders group by number", nativeQuery = true)
    List findByAllSelectNUmber();
}
