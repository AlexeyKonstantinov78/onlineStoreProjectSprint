//package com.example.springsecurityapplication.security;
//
//import com.example.springsecurityapplication.services.PersonDetailsService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.stereotype.Component;
//
//import java.util.Collections;
//
//@Component
//public class AuthenticationProvider implements org.springframework.security.authentication.AuthenticationProvider {
//    private final PersonDetailsService personDetailsService;
//    @Autowired
//    public AuthenticationProvider(PersonDetailsService personDetailsService) {
//        this.personDetailsService = personDetailsService;
//    }
//
//    // логика по аутентификации в приложении
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        // получаем логин сформы аутотификации. поступает он сюда
//        String login = authentication.getName();
//        // получаем пароль с формы аутентификации
//        String password = authentication.getCredentials().toString();
//
//        // т.к данный метод возвращаем объект интерфеса UserDetails то и объект мы создаем через интерфейс
//        UserDetails person = personDetailsService.loadUserByUsername(login);
//
//        // проверка если пароль не соответсвует то выбрасываем исключение
//        if (!password.equals(person.getPassword())) {
//            throw new BadCredentialsException("Не корректный пароль");
//        }
//
//        //возвращаем обект аутентификации в данном объекте лежит пользователь его пароль и его права доступа
//        return new UsernamePasswordAuthenticationToken(person, password, Collections.emptyList());
//    }
//    // будит прописанна в каких случаях будит работать
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return true;
//    }
//}
