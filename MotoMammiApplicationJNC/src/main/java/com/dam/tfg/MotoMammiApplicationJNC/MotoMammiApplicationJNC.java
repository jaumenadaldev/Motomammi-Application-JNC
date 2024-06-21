package com.dam.tfg.MotoMammiApplicationJNC;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MotoMammiApplicationJNC {
	public static void main(String[] args) {
		SpringApplication.run(MotoMammiApplicationJNC.class, args);
	}
}