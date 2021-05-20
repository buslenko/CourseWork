package ru.mirea.sweetshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
public class SpringSweetShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSweetShopApplication.class, args);
    }

}
