package com.example.blog.dto;

import lombok.Data;

@Data
public class CreatePostRequest {
    private String title;
    private String content;
}
