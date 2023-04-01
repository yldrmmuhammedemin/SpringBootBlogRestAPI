package com.yildirim.springbootrestapi.repository;

import com.yildirim.springbootrestapi.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PostRepository extends JpaRepository<Post, Long> {

    Post findAllById(Long postId);
    List<Post> findByCategoryId(Long categoryId);
}
