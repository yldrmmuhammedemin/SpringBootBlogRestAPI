package com.yildirim.springbootrestapi.service;

import com.yildirim.springbootrestapi.payload.PostDto;
import com.yildirim.springbootrestapi.response.PostResponse;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    PostResponse getAllPosts(int pageNo, int PageSize, String sort, String sortDir);

    PostDto findPostById(Long postId);

    PostDto updatePost(PostDto postDto, Long postId);

    String deletePostById(Long postId);
}
