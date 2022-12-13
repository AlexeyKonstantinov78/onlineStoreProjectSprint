package com.example.projectinternetshop.controllers;

import com.example.projectinternetshop.repositories.OrderRepository;
import com.example.projectinternetshop.models.Cart;
import com.example.projectinternetshop.models.Order;
import com.example.projectinternetshop.models.Product;
import com.example.projectinternetshop.repositories.StatusRepository;
import com.example.projectinternetshop.security.PersonDetails;
import com.example.projectinternetshop.services.CartService;
import com.example.projectinternetshop.services.OrderService;
import com.example.projectinternetshop.services.ProductService;
import com.example.projectinternetshop.services.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("orders")
public class OrdersController {

    private final OrderService orderService;
    private final CartService cartService;
    private final ProductService productService;
    private final OrderRepository orderRepository;

    private final StatusService statusService;
    private final StatusRepository statusRepository;

    @Autowired
    public OrdersController(OrderService orderService, CartService cartService, ProductService productService,
                            OrderRepository orderRepository, StatusService statusService,
                            StatusRepository statusRepository) {
        this.orderService = orderService;
        this.cartService = cartService;
        this.productService = productService;
        this.orderRepository = orderRepository;
        this.statusService = statusService;
        this.statusRepository = statusRepository;
    }

    @GetMapping("")
    public String orders(Model model) {

        List<Order> orderList = orderService.allOrderPerson(personDetails().getPerson());

        System.out.println(orderService.selectNumberOrderGroubBy().size());
        model.addAttribute("ordersTitle", orderService.selectNumberOrderGroubBy());
        model.addAttribute("orders", orderList);
        model.addAttribute("person", personDetails());

        return "orders/orders";
    }

    @GetMapping("/create")
    public String createOrder() {

        int personId = personDetails().getPerson().getId();

        List<Cart> cartList = cartService.findByPersonId(personId);
        List<Product>productList = new ArrayList<>();
        for (Cart cart: cartList) {
            productList.add(productService.getProductById(cart.getProductId()));
        }

        String uuid = UUID.randomUUID().toString();

        for (Product product: productList) {
            Order newOrder = new Order(uuid, 1, product.getPrice(), statusService.findAll().get(0), product, personDetails().getPerson());

            orderService.addOrders(newOrder);
            cartService.deleteCartById(product.getId(), personId);
        }
        return "redirect:/orders";
    }

    protected PersonDetails personDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        return personDetails;
    }

}
