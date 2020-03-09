package com.lens.chatter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"com.lens.chatter"})
public class ChatterApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChatterApplication.class, args);
	}

}
