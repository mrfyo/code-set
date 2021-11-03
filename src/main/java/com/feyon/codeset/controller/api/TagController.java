package com.feyon.codeset.controller.api;

import com.feyon.codeset.controller.result.Result;
import com.feyon.codeset.form.TagForm;
import com.feyon.codeset.service.TagService;
import com.feyon.codeset.vo.TagVO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Feng Yong
 */
@RestController
@RequestMapping(value = "/api/v1/tags", produces = MediaType.APPLICATION_JSON_VALUE)
public class TagController implements TagApi{

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping
    public Result addTag(@Valid @RequestBody TagForm tag) {
        tagService.save(tag);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result editTag(@PathVariable Integer id,
                          @Valid @RequestBody TagForm tagForm) {
        tagService.update(id, tagForm);
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
