package com.example.projectinternetshop.util;

import com.example.projectinternetshop.models.Product;
import com.example.projectinternetshop.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class ProductValidator implements Validator {

    private final ProductService productService;
    @Autowired
    public ProductValidator(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return Product.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Product product = (Product) target;

        if (productService.getProductFinfByTitle(product) != null) {
            errors.rejectValue("title", "", "Даное наименование используется");
        }
    }
}
