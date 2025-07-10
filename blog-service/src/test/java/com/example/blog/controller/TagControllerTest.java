package com.example.blog.controller;

import com.example.blog.dto.CreateTagRequest;
import com.example.blog.dto.TagDto;
import com.example.blog.service.TagService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = TagController.class)
@AutoConfigureMockMvc(addFilters = false)
public class TagControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TagService tagService;

    @Autowired
    private ObjectMapper objectMapper;

    private TagDto tagDto;
    private CreateTagRequest createTagRequest;

    @BeforeEach
    void setUp() {
        tagDto = new TagDto();
        tagDto.setId(1L);
        tagDto.setName("Java");

        createTagRequest = new CreateTagRequest();
        createTagRequest.setName("Java");
    }

    @Test
    void testCreateTag() throws Exception {
        when(tagService.createTag(any(CreateTagRequest.class))).thenReturn(tagDto);

        mockMvc.perform(post("/api/tags")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createTagRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Java"));
    }

    @Test
    void testGetAllTags() throws Exception {
        TagDto tagDto2 = new TagDto();
        tagDto2.setId(2L);
        tagDto2.setName("Spring");

        when(tagService.getAllTags()).thenReturn(List.of(tagDto, tagDto2));

        mockMvc.perform(get("/api/tags"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Java"))
                .andExpect(jsonPath("$[1].name").value("Spring"));
    }
}
