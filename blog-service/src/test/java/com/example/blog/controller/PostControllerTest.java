package com.example.blog.controller;

import com.example.blog.dto.CreatePostRequest;
import com.example.blog.dto.PostDto;
import com.example.blog.dto.UpdatePostRequest;
import com.example.blog.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PostController.class)
class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    @Autowired
    private ObjectMapper objectMapper;

    private PostDto postDto;
    private CreatePostRequest createPostRequest;
    private UpdatePostRequest updatePostRequest;

    @BeforeEach
    void setUp() {
        postDto = new PostDto();
        postDto.setId(1L);
        postDto.setTitle("Test Title");
        postDto.setContent("Test Content");
        postDto.setHtmlContent("<p>Test Content</p>");

        createPostRequest = new CreatePostRequest();
        createPostRequest.setTitle("New Post Title");
        createPostRequest.setContent("New Post Content");

        updatePostRequest = new UpdatePostRequest();
        updatePostRequest.setTitle("Updated Title");
        updatePostRequest.setContent("Updated Content");
    }

    @Test
    void createPost_shouldReturnCreatedPost() throws Exception {
        when(postService.createPost(any(CreatePostRequest.class))).thenReturn(postDto);

        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createPostRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Test Title"));

        verify(postService, times(1)).createPost(any(CreatePostRequest.class));
    }

    @Test
    void getPostById_shouldReturnPost_whenPostExists() throws Exception {
        when(postService.getPostById(anyLong())).thenReturn(postDto);

        mockMvc.perform(get("/api/posts/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Test Title"));

        verify(postService, times(1)).getPostById(anyLong());
    }

    @Test
    void getPostById_shouldReturnNotFound_whenPostDoesNotExist() throws Exception {
        when(postService.getPostById(anyLong())).thenThrow(new RuntimeException("Post not found"));

        mockMvc.perform(get("/api/posts/{id}", 99L))
                .andExpect(status().isNotFound());

        verify(postService, times(1)).getPostById(anyLong());
    }

    @Test
    void getAllPosts_shouldReturnListOfPosts() throws Exception {
        List<PostDto> postDtoList = Arrays.asList(postDto, new PostDto());
        when(postService.getAllPosts()).thenReturn(postDtoList);

        mockMvc.perform(get("/api/posts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));

        verify(postService, times(1)).getAllPosts();
    }

    @Test
    void getPagedPosts_shouldReturnPageOfPosts() throws Exception {
        Pageable pageable = PageRequest.of(0, 10);
        Page<PostDto> postPage = new PageImpl<>(Arrays.asList(postDto), pageable, 1);
        when(postService.getAllPosts(any(Pageable.class))).thenReturn(postPage);

        mockMvc.perform(get("/api/posts/paged")
                        .param("page", "0")
                        .param("size", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(1))
                .andExpect(jsonPath("$.totalElements").value(1));

        verify(postService, times(1)).getAllPosts(any(Pageable.class));
    }

    @Test
    void updatePost_shouldReturnUpdatedPost() throws Exception {
        when(postService.updatePost(anyLong(), any(UpdatePostRequest.class))).thenReturn(postDto);

        mockMvc.perform(put("/api/posts/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatePostRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.title").value("Test Title"));

        verify(postService, times(1)).updatePost(anyLong(), any(UpdatePostRequest.class));
    }

    @Test
    void updatePost_shouldReturnNotFound_whenPostDoesNotExist() throws Exception {
        when(postService.updatePost(anyLong(), any(UpdatePostRequest.class))).thenThrow(new RuntimeException("Post not found"));

        mockMvc.perform(put("/api/posts/{id}", 99L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatePostRequest)))
                .andExpect(status().isNotFound());

        verify(postService, times(1)).updatePost(anyLong(), any(UpdatePostRequest.class));
    }

    @Test
    void deletePost_shouldReturnNoContent_whenPostExists() throws Exception {
        doNothing().when(postService).deletePost(anyLong());

        mockMvc.perform(delete("/api/posts/{id}", 1L))
                .andExpect(status().isNoContent());

        verify(postService, times(1)).deletePost(anyLong());
    }

    @Test
    void deletePost_shouldReturnNotFound_whenPostDoesNotExist() throws Exception {
        doThrow(new RuntimeException("Post not found")).when(postService).deletePost(anyLong());

        mockMvc.perform(delete("/api/posts/{id}", 99L))
                .andExpect(status().isNotFound());

        verify(postService, times(1)).deletePost(anyLong());
    }
}
