package com.example.blog.service;

import com.example.blog.dto.CreatePostRequest;
import com.example.blog.dto.PostDto;
import com.example.blog.dto.UpdatePostRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    PostDto createPost(CreatePostRequest createPostRequest);

    PostDto getPostById(Long id);

    List<PostDto> getAllPosts(); // Existing method

    Page<PostDto> getAllPosts(Pageable pageable); // New method for pagination

    PostDto updatePost(Long id, UpdatePostRequest updatePostRequest);

    void deletePost(Long id);

    List<PostDto> getPostsByTagName(String tagName);
}
