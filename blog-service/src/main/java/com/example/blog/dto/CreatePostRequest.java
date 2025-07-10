package com.example.blog.dto;

import lombok.Data;

import java.util.Set;

@Data
public class CreatePostRequest {
    private String title;
    private String content;
    private Set<String> tags;
}