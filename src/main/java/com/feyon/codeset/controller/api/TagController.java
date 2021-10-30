package com.feyon.codeset.controller.api;

import com.feyon.codeset.controller.result.Result;
import com.feyon.codeset.entity.Tag;
import com.feyon.codeset.service.TagService;
import com.feyon.codeset.vo.TagVO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Feng Yong
 */
@RestController
@RequestMapping("/api/v1/tags")
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
    public TagVO findById(@PathVariable Integer id) {
        return tagService.findOne(id);
    }

    @GetMapping
    public List<TagVO> findAll() {
        return tagService.findAll();
    }



}
