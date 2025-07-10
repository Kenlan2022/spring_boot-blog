package com.example.blog.service;

import com.example.blog.dto.CreatePostRequest;
import com.example.blog.dto.PostDto;
import com.example.blog.dto.UpdatePostRequest;
import com.example.blog.entity.Post;
import com.example.blog.entity.Tag;
import com.example.blog.repository.PostRepository;
import com.example.blog.repository.TagRepository;
import com.example.blog.service.impl.PostServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private PostServiceImpl postService;

    private Post post;
    private CreatePostRequest createPostRequest;
    private UpdatePostRequest updatePostRequest;

    @BeforeEach
    void setUp() {
        post = new Post();
        post.setId(1L);
        post.setTitle("Test Title");
        post.setContent("Test Content");

        createPostRequest = new CreatePostRequest();
        createPostRequest.setTitle("New Title");
        createPostRequest.setContent("New Content");
        createPostRequest.setTags(Set.of("Java", "Spring"));

        updatePostRequest = new UpdatePostRequest();
        updatePostRequest.setTitle("Updated Title");
        updatePostRequest.setContent("Updated Content");
        updatePostRequest.setTags(Set.of("Java", "Docker"));
    }

    @Test
    void createPost_shouldReturnPostDtoWithTags() {
        when(postRepository.save(any(Post.class))).thenReturn(post);
        when(tagRepository.findByName("Java")).thenReturn(Optional.of(new Tag()));
        when(tagRepository.findByName("Spring")).thenReturn(Optional.empty());

        PostDto result = postService.createPost(createPostRequest);

        assertNotNull(result);
        assertEquals(post.getId(), result.getId());
        verify(postRepository, times(1)).save(any(Post.class));
        verify(tagRepository, times(2)).findByName(anyString());
    }

    @Test
    void updatePost_shouldReturnUpdatedPostDtoWithTags() {
        when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));
        when(postRepository.save(any(Post.class))).thenReturn(post);
        when(tagRepository.findByName("Java")).thenReturn(Optional.of(new Tag()));
        when(tagRepository.findByName("Docker")).thenReturn(Optional.empty());

        PostDto result = postService.updatePost(1L, updatePostRequest);

        assertNotNull(result);
        assertEquals(post.getId(), result.getId());
        verify(postRepository, times(1)).findById(anyLong());
        verify(postRepository, times(1)).save(any(Post.class));
        verify(tagRepository, times(2)).findByName(anyString());
    }

    @Test
    void getPostsByTagName_shouldReturnListOfPostDto() {
        when(postRepository.findByTags_Name("Java")).thenReturn(List.of(post));

        List<PostDto> result = postService.getPostsByTagName("Java");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(post.getId(), result.get(0).getId());
        verify(postRepository, times(1)).findByTags_Name("Java");
    }
}
