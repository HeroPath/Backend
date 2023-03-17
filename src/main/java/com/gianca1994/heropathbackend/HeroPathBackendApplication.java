package com.gianca1994.heropathbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @Author: Gianca1994
 * @Explanation: This class is in charge of the main application.
 */

@SpringBootApplication
@EnableScheduling
public class HeroPathBackendApplication {
	public static void main(String[] args) {
		SpringApplication.run(HeroPathBackendApplication.class, args);
	}
}
