package com.yildirim.springbootrestapi.service;

import com.yildirim.springbootrestapi.payload.PostDto;

public interface PostService {
    PostDto createPost(PostDto postDto);
}
