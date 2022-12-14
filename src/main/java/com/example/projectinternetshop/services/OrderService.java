package com.example.projectinternetshop.services;

import com.example.projectinternetshop.repositories.OrderRepository;
import com.example.projectinternetshop.models.Order;
import com.example.projectinternetshop.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class OrderService {

    private OrderRepository orderRepository;
    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> allOrderPerson(Person person) {
        return orderRepository.findByPerson(person);
    }

    @Transactional
    public void addOrders(Order order) {
        orderRepository.save(order);
    }

    public List selectNumberOrderGroubBy(int idPerson) {
        return orderRepository.findByAllSelectNUmber(idPerson);
    }

}
