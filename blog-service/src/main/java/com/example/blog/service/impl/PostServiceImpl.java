package com.example.blog.service.impl;

import com.example.blog.dto.CreatePostRequest;
import com.example.blog.dto.PostDto;
import com.example.blog.dto.TagDto;
import com.example.blog.dto.UpdatePostRequest;
import com.example.blog.entity.Post;
import com.example.blog.entity.Tag;
import com.example.blog.repository.PostRepository;
import com.example.blog.repository.TagRepository;
import com.example.blog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final TagRepository tagRepository;
    private final Parser markdownParser = Parser.builder().build();
    private final HtmlRenderer htmlRenderer = HtmlRenderer.builder().escapeHtml(true).build();

    @Override
    public PostDto createPost(CreatePostRequest createPostRequest) {
        Post post = new Post();
        post.setTitle(createPostRequest.getTitle());
        post.setContent(createPostRequest.getContent());

        Set<Tag> tags = getTagsFromRequest(createPostRequest.getTags());
        post.setTags(tags);

        Post savedPost = postRepository.save(post);

        return convertToDto(savedPost);
    }

    @Override
    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + id));
        return convertToDto(post);
    }

    @Override
    public List<PostDto> getAllPosts() {
        return postRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Page<PostDto> getAllPosts(Pageable pageable) {
        Page<Post> postPage = postRepository.findAll(pageable);
        List<PostDto> postDtos = postPage.getContent().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
        return new PageImpl<>(postDtos, pageable, postPage.getTotalElements());
    }

    @Override
    public PostDto updatePost(Long id, UpdatePostRequest updatePostRequest) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + id));

        post.setTitle(updatePostRequest.getTitle());
        post.setContent(updatePostRequest.getContent());

        Set<Tag> tags = getTagsFromRequest(updatePostRequest.getTags());
        post.setTags(tags);

        Post updatedPost = postRepository.save(post);
        return convertToDto(updatedPost);
    }

    @Override
    public void deletePost(Long id) {
        if (!postRepository.existsById(id)) {
            throw new RuntimeException("Post not found with id: " + id);
        }
        postRepository.deleteById(id);
    }

    @Override
    public List<PostDto> getPostsByTagName(String tagName) {
        return postRepository.findByTags_Name(tagName).stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private Set<Tag> getTagsFromRequest(Set<String> tagNames) {
        if (tagNames == null || tagNames.isEmpty()) {
            return new HashSet<>();
        }

        return tagNames.stream()
                .map(tagName -> tagRepository.findByName(tagName)
                        .orElseGet(() -> {
                            Tag newTag = new Tag();
                            newTag.setName(tagName);
                            return newTag;
                        }))
                .collect(Collectors.toSet());
    }

    private PostDto convertToDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());

        // Convert Markdown to HTML
        Node document = markdownParser.parse(post.getContent());
        String html = htmlRenderer.render(document);
        postDto.setHtmlContent(html);

        if (post.getTags() != null) {
            postDto.setTags(post.getTags().stream()
                    .map(this::convertTagToDto)
                    .collect(Collectors.toSet()));
        }

        return postDto;
    }

    private TagDto convertTagToDto(Tag tag) {
        TagDto tagDto = new TagDto();
        tagDto.setId(tag.getId());
        tagDto.setName(tag.getName());
        return tagDto;
    }
}
