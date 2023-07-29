package com.blogapi.service.impl;

import com.blogapi.entity.Comment;
import com.blogapi.entity.Post;
import com.blogapi.exceptions.ResourceNotFoundException;
import com.blogapi.payload.CommentDto;
import com.blogapi.repository.CommentRepository;
import com.blogapi.repository.PostRepository;
import com.blogapi.service.CommentService;
import org.springframework.stereotype.Service;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private PostRepository postRepo;

    public CommentServiceImpl(PostRepository postRepo, CommentRepository commentRepo) {
        this.postRepo = postRepo;
        this.commentRepo = commentRepo;
    }

    private CommentRepository commentRepo;

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Post post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException(postId)
        );
        Comment comment = new Comment();
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        comment.setPost(post);//above cmnt for this post object and post object is belong to postId number
        Comment savedComment = commentRepo.save(comment);

        CommentDto dto = new CommentDto();
        dto.setId(savedComment.getId());
        dto.setName(savedComment.getName());
        dto.setEmail(savedComment.getEmail());
        dto.setBody(savedComment.getBody());
        return dto;
    }

    public List<CommentDto> findCommentByPostId(long postId) {
        Post post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException(postId)
        );
    List<Comment> comments = commentRepo.findByPostId(postId);
    return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
}

    @Override
    public void deleteCommentById(long postId, long id) {
        Post post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException(postId
        ));
        Comment comment  = commentRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id)
        );
        commentRepo.deleteById(id);
    }

    @Override
    public CommentDto getCommentById(long postId, long id) {
        Post post = postRepo.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException(postId)
        );
        Comment comment  = commentRepo.findById(id).orElseThrow(
                () -> new ResourceNotFoundException(id)
        );
        CommentDto commentDto = mapToDto(comment);
        return commentDto;
    }

    @Override
    public CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto) {
        Post post = postRepo.findById(postId).orElseThrow( // retrieve post entity by id
                () -> new ResourceNotFoundException(postId)
        );
        Comment comment  = commentRepo.findById(commentId).orElseThrow( //retreive comment by id
                () -> new ResourceNotFoundException(commentId)
        );
        comment.setId(commentId);
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        Comment updatedComment=commentRepo.save(comment);

        return mapToDto(updatedComment);
    }

    CommentDto mapToDto(Comment comment){
        CommentDto dto= new CommentDto();
        dto.setId(comment.getId());
        dto.setName(comment.getName());
        dto.setEmail(comment.getEmail());
        dto.setBody(comment.getBody());

        return dto;
    }


}
