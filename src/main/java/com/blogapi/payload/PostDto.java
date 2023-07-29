       package com.blogapi.payload;

       import lombok.Data;
       import javax.validation.constraints.NotEmpty;
       import javax.validation.constraints.Size;

        @Data // it will give setter and getter... encapsulation achieve with  only one annotation
        public class PostDto {
        private long id;

        @NotEmpty
        @Size(min=2,message="Title should be atleast 2 character")
        private String title;

        @NotEmpty
        @Size(min=4,message="description should be atleast 4 character")
        private String description;

        @NotEmpty(message="content may not be empty") //for custom message
        private String content;

    }

//{
//
//        "title": "simi",
//        "description": "java 2  ",
//        "content":"java2"
//        }