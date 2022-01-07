package com.feyon.codeset;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author Feng Yong
 */
@MapperScan(basePackages = "com.feyon.codeset.mapper")
@SpringBootApplication
@EnableAspectJAutoProxy
public class CodeSetApplication {

    public static void main(String[] args) {
        SpringApplication.run(CodeSetApplication.class, args);
    }

}
