package com.example.projectinternetshop.controllers;

import com.example.projectinternetshop.models.Product;
import com.example.projectinternetshop.models.Status;
import com.example.projectinternetshop.security.PersonDetails;
import com.example.projectinternetshop.services.CategoryService;
import com.example.projectinternetshop.services.ProductService;
import com.example.projectinternetshop.services.StatusService;
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
import java.util.List;

@Controller
@RequestMapping("admin")
//@PreAuthorize("hasAnyAuthority('ROLE_ADMIN')") // доступ только админ к контролеру 1 способ
public class AdminController {

    private final ProductValidator productValidator;
    private final ProductService productService;

    private final CategoryService categoryService;

    private final StatusService statusService;

    public AdminController(ProductValidator productValidator, ProductService productService, CategoryService categoryService, StatusService statusService) {
        this.productValidator = productValidator;
        this.productService = productService;
        this.categoryService = categoryService;
        this.statusService = statusService;
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
        model.addAttribute("person", personDetails());
        model.addAttribute("products", productService.getAllProductSortId());

        return "admin/admin";
    }

    @GetMapping("product/add")
    public String addProduct(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.findAll());
        model.addAttribute("person", personDetails());
        return "product/addProduct";
    }
    @PostMapping("product/add")
    public String addProduct(@ModelAttribute("product") @Valid Product product, BindingResult bindingResult, @RequestParam("file_one") List<MultipartFile> file_one, Model model) throws IOException {
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
        return "product/editProduct";
    }
    @PostMapping("/product/edit/{id}")
    public String editProduct(@PathVariable("id") int id, @ModelAttribute("editProduct") @Valid Product product, BindingResult bindingResult, @RequestParam("file_one") List<MultipartFile> file_one, Model model) {

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
        model.addAttribute("person", personDetails());
        return "admin/status";
    }

    @GetMapping("/status/add")
    public String addStatus(Model model) {
        model.addAttribute("statusProduct", new Status());
        model.addAttribute("person", personDetails());
        return "admin/addStatus";
    }

    @PostMapping("/status/add")
    public String saveStatus(@ModelAttribute("statusProduct") @Valid Status status, BindingResult bindingResult, Model model) throws IOException {
        model.addAttribute("person", personDetails());
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
        return "admin/editStatus";
    }

    @PostMapping("/status/edit/{id}")
    public String editStatus(@PathVariable("id") int id, @ModelAttribute("statusProduct") @Valid Status status, BindingResult bindingResult, Model model) throws IOException {

        model.addAttribute("person", personDetails());

        if (bindingResult.hasErrors()) {
            return "admin/editStatus";
        }
        statusService.updateStatus(id, status);

        return "redirect:/admin/status";
    }

    protected PersonDetails personDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        return personDetails;
    }
}
