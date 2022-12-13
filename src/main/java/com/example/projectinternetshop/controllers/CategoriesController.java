package com.example.projectinternetshop.controllers;

import com.example.projectinternetshop.models.Category;
import com.example.projectinternetshop.repositories.CategoryRepository;
import com.example.projectinternetshop.security.PersonDetails;
import com.example.projectinternetshop.services.CategoryService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@Controller
@RequestMapping("admin/categories")
public class CategoriesController {
    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;

    public CategoriesController(CategoryService categoryService,
                                CategoryRepository categoryRepository) {
        this.categoryService = categoryService;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("")
    public String allCategories(Model model) {
        model.addAttribute("categories_link_activ", "categories_link_activ");
        model.addAttribute("person", personDetails());
        model.addAttribute("categories", categoryService.findAll());
        return "admin/categories";
    }

    @GetMapping("/add")
    public String addCategories(Model model) {
        model.addAttribute("categories_link_activ", "categories_link_activ");
        model.addAttribute("person", personDetails());
        model.addAttribute("categories", new Category());
        return "admin/addCategories";
    }

    @PostMapping("/add")
    public String addCategories(@ModelAttribute("categories") @Valid Category category, BindingResult bindingResult, Model model) throws IOException {
        model.addAttribute("person", personDetails());
        model.addAttribute("categories_link_activ", "categories_link_activ");
        if (bindingResult.hasErrors()) {
            return "admin/addCategories";
        }

        categoryService.saveCategories(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("/edit/{id}")
    public String editCategory(@PathVariable("id") int id, Model model) {
        model.addAttribute("categories_link_activ", "categories_link_activ");
        model.addAttribute("person", personDetails());
        model.addAttribute("categories", categoryService.getCategoryById(id));
        return "admin/editCategories";
    }

    @PostMapping("/edit/{id}")
    public String editSaveCategory(@PathVariable("id") int id, @ModelAttribute("categories") @Valid Category category, BindingResult bindingResult, Model model) throws IOException {
        model.addAttribute("categories_link_activ", "categories_link_activ");
        model.addAttribute("person", personDetails());
        if (bindingResult.hasErrors()) {
            return "admin/editCategories";
        }
        categoryService.updateCategories(id, category);
        return "redirect:/admin/categories";
    }

    protected PersonDetails personDetails() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();

        return personDetails;
    }
}
