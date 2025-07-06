package com.example.blog.service;

import com.example.blog.dto.CreatePostRequest;
import com.example.blog.dto.PostDto;
import com.example.blog.dto.UpdatePostRequest;
import com.example.blog.entity.Post;
import com.example.blog.repository.PostRepository;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {

    @Mock
    private PostRepository postRepository;

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

        updatePostRequest = new UpdatePostRequest();
        updatePostRequest.setTitle("Updated Title");
        updatePostRequest.setContent("Updated Content");
    }

    @Test
    void createPost_shouldReturnPostDto() {
        when(postRepository.save(any(Post.class))).thenReturn(post);

        PostDto result = postService.createPost(createPostRequest);

        assertNotNull(result);
        assertEquals(post.getId(), result.getId());
        assertEquals(post.getTitle(), result.getTitle());
        assertEquals(post.getContent(), result.getContent());
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    void getPostById_shouldReturnPostDto_whenPostExists() {
        when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));

        PostDto result = postService.getPostById(1L);

        assertNotNull(result);
        assertEquals(post.getId(), result.getId());
        assertEquals(post.getTitle(), result.getTitle());
        assertEquals(post.getContent(), result.getContent());
        verify(postRepository, times(1)).findById(anyLong());
    }

    @Test
    void getPostById_shouldThrowRuntimeException_whenPostDoesNotExist() {
        when(postRepository.findById(anyLong())).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            postService.getPostById(1L);
        });

        assertTrue(thrown.getMessage().contains("Post not found"));
        verify(postRepository, times(1)).findById(anyLong());
    }

    @Test
    void getAllPosts_shouldReturnListOfPostDto() {
        Post anotherPost = new Post();
        anotherPost.setId(2L);
        anotherPost.setTitle("Another Title");
        anotherPost.setContent("Another Content");

        List<Post> posts = Arrays.asList(post, anotherPost);
        when(postRepository.findAll()).thenReturn(posts);

        List<PostDto> result = postService.getAllPosts();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(postRepository, times(1)).findAll();
    }

    @Test
    void getAllPostsPaged_shouldReturnPageOfPostDto() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Post> postPage = new PageImpl<>(Arrays.asList(post), pageable, 1);
        when(postRepository.findAll(pageable)).thenReturn(postPage);

        Page<PostDto> result = postService.getAllPosts(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(1, result.getContent().size());
        verify(postRepository, times(1)).findAll(pageable);
    }

    @Test
    void updatePost_shouldReturnUpdatedPostDto_whenPostExists() {
        when(postRepository.findById(anyLong())).thenReturn(Optional.of(post));
        when(postRepository.save(any(Post.class))).thenReturn(post);

        PostDto result = postService.updatePost(1L, updatePostRequest);

        assertNotNull(result);
        assertEquals(post.getId(), result.getId());
        assertEquals(updatePostRequest.getTitle(), result.getTitle());
        assertEquals(updatePostRequest.getContent(), result.getContent());
        verify(postRepository, times(1)).findById(anyLong());
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    void updatePost_shouldThrowRuntimeException_whenPostDoesNotExist() {
        when(postRepository.findById(anyLong())).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            postService.updatePost(1L, updatePostRequest);
        });

        assertTrue(thrown.getMessage().contains("Post not found"));
        verify(postRepository, times(1)).findById(anyLong());
        verify(postRepository, never()).save(any(Post.class));
    }

    @Test
    void deletePost_shouldDeletePost_whenPostExists() {
        when(postRepository.existsById(anyLong())).thenReturn(true);
        doNothing().when(postRepository).deleteById(anyLong());

        postService.deletePost(1L);

        verify(postRepository, times(1)).existsById(anyLong());
        verify(postRepository, times(1)).deleteById(anyLong());
    }

    @Test
    void deletePost_shouldThrowRuntimeException_whenPostDoesNotExist() {
        when(postRepository.existsById(anyLong())).thenReturn(false);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            postService.deletePost(1L);
        });

        assertTrue(thrown.getMessage().contains("Post not found"));
        verify(postRepository, times(1)).existsById(anyLong());
        verify(postRepository, never()).deleteById(anyLong());
    }
}
