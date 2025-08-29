package com.org.skillshare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Base64;

@SpringBootApplication
public class SkillshareApplication {

	public static void main(String[] args) {

        SpringApplication.run(SkillshareApplication.class, args);
        System.out.println("hello world!!");

        var key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        System.out.println(Base64.getEncoder().encodeToString(key.getEncoded()));
	}

}
