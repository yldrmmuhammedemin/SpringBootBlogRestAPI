package com.yildirim.springbootrestapi.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;


@Data
public class CommentDto {
    private long id;
    @NotEmpty
    @Size(min = 3, message = "Name should have min 2 characters.")
    private String name;

    @Email
    @NotEmpty(message = "Email should not be null or empty.")
    private String email;

    @NotEmpty
    @Size(min = 2, max = 30, message = "Body should have max 30 and min 2 characters.")
    private String body;
}
