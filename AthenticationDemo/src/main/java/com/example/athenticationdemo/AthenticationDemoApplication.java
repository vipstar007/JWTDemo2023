package com.example.athenticationdemo;

import com.example.athenticationdemo.dto.RegisterRequestDto;
import com.example.athenticationdemo.jwt.service.AuthenticationService;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static com.example.athenticationdemo.user.Role.ADMIN;
import static com.example.athenticationdemo.user.Role.MANAGER;

@SpringBootApplication
public class AthenticationDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(AthenticationDemoApplication.class, args);
    }
//    @Bean
//    public CommandLineRunner commandLineRunner(
//            AuthenticationService service
//    ) {
//        return args -> {
//            var admin = RegisterRequestDto.builder()
//                    .firstname("Admin")
//                    .lastname("Admin")
//                    .email("admin@mail.com")
//                    .password("password")
//                    .role(ADMIN)
//                    .build();
//            System.out.println("Admin token: " + service.register(admin).getAccessToken());
//
//            var manager = RegisterRequestDto.builder()
//                    .firstname("Admin")
//                    .lastname("Admin")
//                    .email("manager@mail.com")
//                    .password("password")
//                    .role(MANAGER)
//                    .build();
//            System.out.println("Manager token: " + service.register(manager).getAccessToken());
//
//        };
//    }
}
