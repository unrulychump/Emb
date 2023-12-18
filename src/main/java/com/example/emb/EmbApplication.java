package com.example.emb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@MapperScan("com.example.emb.mapper")
@SpringBootApplication
public class EmbApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmbApplication.class, args);
    }

}
