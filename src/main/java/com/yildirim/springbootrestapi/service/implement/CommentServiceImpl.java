package com.yildirim.springbootrestapi.service.implement;

import com.yildirim.springbootrestapi.entity.Comment;
import com.yildirim.springbootrestapi.entity.Post;
import com.yildirim.springbootrestapi.entity.User;
import com.yildirim.springbootrestapi.exception.APIException;
import com.yildirim.springbootrestapi.exception.ResourceNotFoundException;
import com.yildirim.springbootrestapi.payload.CommentDto;
import com.yildirim.springbootrestapi.repository.CommentRepository;
import com.yildirim.springbootrestapi.repository.PostRepository;
import com.yildirim.springbootrestapi.repository.UserRepository;
import com.yildirim.springbootrestapi.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto);
        Post post = postRepository.findById(postId)
                .orElseThrow(()->
                new ResourceNotFoundException("Post", "id", postId));
        User user = userRepository.findByEmail(authanticatedUsername()).orElseThrow(()-> new UsernameNotFoundException("User not found"));
        comment.setPost(post);
        comment.setCommentUser(user);
        Comment responseComment = commentRepository.save(comment);
        return mapToDto(responseComment);
    }

    @Override
    public List<CommentDto> getAllPostComments(long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);

        return comments.stream().map(comment -> mapToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto getCommentById(long postId, long commentId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Post", "id", postId));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Comment", "id", commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            new APIException(HttpStatus.BAD_REQUEST, "This comment not belong to post.");
        }

        return mapToDto(comment);
    }

    @Override
    public CommentDto update(long postId, long commentId, CommentDto commentDto) {
        Post post = postRepository.findById(postId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Post", "id", postId));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Comment", "id", commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            new APIException(HttpStatus.BAD_REQUEST, "This comment not belong to post.");
        }
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        Comment responseComment = commentRepository.save(comment);
        return mapToDto(responseComment);
    }

    @Override
    public String deleteComment(long postId, long commentId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Post", "id", postId));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Comment", "id", commentId));

        if(!comment.getPost().getId().equals(post.getId())){
            new APIException(HttpStatus.BAD_REQUEST, "This comment not belong to post.");
        }
        commentRepository.deleteById(comment.getId());
        return "Comment successfully deleted.";
    }

    private CommentDto mapToDto(Comment comment){
        CommentDto commentDto = modelMapper.map(comment,CommentDto.class);
        return commentDto;
    }

    private Comment mapToEntity(CommentDto commentDto){
        Comment comment = modelMapper.map(commentDto, Comment.class);
        return comment;
    }

    private String authanticatedUsername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        return username;
    }
}
