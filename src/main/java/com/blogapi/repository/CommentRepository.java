package com.blogapi.repository;

import com.blogapi.entity.Comment;
import com.blogapi.payload.CommentDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment,Long > {

    //custom sql query
    List<Comment>findByPostId(long id); // hibernate internally create sql query when we write the method
    //List<Comment> existByEmail(String email);
    //List<Comment> findByName(long name);
    //findByPostIdOrEmail //'Or'operator will use for any one like either/ or
  // findByPostIdAndEmail

}
