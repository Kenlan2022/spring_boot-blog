package com.example.blog.dto;

import lombok.Data;

@Data
public class UpdatePostRequest {
    private String title;
    private String content;
}
