package com.yildirim.springbootrestapi.service;

import com.yildirim.springbootrestapi.payload.PostDto;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    List<PostDto> getAllPosts();

    PostDto findPostById(Long postId);

    PostDto updatePost(PostDto postDto, Long postId);

    String deletePostById(Long postId);
}
