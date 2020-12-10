package com.numberplate.numberplateserver;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.numberplate.numberplateserver.mapper")
public class  NumberplateserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(NumberplateserverApplication.class, args);
    }

}
