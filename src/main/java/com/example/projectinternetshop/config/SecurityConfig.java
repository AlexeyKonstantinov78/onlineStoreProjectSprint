package com.example.projectinternetshop.config;

//import com.example.springsecurityapplication.security.AuthenticationProvider;
//import org.springframework.beans.factory.annotation.Autowired;
import com.example.projectinternetshop.services.PersonDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

// основной конфиг  для конфигурации безопасности в приложении
@EnableWebSecurity
// сообщает что в приложение доступно разагроничение роле на основе анатаций
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
//    private final AuthenticationProvider authenticationProvider;
//    @Autowired
//    public SecurityConfig(AuthenticationProvider authenticationProvider) {
//        this.authenticationProvider = authenticationProvider;
//    }
    private final PersonDetailsService personDetailsService;
    @Autowired
    public SecurityConfig(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    // Метод по настроке аутентификации
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        // Производим футентификацию с помощью сервиса
        authenticationManagerBuilder.userDetailsService(personDetailsService).passwordEncoder(getPasswordEncoder());

//        authenticationManagerBuilder.authenticationProvider(authenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //Конфигурируем авторизацию
        //указываем какой url запрос будит отправлятся при заходе на закрытие страницы

        http
                //отключаем защиту от потделик запросов
                //.csrf().disable()
                .authorizeRequests()
                // данная страница доступна с ролью admin
                .antMatchers("/admin").hasAnyRole("ADMIN")
                .antMatchers("/auth/login", "/error", "/auth/registration", "/product", "/product/info/{id}", "/img/**", "/css/**", "/js/**", "/product/search").permitAll()
                // все остальные страницы доступны с ролью user и admin
                .anyRequest().hasAnyRole("USER", "ADMIN")
//                //для всех остальных страниц необхадима авторизация
//                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/auth/login")
                .loginProcessingUrl("/process_login")
                .defaultSuccessUrl("/index", true) // вслучае успешной идентификации перенаправлять на index
                .failureUrl("/auth/login")
                .and()
                //указываем при переходе на /logout будит очищена сейсия пользователя и перенаправить на /auth/login
                .logout().logoutUrl("/logout").logoutSuccessUrl("/auth/login");
    }

    // шифрование паролей
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
