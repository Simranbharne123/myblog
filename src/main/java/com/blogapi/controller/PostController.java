package com.blogapi.controller;

import com.blogapi.payload.PostDto;
import com.blogapi.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/posts") //this are api url
public class PostController {
    private PostService postService;
    public PostController(PostService postService) { // CONSTRUCTOR BASED DEPENDENCY INJECTION

        this.postService = postService;
    }

    //http://localhost:8080/api/posts
    @PreAuthorize("hasRole('ADMIN')")// only when you login as admin you can create a post
    @PostMapping
    public ResponseEntity<?> createPost(@Valid @RequestBody PostDto postDto, BindingResult result) {  //to get  response in postman we have to use this method ResponseEntity

        if(result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);//201
        }
        PostDto savedDto = postService.createPost(postDto);
        return new ResponseEntity<>(savedDto, HttpStatus.CREATED);   //code 201
    }

    //http://localhost:8080/api/posts/1000   this is now path parameter
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") long id) { //the value presnt in the id it will get initialize here(long id)
        //System.out.println(id);
        PostDto dto = postService.getById(id);//it find the record based on the id and return back into postdto object
        return new ResponseEntity<>(dto, HttpStatus.OK); //ok status 200
    }
    //http://localhost:8080/api/posts?pageNo=0&pageSize=3&sortBy=title

    @GetMapping
    public List<PostDto> getAllPosts(
            @RequestParam(value = "pageNo",defaultValue = "0",required = false) int pageNo,
            @RequestParam(value = "pageSize",defaultValue = "5",required = false) int pageSize,
            @RequestParam(value="sortBy",defaultValue="id",required = false) String sortBy,
            @RequestParam(value="sortDir",defaultValue="asc",required = false) String sortDir
    ) {
        List<PostDto> postDtos = postService.getAllPosts(pageNo,pageSize,sortBy,sortDir);
        return postDtos;
    }

    //http://localhost:8080/api/posts/1000   this is now path parameter
    @PreAuthorize("hasRole('ADMIN')")// only when you Delete as admin you can create a post
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") long id) {
        postService.deletePost(id);
        return new ResponseEntity<>("post is deleted!!", HttpStatus.OK);//staus 200
    }

    //http://localhost:8080/api/posts/1000
 @PreAuthorize("hasRole('ADMIN')")// only when you update as admin you can create a post
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable("id") long id, @RequestBody PostDto postDto) {//it will take the id from the url and json content will go to postDto object
        PostDto dto = postService.updatePost(id, postDto);  //staus 200
        return new ResponseEntity<>(dto,HttpStatus.OK); //status 200
    }

}
//when we click send in postman , that data go to PostDto controller layer then calling the service layer which is service layer take the postDto and that dto  should save into the database.
// but it can not save the Dto to database then we convert Dto object in Entity,  save after saving  whatever entity save gets a detail convert tht save Post to PostDto and
// return the dto back to controller.
//when we return ResponseEntity<PostDto>  so that the content  can be see in the response in postman once I save.
// we Repose in postman because of  (return new ResponseEntity<>(saveDto, HttpStatus.CREATED)) its returning Dto.
// HttpStatus.CREATED we write this then we get in postman status 201 Created(postmapping).

//pathvariable is help to read the data from path param and in case of query parameter we use @RequestParam
// status code for fetching the record from db(getmapping) is 200 and 404 found when url is not corect