package com.blogapi.payload;

import lombok.Data;

@Data
public class LoginDto { // encapsulation
    private String usernameOrEmail;
    private String password;


    }

//"usernameOrEmail":"mike@123",
//  "password":"testing"

//{
//    "email":"nikita@gmail.com",
//    "name":"nkita",
//    "username":"nikitasss", this is main
//    "password":"testing9"
//}