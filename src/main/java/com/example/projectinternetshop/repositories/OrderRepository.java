package com.example.projectinternetshop.repositories;

import com.example.projectinternetshop.models.Order;
import com.example.projectinternetshop.models.OrdersTitle;
import com.example.projectinternetshop.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByPerson(Person person);

    @Query(value = "select number from orders where person_id=?1 group by number", nativeQuery = true)
    List findByAllSelectNUmber(int idPerson);

//    @Query(value = "select distinct a.person_id, a.number from (select person_id, number, date_time from orders group by person_id, date_time, number order by date_time desc , number desc) a", nativeQuery = true)
//    List[] findByAllSelectNUmber();

    @Query(value = "select person_id, number, date_time from orders group by person_id, date_time, number order by date_time desc , number desc", nativeQuery = true)
    List[] findByAllSelectNUmber();
}
