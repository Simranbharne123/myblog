package com.blogapi.payload;

import lombok.Data;

@Data
public class SignUpDto {
    private String name;
    private String username;
    private String email;
    private String password;
}
//{
//        "name":"nkita",
//     "username":"nikitasss",
//       "email":"nikita@gmail.com",
//        "password":"testing9"
//        }

//{
//        "usernameOrEmail":"mike@gmail.com",
//        "password":"testing"
//        }