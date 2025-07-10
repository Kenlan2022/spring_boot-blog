package com.example.blog.service.impl;

import com.example.blog.dto.CreateTagRequest;
import com.example.blog.dto.TagDto;
import com.example.blog.entity.Tag;
import com.example.blog.repository.TagRepository;
import com.example.blog.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

    private final TagRepository tagRepository;

    @Override
    public TagDto createTag(CreateTagRequest createTagRequest) {
        Tag tag = new Tag();
        tag.setName(createTagRequest.getName());
        Tag savedTag = tagRepository.save(tag);
        return convertToDto(savedTag);
    }

    @Override
    public List<TagDto> getAllTags() {
        return tagRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private TagDto convertToDto(Tag tag) {
        TagDto tagDto = new TagDto();
        tagDto.setId(tag.getId());
        tagDto.setName(tag.getName());
        return tagDto;
    }
}
