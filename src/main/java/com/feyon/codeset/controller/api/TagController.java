package com.feyon.codeset.controller.api;

import com.feyon.codeset.controller.result.Result;
import com.feyon.codeset.entity.Tag;
import com.feyon.codeset.service.TagService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Feng Yong
 */
@RestController
public class TagController {

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping
    public Result addTag(@Valid @RequestBody Tag tag) {
        tagService.save(tag);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result editTag(@PathVariable Integer id,
                          @Valid @RequestBody Tag tag) {
        tagService.update(id, tag);
        return Result.success();
    }

    @PutMapping("/{id}")
    public Result deleteTag(@PathVariable Integer id) {
        tagService.remove(id);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result findAll(@PathVariable(required = false) Integer id) {
        return id == null ? Result.success(tagService.findOne(id)) : Result.success(tagService.findAll());
    }

}
