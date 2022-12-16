package com.example.projectinternetshop.controllers;

import com.example.projectinternetshop.models.Order;
import com.example.projectinternetshop.models.Role;
import com.example.projectinternetshop.services.OrderService;
import com.example.projectinternetshop.services.RoleService;
import com.example.projectinternetshop.util.ProductErrorResponse;
import com.example.projectinternetshop.util.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final OrderService orderService;

    private final RoleService roleService;
    @Autowired
    public ApiController(OrderService orderService, RoleService roleService) {
        this.orderService = orderService;
        this.roleService = roleService;
    }

//    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/orders")
    public List<Order> getAllOrders() {

        return orderService.allOrder();
    }

   @ExceptionHandler
    public ResponseEntity<ProductErrorResponse> handlerException(ProductNotFoundException productNotFoundException){
        ProductErrorResponse response = new ProductErrorResponse("Не удалось найти  ");
        // NOT FOUND - 404 - статус в заголовке ответа
        // RESPONSE - тело ответа
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

}
