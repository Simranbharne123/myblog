package com.blogapi.repository;

import com.blogapi.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {

    Optional<Role> findByName(String name);// you just give the role name and it will find the object based on role name i.e. admin, user


}
