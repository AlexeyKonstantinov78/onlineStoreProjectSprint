package com.example.projectinternetshop.controllers;

import com.example.projectinternetshop.models.*;
import com.example.projectinternetshop.repositories.ImageRepository;
import com.example.projectinternetshop.security.PersonDetails;
import com.example.projectinternetshop.services.*;
import com.example.projectinternetshop.util.ProductValidator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("admin")
//@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')") // доступ только админ к контролеру 1 способ
public class AdminController {

    private final ProductValidator productValidator;
    private final ProductService productService;

    private final CategoryService categoryService;

    private final StatusService statusService;

    private final OrderService orderService;
    private final ImageRepository imageRepository;

    private final PersonService personService;

    public AdminController(ProductValidator productValidator, ProductService productService, CategoryService categoryService, StatusService statusService, OrderService orderService,
                           ImageRepository imageRepository, PersonService personService) {
        this.productValidator = productValidator;
        this.productService = productService;
        this.categoryService = categoryService;
        this.statusService = statusService;
        this.orderService = orderService;
        this.imageRepository = imageRepository;
        this.personService = personService;
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_USER')")
    //@PreAuthorize("hasRole('ROLE_ADMIN')")// анатация добавляет роли 2 способ
    @GetMapping("")
    public String admin(Model model) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        // преобразовать в класс по работе с пользоователем
//
//        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
//
//        System.err.println("ID пользователя: " + personDetails.getPerson().getId());
//        System.out.println("Логин пользователя: " + personDetails.getPerson().getLogin());
//        System.out.println("Пароль пользователя: " + personDetails.getPerson().getPassword());
        String role = personDetails().getPerson().getRole();
        if (role.equals("ROLE_USER")) {
            return "redirect:/index";
        }
        model.addAttribute("products_link_activ", "products_link_activ");
        model.addAttribute("person", personDetails());
        model.addAttribute("products", productService.getAllProductSortId());

        return "admin/admin";
    }

    @GetMapping("product/add")
    public String addProduct(Model model) {
        model.addAttribute("products_link_activ", "products_link_activ");
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("person", personDetails());
        return "product/addProduct";
    }
    @PostMapping("product/add")
    public String addProduct(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult, @RequestParam("file_one") List<MultipartFile> file_one, Model model) throws IOException {
        model.addAttribute("person", personDetails());
        model.addAttribute("products_link_activ", "products_link_activ");
        productValidator.validate(product, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            return "product/addProduct";
        }

        productService.saveProduct(product);
        productService.addImageProduct(file_one, product);
        return  "redirect:/admin";
    }

