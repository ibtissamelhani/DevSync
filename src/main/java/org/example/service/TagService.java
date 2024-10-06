package org.example.service;

import org.example.model.entities.Tag;
import org.example.repository.interfaces.TagRepository;

import java.util.List;
import java.util.Optional;

public class TagService {

    TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Optional<Tag> findById(Long id) {
        return tagRepository.findById(id);
    }


    public List<Tag> findAll() {
        return tagRepository.findAll();
    }


    public void createTag(Tag tag) {
         tagRepository.save(tag);
    }

    public void updateTag(Tag tag) {
             tagRepository.update(tag);
    }

    public void deleteTag(Long tagId) {
        Optional<Tag> existingTag = tagRepository.findById(tagId);
        existingTag.ifPresent(tagRepository::delete);
    }

    public Optional<Tag> findByName(String name) {
        return tagRepository.findByName(name);
    }

}
