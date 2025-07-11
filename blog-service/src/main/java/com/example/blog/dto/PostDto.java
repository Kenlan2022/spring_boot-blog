package com.example.blog.dto;

import lombok.Data;

import java.util.Set;

@Data
public class PostDto {
    private Long id;
    private String title;
    private String content;
    private String htmlContent;
    private Set<TagDto> tags;
}