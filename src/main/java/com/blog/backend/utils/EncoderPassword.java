package com.blog.backend.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class EncoderPassword {
	
	public static void main(String[] args) {
		PasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println(encoder.encode("12345"));
	}
}
