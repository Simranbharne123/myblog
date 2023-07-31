package com.blogapi.controller;

import com.blogapi.entity.Role;
import com.blogapi.entity.User;
import com.blogapi.payload.JWTAuthResponse;
import com.blogapi.payload.LoginDto;
import com.blogapi.payload.SignUpDto;
import com.blogapi.repository.RoleRepository;
import com.blogapi.repository.UserRepository;
import com.blogapi.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
@RestController
@RequestMapping("/api/auth")
public class AuthController {

   @Autowired
    private UserRepository userRepository;
     @Autowired
     private PasswordEncoder passwordEncoder;
    @Autowired
   private RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager; // it manage ur authentication login logout. it is controll by this classs. and it present in security library

    @Autowired
    private JwtTokenProvider tokenProvider;





    //http://localhost:8080/api/auth/signin
    @PostMapping("/signin")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsernameOrEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // get token form tokenProvider
        String token = tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JWTAuthResponse(token));
    }


    //http://localhost:8080/api/auth/signin
//    @PostMapping("/signin")
//    public ResponseEntity<String> authenticateUser(@RequestBody LoginDto loginDto){
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(loginDto.getUsernameOrEmail(), // it just validate the username and password if valid it will genrated token if not throws exception and stops
//                        loginDto.getPassword()) //  from logindto it will extract UsernameOrEmail&getPassword if it isvalid it will generate a token
//        );
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        return new ResponseEntity<>("User signed-in successfully!.",
//                HttpStatus.OK);
//    }

    //localhost:8080/api/auth/signup  via this url we submit json object to signupdto(java object)
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignUpDto signUpDto){
        // add check for username exists in a DB
        if(userRepository.existsByUsername(signUpDto.getUsername())){
            return new ResponseEntity<>("Username is already taken!",
                    HttpStatus.BAD_REQUEST);
        }
        // add check for email exists in DB
        if(userRepository.existsByEmail(signUpDto.getEmail())){
            return new ResponseEntity<>("Email is already taken!",
                    HttpStatus.BAD_REQUEST);
        }
        // create user object
        User user = new User(); // 1.cpying the data in user entity
        user.setName(signUpDto.getName());
        user.setUsername(signUpDto.getUsername());  //creating the user
        user.setEmail(signUpDto.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
        Role roles = roleRepository.findByName("ROLE_ADMIN").get();//2.this two lines will actually set the role when you sign up
        user.setRoles(Collections.singleton(roles));// whenever you setting the role this singleton object should being follwed
        userRepository.save(user); // save the user object(as admin)
        return new ResponseEntity<>("User registered successfully",
                HttpStatus.OK);
    }
}

// singleton design pattern is you create one object through your programme and used that object
// private Set<Role> roles = new HashSet<>(); when you set the values/set roles  object that you putting, you need to convert that  into a collection
//set role is a set and roles is not a set thats why we have to convert that roles(47)into setby using collection singleton object
// when you signup  as an admin/then admin can create user account