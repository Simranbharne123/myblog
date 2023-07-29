package com.blogapi.service.impl;

import com.blogapi.entity.Post;
import com.blogapi.exceptions.ResourceNotFoundException;
import com.blogapi.payload.PostDto;
import com.blogapi.repository.PostRepository;
import com.blogapi.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepo;

    private ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepo, ModelMapper modelMapper) {

        this.postRepo = postRepo;
        this.modelMapper = modelMapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = mapToEntity(postDto);

        Post savedPost = postRepo.save(post);
        PostDto dto = mapToDto(savedPost);//mapToDto()this method will take the entity object then it return the dto

        return dto;        // return the dto
    }

    @Override
    public PostDto getById(long id) {
        //  Optional<Post> byId =postRepo.findById(id); // this will find the record and return back to me an optional object
        Post post = postRepo.findById(id).orElseThrow( // if just want custom exception use orElseThrow()
                () -> new ResourceNotFoundException(id)//lambdas expression are used  to throw the exception when record is not found by just creating object

        );
        PostDto dto = mapToDto(post);


        return dto; // return back  to the dto controller
    }

    @Override
    public List<PostDto> getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);//Sort.by(sortBy)String sortBy will convert into this Sort object


        Page<Post> posts = postRepo.findAll(pageable);
        List<Post> content = posts.getContent();// getContent() does all page the page object is converted back into list
        List<PostDto> postDtos = content.stream().map(post -> mapToDto(post)).collect(Collectors.toList());
        return postDtos;
    }

    @Override
    public void deletePost(long id) {
        Post post = postRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id)
        );
        postRepo.deleteById(id);
    }

    @Override
    public PostDto updatePost(long id, PostDto postDto) {
        Post post = postRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id)
        );
        Post updatedContent = mapToEntity(postDto);
        updatedContent.setId(post.getId());
        Post updatedPostInfo=postRepo.save(updatedContent);
        return mapToDto(updatedPostInfo);

//        Post post = new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
//        post.setId(postDto.getId());
//
//        Post updatedPost = postRepo.save(post);
//
//
//        PostDto dto = new PostDto();
//        dto.setId(post.getId());
//        dto.setTitle(updatedPost.getTitle());
//        dto.setDescription(updatedPost.getDescription());
//        dto.setContent(updatedPost.getContent());
   //return dto;
    }






    PostDto mapToDto (Post post){ // whenever you want to convert entity to dto call this method
       // PostDto dto= new PostDto();
      PostDto dto=  modelMapper.map(post,PostDto.class);//this method will take the content from post entity &copy into the postdto object and thus its address return to the dto
//        dto.setId(post.getId()); //it copies the data from entity to dto
//        dto.setTitle(post.getTitle());
//        dto.setDescription(post.getDescription());
//        dto.setContent(post.getContent());
        return dto;
    }
    Post mapToEntity(PostDto postDto){
    Post post =modelMapper.map(postDto,Post.class);

//        Post post=new Post();
//
//        post.setTitle(postDto.getTitle());  // dto will fetch the data and set into the postobject and store to db
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
       return post;

    }

}


//  when i apply get()method that optional class wil convert to entity classs
//we cannot return post object to postman so that we have to convert that post object into dto