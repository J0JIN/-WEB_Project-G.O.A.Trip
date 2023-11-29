package com.ssafy.goatrip;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackages = {"com.ssafy.goatrip"})
@MapperScan(basePackages = "com.ssafy.goatrip.model.dao")
public class GoatripApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoatripApplication.class, args);
	}

}
