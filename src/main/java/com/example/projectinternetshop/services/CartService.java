package com.example.projectinternetshop.services;

import com.example.projectinternetshop.repositories.CartRepository;
import com.example.projectinternetshop.models.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CartService {
    private final CartRepository cartRepository;

    @Autowired
    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public List<Cart> findByPersonId(int id) {
        return cartRepository.findByPersonId(id);
    }

    @Transactional
    public void addCart(Cart cart) {
        cartRepository.save(cart);
    }

    @Transactional
    public void deleteCart(Cart cart) {
        cartRepository.delete(cart);
    }

    @Transactional
    public void deleteCartById(int id, int person_id){
        cartRepository.deleteCartById(id, person_id);
    }
}
