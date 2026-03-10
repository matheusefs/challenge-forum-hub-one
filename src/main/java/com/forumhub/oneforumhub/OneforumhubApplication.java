package com.forumhub.oneforumhub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class OneforumhubApplication {
	public static void main(String[] args) {
		SpringApplication.run(OneforumhubApplication.class, args);
	}
}
