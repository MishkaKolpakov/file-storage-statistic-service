package ua.softserve.academy.kv030.statservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackages = {"ua.softserve.academy.kv030.statservice.controller", "ua.softserve.academy.kv030.statservice.config", "ua.softserve.academy.kv030.statservice.service"})
public class StatisticServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(StatisticServiceApplication.class, args);
    }


}
