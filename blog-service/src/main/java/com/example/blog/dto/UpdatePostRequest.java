package com.example.blog.dto;

import lombok.Data;

import java.util.Set;

@Data
public class UpdatePostRequest {
    private String title;
    private String content;
    private Set<String> tags;
}