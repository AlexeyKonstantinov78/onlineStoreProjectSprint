package com.example.projectinternetshop.controllers;

import com.example.projectinternetshop.models.Cart;
import com.example.projectinternetshop.models.Product;
import com.example.projectinternetshop.security.PersonDetails;
import com.example.projectinternetshop.services.CartService;
import com.example.projectinternetshop.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class UserControler {
    private final CartService cartService;
    private final ProductService productService;
    @Autowired
    public UserControler(ProductService productService, CartService cartService) {
        this.productService = productService;
        this.cartService = cartService;
    }

    @GetMapping("index")
    public String index(Model model) {

        // Получаем объект футентификации -> с помьщю SecurityContextHolder обращаемся к контексту вызываем метод аутентификации
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        // преобразовать в класс по работе с пользоователем
//        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
//        System.err.println("ID пользователя: " + personDetails.getPerson().getId());
//        System.out.println("Логин пользователя: " + personDetails.getPerson().getLogin());
//        System.out.println("Пароль пользователя: " + personDetails.getPerson().getPassword());
        String role = personDetails().getPerson().getRole();
        if (role.equals("ROLE_ADMIN")) {
            return "redirect:/admin";
        }
        model.addAttribute("person", personDetails());
        model.addAttribute("products", productService.getAllProduct());
        return "user/index";
    }

    @GetMapping("user/info/{id}")
    public String infoProduct(@PathVariable("id") int id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        model.addAttribute("person", personDetails());
        return "user/infoProduct";
    }

    // карзина
    @GetMapping("/cart")
    public String cart(Model model) {

        int personId = personDetails().getPerson().getId();
        List<Cart> cartList = cartService.findByPersonId(personId);
        List<Product>productList = new ArrayList<>();
        for (Cart cart: cartList) {
            productList.add(productService.getProductById(cart.getProductId()));
        }

        float price = 0;
        for (Product pr : productList) {
            price += pr.getPrice();
        }
        model.addAttribute("person", personDetails());
        model.addAttribute("cart", productList);
        model.addAttribute("totalPrice", price);
        return "cart/cart";
    }

    // добавляем товар в карзину
    @GetMapping("/cart/add/{id}")
    public String addProductInCart(@PathVariable("id") int id, Model model) {
        Product product = productService.getProductById(id);

        int personId = personDetails().getPerson().getId();
        Cart cart = new Cart(personId, product.getId());
        cartService.addCart(cart);
//        model.addAttribute("cart", product);
//        model.addAttribute("person", personDetails);
        return "redirect:/cart";
    }

    @GetMapping("/cart/delete/{id}")
    public String delCartIdProduct(@PathVariable("id") int id ) {
        int personId = personDetails().getPerson().getId();

        List<Cart> cartList = cartService.findByPersonId(personId);

        for (Cart cart: cartList) {
            if (cart.getProductId() == id) {
                cartService.deleteCart(cart);
                break;
            }
        }

        return "redirect:/cart";
    }

    protected PersonDetails personDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        return personDetails;
    }
}

