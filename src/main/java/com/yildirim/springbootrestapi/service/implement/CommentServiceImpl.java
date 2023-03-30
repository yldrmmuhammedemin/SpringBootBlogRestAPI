package com.yildirim.springbootrestapi.service.implement;

import com.yildirim.springbootrestapi.entity.Comment;
import com.yildirim.springbootrestapi.entity.Post;
import com.yildirim.springbootrestapi.exception.APIException;
import com.yildirim.springbootrestapi.exception.ResourceNotFoundException;
import com.yildirim.springbootrestapi.payload.CommentDto;
import com.yildirim.springbootrestapi.repository.CommentRepository;
import com.yildirim.springbootrestapi.repository.PostRepository;
import com.yildirim.springbootrestapi.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto);
        Post post = postRepository.findById(postId)
                .orElseThrow(()->
                new ResourceNotFoundException("Post", "id", postId));
        comment.setPost(post);
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
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setName(comment.getName());
        commentDto.setBody(comment.getBody());
        commentDto.setEmail(comment.getEmail());
        return commentDto;
    }

    private Comment mapToEntity(CommentDto commentDto){
        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());
        return comment;
    }
}
