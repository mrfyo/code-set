package com.feyon.codeset;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Feng Yong
 */
@MapperScan(basePackages = "com.feyon.codeset.mapper")
@SpringBootApplication
public class CodeSetApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodeSetApplication.class, args);
    }

}
