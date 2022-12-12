package com.example.projectinternetshop.security;

import com.example.projectinternetshop.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class PersonDetails implements UserDetails {

    private final Person person;
    @Autowired
    public PersonDetails(Person person) {
        this.person = person;
    }

    // представлят инфу о роли пользователя
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        //возвращает лист из однго элемента
        return Collections.singletonList(new SimpleGrantedAuthority(person.getRole()));
    }

    // позволяет получить пароль пользователя
    @Override
    public String getPassword() {
        return this.person.getPassword();
    }
    // позволяет получить логин польлзователя
    @Override
    public String getUsername() {
        return this.person.getLogin();
    }
    // аккаунт действителен
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    // Аккаунт не заблокирован
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // пароль является действительным/валидным
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    // Аккаунт активен
    @Override
    public boolean isEnabled() {
        return true;
    }

    // метод для получения всего пользователя
    public Person getPerson() {
        return this.person;
    }

}
