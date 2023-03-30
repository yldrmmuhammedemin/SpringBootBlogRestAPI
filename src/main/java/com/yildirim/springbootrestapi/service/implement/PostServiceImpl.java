package com.yildirim.springbootrestapi.service.implement;

import com.yildirim.springbootrestapi.entity.Post;
import com.yildirim.springbootrestapi.exception.ResourceNotFoundException;
import com.yildirim.springbootrestapi.payload.PostDto;
import com.yildirim.springbootrestapi.repository.PostRepository;
import com.yildirim.springbootrestapi.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = mapToEntity(postDto);
        Post responsePost = postRepository.save(post);
        PostDto postResponse = mapToDTO(responsePost);
        return postResponse;
    }

    @Override
    public List<PostDto> getAllPosts() {
        List<Post> postDtoList = postRepository.findAll();
        return postDtoList.stream().map(post -> mapToDTO(post)).collect(Collectors.toList());
    }

    @Override
    public PostDto findPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(()->
                        new ResourceNotFoundException("Post", "id", postId));
        PostDto postDto = mapToDTO(post);
        return postDto;
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long postId) {
        Post responsePost = postRepository.findById(postId).
                orElseThrow(()->
                        new ResourceNotFoundException("Post", "id", postId));
        responsePost.setTitle(postDto.getTitle());
        responsePost.setDescription(postDto.getDescription());
        responsePost.setContent(postDto.getContent());
        postRepository.save(responsePost);
        PostDto responsePostDto = mapToDTO(responsePost);

        return responsePostDto;
    }

    @Override
    public String deletePostById(Long postId) {
        postRepository.deleteById(postId);
        return "Successfully Deleted.";
    }

    private PostDto mapToDTO(Post post){
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setDescription(post.getDescription());
        postDto.setContent(post.getContent());
        return postDto;
    }

    private Post mapToEntity(PostDto postDto){
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());
        return post;
    }
}
