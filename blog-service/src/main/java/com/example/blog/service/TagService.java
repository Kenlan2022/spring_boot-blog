package com.example.blog.service;

import com.example.blog.dto.CreateTagRequest;
import com.example.blog.dto.TagDto;

import java.util.List;

public interface TagService {
    TagDto createTag(CreateTagRequest createTagRequest);

    List<TagDto> getAllTags();
}
