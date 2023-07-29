package com.blogapi.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration // whenever its configuration class you have to tell sprinboot its config classso that it will read the config and do required thing
@EnableWebSecurity// if you dont want to enable the default security config,iwant my own user name nd password to enable
@EnableGlobalMethodSecurity(prePostEnabled = true) // after adding this annotation @PreAuthorize annotstion will work it means it enables preauthorize
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Bean //this annotattion will generet an object and spring ioc will return the object from this method and inject the object into the variable
    PasswordEncoder passwordEncoder(){

        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception //  and this is not work without @Bean annotation.this method is comes from builtin liabrary
    {                                                                           //this class will authenticate the username & password and tell pass or fail thats why this class is required
        return super.authenticationManagerBean();
    }

    @Override
     protected void configure(HttpSecurity http) throws Exception{//HttpSecurity object is defined the security rule which means which url who can access
         http   // it is ref.variable which automatically get intialize with the object address on its own
                 .csrf().disable()   // csrf is a technique of performing a hacking// this all below are chain statement
                 .authorizeRequests()// which request should be allowed and which is denied is controlled by this method
                 .antMatchers(HttpMethod.GET,"/api/**").permitAll() // this line removes authentication on those url which you dont want to authenticate while other urls are still secured
                 .antMatchers("/api/auth/**").permitAll() //any url which has this api path will open no need of authentication
                 //.antMatchers(HttpMethod.POST,"/api/**").permitAll()
                 //.antMatchers("/api/auth/signup").hasRole("ADMIN")// when you sigup as an admin then this url will work and then admin can create user
                 .anyRequest()
                 .authenticated()////any other request rather than permitall url will be authenticated by default
                 .and()
                 .httpBasic(); // it follows form based,basic authentication
     }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {// we are not object is created this is bild in
        auth.userDetailsService(userDetailsService) // this is builtin method.
                .passwordEncoder(passwordEncoder());// again this is  builtin method.
}
     // Inmemory Authentication //in that creating two object storing username and password
//    @Override
//    @Bean
//    protected UserDetailsService userDetailsService() {  //only for learning purposse not for realistic projacet
//        UserDetails user =    //create user object
//                User.builder().username("pankaj").password(passwordEncoder()
//                        .encode("password")).roles("USER").build();
//        UserDetails admin1 =                         //create admin object
//                User.builder().username("admin1").password(passwordEncoder()
//                        .encode("admin1")).roles("ADMIN").build();
//        return new InMemoryUserDetailsManager(user, admin1);
   // }
}

//.antMatchers(HttpMethod.POST,"/api/post").permitAll()
// @Bean=such built in library object to be created and  to be maintained by spring ioc we have to use @bean
