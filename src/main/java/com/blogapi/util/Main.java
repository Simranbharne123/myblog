package com.blogapi.util;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Main {
    public static void main(String[] args) {
        PasswordEncoder passwordEncoder =new BCryptPasswordEncoder();  // this object comes from spring security library
        System.out.println(passwordEncoder.encode("test")); // the functionality of this encode method is that the password you given it will make that encoded in db
    }
}
