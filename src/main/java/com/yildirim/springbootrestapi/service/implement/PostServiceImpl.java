package com.yildirim.springbootrestapi.service.implement;

import com.yildirim.springbootrestapi.entity.Post;
import com.yildirim.springbootrestapi.payload.PostDto;
import com.yildirim.springbootrestapi.repository.PostRepository;
import com.yildirim.springbootrestapi.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Override
    public PostDto createPost(PostDto postDto) {

        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getContent());
        post.setContent(postDto.getContent());
       Post newPost = postRepository.save(post);

       PostDto postResponse = new PostDto();
       postResponse.setId(newPost.getId());
       postResponse.setTitle(newPost.getTitle());
       postResponse.setDescription(newPost.getContent());
       postResponse.setContent(newPost.getContent());
        return null;
    }
}
