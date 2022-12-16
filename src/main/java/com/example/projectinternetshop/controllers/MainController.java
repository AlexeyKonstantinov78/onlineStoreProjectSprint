package com.example.projectinternetshop.controllers;

import com.example.projectinternetshop.services.ProductService;
import com.example.projectinternetshop.security.PersonDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("product")
public class MainController {
    private final ProductService productService;
    @Autowired
    public MainController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("")
    public String getAllPproduct(Model model) {
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
            String role = personDetails.getPerson().getRole();
            if (role.equals("ROLE_USER")) {
                return "redirect:/index";
            }
        } catch (Exception e) {
            System.out.println("Пользователь без авторизации");
        }

        model.addAttribute("products", productService.getAllProduct());

        return "product/product";
    }

    @GetMapping("/info/{id}")
    public String infoProduct(@PathVariable("id") int id, Model model) {

        model.addAttribute("product", productService.getProductById(id));
        model.addAttribute("person", personDetails());

        try {
            if (personDetails().getPerson().getRole().equals("ROLE_USER")) {
                return "redirect:/user/info/{id}";
            }
        } catch (Exception e) {
            System.out.println("пользователь не авторизован");
        }

        return "product/infoProduct";
    }

    @PostMapping("/search")
    public String productSearch(
            @RequestParam("search") String search,
            @RequestParam("ot") String ot,
            @RequestParam("do") String Do,
            @RequestParam(value = "price_sort",required = false, defaultValue = "") String priceSort,
            @RequestParam(value = "category",required = false, defaultValue = "") String category,
            Model model
    ) {
        float value_Do = 0.0F;
        System.out.println("serach: " + search);
        System.out.println("ot: " + ot);
        System.out.println("do: " + Do);
        System.out.println("priceSort: " + priceSort);
        System.out.println("category: " + category);


        if (!Do.isEmpty()) {
            value_Do = Float.parseFloat(Do);
        }

        if (!ot.isEmpty() & Do.isEmpty()) {
            value_Do = Float.MAX_VALUE;
        }

        if (ot.isEmpty() & !Do.isEmpty()) {
            ot = String.valueOf(Float.parseFloat("0"));
        }

        if (!ot.isEmpty() || !Do.isEmpty()) {
            // выбран интервал цен
            if (!priceSort.isEmpty()) {
                //  выбрана сортирвка по убыванию млм увеличению цены
                if (priceSort.equals("ascending_price")) {
                    //
                    if (category.equals("furniture")) {
                        model.addAttribute("search_products", productService.searchNameFilterOtAndDoCategorySortedPrice(search.toLowerCase().trim(), Float.parseFloat(ot), value_Do, 1));
                    } else if (category.equals("appliances")) {
                        model.addAttribute("search_products", productService.searchNameFilterOtAndDoCategorySortedPrice(search.toLowerCase().trim(), Float.parseFloat(ot), value_Do, 2));
                    } else if (category.equals("clothes")) {
                        model.addAttribute("search_products", productService.searchNameFilterOtAndDoCategorySortedPrice(search.toLowerCase().trim(), Float.parseFloat(ot), value_Do, 3));
                    } else if (category.isEmpty()) {
                        // не выбрана категория
                        model.addAttribute("search_products", productService.searchNameFilterOtAndDoSortedPriceAsc(search.toLowerCase().trim(), Float.parseFloat(ot), value_Do));
                    }
                }

                if (priceSort.equals("descending_price")) {
                    if (category.equals("furniture")) {
                        model.addAttribute("search_products", productService.searchNameFilterOtAndDoCategorySortedPriceDesc(search.toLowerCase().trim(), Float.parseFloat(ot), value_Do, 1));
                    } else if (category.equals("appliances")) {
                        model.addAttribute("search_products", productService.searchNameFilterOtAndDoCategorySortedPriceDesc(search.toLowerCase().trim(), Float.parseFloat(ot), value_Do, 2));
                    } else if (category.equals("clothes")) {
                        model.addAttribute("search_products", productService.searchNameFilterOtAndDoCategorySortedPriceDesc(search.toLowerCase().trim(), Float.parseFloat(ot), value_Do, 3));
                    } else if (category.isEmpty()) {
                        // не выбрана категория
                        model.addAttribute("search_products", productService.searchNameFilterOtAndDoSortedPriceDesc(search.toLowerCase().trim(), Float.parseFloat(ot), value_Do));
                    }
                }
            } else {
                // не выбрана сортировка цен
                if (!category.isEmpty()) {
                    if (category.equals("furniture")) {
                        model.addAttribute("search_products", productService.searchNameFilterPriceOtAndDoAndCategory(search.toLowerCase().trim(), Float.parseFloat(ot), value_Do, 1));
                    } else if (category.equals("appliances")) {
                        model.addAttribute("search_products", productService.searchNameFilterPriceOtAndDoAndCategory(search.toLowerCase().trim(), Float.parseFloat(ot), value_Do, 2));
                    } else if (category.equals("clothes")) {
                        model.addAttribute("search_products", productService.searchNameFilterPriceOtAndDoAndCategory(search.toLowerCase().trim(), Float.parseFloat(ot), value_Do, 3));
                    }
                } else {
                    // нет категорий
                    model.addAttribute("search_products", productService.searchNameFilterOtAndDo(search.toLowerCase().trim(), Float.parseFloat(ot), value_Do));
                }
            }
        } else {
            // только слово для поиска
            System.out.println(productService.searchName(search).size());
            System.out.println(search);
            System.out.println(search.toLowerCase().trim());
            model.addAttribute("search_products", productService.searchName(search.toLowerCase().trim()));
        }

        model.addAttribute("search", search);
        model.addAttribute("ot", ot);
        model.addAttribute("Do", Do);
//        model.addAttribute("category", category);
//        model.addAttribute("price_sort", priceSort);
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
            String role = personDetails.getPerson().getRole();
            if (role.equals("ROLE_USER")) {
                return "/user/index";
            }
        } catch (Exception e) {
            System.out.println("нет авторизации");
        }

        //model.addAttribute("products", productService.getAllProduct());
        return "/product/product";
    }
    protected PersonDetails personDetails() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();
            return personDetails;
        } catch (Exception e) {
            System.out.println("нет авторизации");
        }

        return null;
    }
}
