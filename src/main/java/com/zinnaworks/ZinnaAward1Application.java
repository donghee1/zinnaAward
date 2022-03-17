package com.zinnaworks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ZinnaAward1Application {

	public static void main(String[] args) {
		SpringApplication.run(ZinnaAward1Application.class, args);
	}

}