    @GetMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable("id") int id) {
        productService.deleteProduct(id);
        return  "redirect:/admin";
    }

    @GetMapping("/product/edit/{id}")
    public String editProduct(@PathVariable("id") int id, Model model) {
        model.addAttribute("editProduct", productService.getProductById(id));
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("person", personDetails());
        model.addAttribute("products_link_activ", "products_link_activ");
        return "product/editProduct";
    }
    @PostMapping("/product/edit/{id}")
    public String editProduct(@PathVariable("id") int id, @ModelAttribute("editProduct") @Valid Product product, BindingResult bindingResult, @RequestParam("file_one") List<MultipartFile> file_one, Model model) {
        model.addAttribute("person", personDetails());
        model.addAttribute("products_link_activ", "products_link_activ");
        if (bindingResult.hasErrors()) {
            model.addAttribute("categories", categoryService.findAll());
            return "product/editProduct";
        }
        productService.addImageProduct(file_one, product);
        productService.updateProduct(id, product);

        return "redirect:/admin";
    }
    // статус
    @GetMapping("/status")
    public String allStatus(Model model) {
        model.addAttribute("statusProduct", statusService.findAll());
        model.addAttribute("statusProduct_link_activ", "statusProduct_link_activ");
        model.addAttribute("person", personDetails());
        return "admin/status";
    }

    @GetMapping("/status/add")
    public String addStatus(Model model) {
        model.addAttribute("statusProduct", new Status());
        model.addAttribute("person", personDetails());
        model.addAttribute("statusProduct_link_activ", "statusProduct_link_activ");
        return "admin/addStatus";
    }

    @PostMapping("/status/add")
    public String saveStatus(@ModelAttribute("statusProduct") @Valid Status status, BindingResult bindingResult, Model model) throws IOException {
        model.addAttribute("person", personDetails());
        model.addAttribute("statusProduct_link_activ", "statusProduct_link_activ");
        if (bindingResult.hasErrors()) {
            return "admin/addStatus";
        }
        statusService.saveStatus(status);
        return "redirect:/admin/status";
    }

    // Коректировка
    @GetMapping("/status/edit/{id}")
    public String editStatus(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDetails());
        model.addAttribute("statusProduct", statusService.getStatusById(id));
        model.addAttribute("statusProduct_link_activ", "statusProduct_link_activ");
        return "admin/editStatus";
    }

    @PostMapping("/status/edit/{id}")
    public String editStatus(@PathVariable("id") int id, @ModelAttribute("statusProduct") @Valid Status status, BindingResult bindingResult, Model model) throws IOException {
        model.addAttribute("statusProduct_link_activ", "statusProduct_link_activ");
        model.addAttribute("person", personDetails());

        if (bindingResult.hasErrors()) {
            return "admin/editStatus";
        }
        statusService.updateStatus(id, status);

        return "redirect:/admin/status";
    }

    @GetMapping("/orders")
    public String allOrders(Model model) {
        model.addAttribute("orders_link_activ", "orders_link_activ");
        List<OrdersTitle> ordersTitleList = new ArrayList<>();
        List[] list = orderService.selectNumberOrderGroubBy();

        for (List li: list) {
            String[] liSplit = li.toString().split(", ");

            int id = Integer.parseInt(liSplit[0].substring(1).trim());
            String number = liSplit[1].substring(0).trim();
            LocalDate dateTime = LocalDate.parse(liSplit[2].substring(0, 10).trim());

            if (!ordersTitleListBoolean(number, ordersTitleList)) {
                ordersTitleList.add(new OrdersTitle(id, getPerson(id), number, dateTime));
            }
        }
        List<Order> orderList = orderService.allOrder();

        model.addAttribute("ordersTitle", ordersTitleList);
        model.addAttribute("orders", orderList);
        model.addAttribute("person", personDetails());

        return "admin/ordersAdminPanel";
    }

    @GetMapping("/orders/editStatus/{id}")
    public String editStatusOrder(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDetails());
        model.addAttribute("orders_link_activ", "orders_link_activ");
        model.addAttribute("order", orderService.getOrderById(id));
        model.addAttribute("statuses", statusService.findAll());

        return "admin/ordersEdit";
    }

    @PostMapping("/orders/editStatus/{id}")
    public String updateStatusOrder(@PathVariable("id") int id, @ModelAttribute("order") Order order, @RequestParam("status") int status_id, Model model) {
        model.addAttribute("person", personDetails());
        model.addAttribute("orders_link_activ", "orders_link_activ");
        orderService.updateOrder(id, order.getStatus());

        return "redirect:/admin/orders";
    }

    protected Boolean ordersTitleListBoolean(String number,  List<OrdersTitle> ordersTitleList) {
        for (OrdersTitle list : ordersTitleList) {
            if (list.getNumber().equals(number)) {
                return true;
            }
        }
        return false;
    }

    protected PersonDetails personDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        return personDetails;
    }

    protected Person getPerson(int id) {
        List<Person> personList =  personService.getAll();
        Person person = new Person();

        for (Person per: personList) {
            if (per.getId() == id){
                person = per;
                break;
            }
        }
        
        return person;
    }
}
