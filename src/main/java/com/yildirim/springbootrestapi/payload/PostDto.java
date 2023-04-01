package com.yildirim.springbootrestapi.payload;

import com.yildirim.springbootrestapi.entity.Category;
import com.yildirim.springbootrestapi.entity.Comment;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class PostDto {
    private long id;

    @NotEmpty
    @Size(min = 2, max = 30, message = "Post title should have max 30 and min 2 characters.")
    private String title;

    @NotEmpty
    @Size(min = 10, message = "Post description at least should have at least 10 characters.")
    private String description;

    @NotEmpty
    private String content;

    private Set<CommentDto> comments;

    private CategoryDto categoryDto;
}
