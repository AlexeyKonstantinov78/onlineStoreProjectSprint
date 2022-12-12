package com.example.projectinternetshop.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

// конфиг для сканирования и использования кастомного валидатора
@Configuration
@ComponentScan("com.example.springsecurityapplication.util")
public class SpringConfig {
}
