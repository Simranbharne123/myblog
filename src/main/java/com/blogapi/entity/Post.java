package com.blogapi.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

   @Data // instead of getter and setter
   @AllArgsConstructor // that is equivalent to parameterized constructor.
   @NoArgsConstructor// no argument constructor,default constructor does not come to the picture.
   @Entity
   @Table(name="posts", uniqueConstraints = {@UniqueConstraint(columnNames= {"title"})})//instead of unique=true.
   public class Post {

      @Id
      @GeneratedValue(strategy = GenerationType.IDENTITY)
      private long id;

      @Column(name="title", nullable=false)
      private String title;

      @Column(name="description", nullable=false)
      private String description;

      @Column(name="content", nullable=false)
      private String content;

      @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
      private List<Comment> comments = new ArrayList<>();

   }


