package com.example.blog.service;

import com.example.blog.dto.CreateTagRequest;
import com.example.blog.dto.TagDto;
import com.example.blog.entity.Tag;
import com.example.blog.repository.TagRepository;
import com.example.blog.service.impl.TagServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TagServiceTest {

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TagServiceImpl tagService;

    @Test
    void testCreateTag() {
        CreateTagRequest request = new CreateTagRequest();
        request.setName("Java");

        Tag tag = new Tag();
        tag.setId(1L);
        tag.setName("Java");

        when(tagRepository.save(any(Tag.class))).thenReturn(tag);

        TagDto result = tagService.createTag(request);

        assertEquals(1L, result.getId());
        assertEquals("Java", result.getName());
    }

    @Test
    void testGetAllTags() {
        Tag tag1 = new Tag();
        tag1.setId(1L);
        tag1.setName("Java");

        Tag tag2 = new Tag();
        tag2.setId(2L);
        tag2.setName("Spring");

        when(tagRepository.findAll()).thenReturn(List.of(tag1, tag2));

        List<TagDto> result = tagService.getAllTags();

        assertEquals(2, result.size());
        assertEquals("Java", result.get(0).getName());
        assertEquals("Spring", result.get(1).getName());
    }
}
