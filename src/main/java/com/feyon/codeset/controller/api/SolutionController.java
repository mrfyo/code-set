package com.feyon.codeset.controller.api;

import com.feyon.codeset.controller.result.Result;
import com.feyon.codeset.form.SolutionForm;
import com.feyon.codeset.query.SolutionQuery;
import com.feyon.codeset.service.SolutionService;
import com.feyon.codeset.vo.PageVO;
import com.feyon.codeset.vo.SolutionVO;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Feng Yong
 */
@RestController
@RequestMapping("/api/v1/solutions")
public class SolutionController {

    private final SolutionService questionService;

    public SolutionController(SolutionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping
    public Result addSolution(@RequestBody SolutionForm form) {
        questionService.save(form);
        return Result.success();
    }

    @PostMapping("/{solutionId}")
    public Result deleteSolution(@PathVariable Integer solutionId) {
        questionService.remove(solutionId);
        return Result.success();
    }

    @PutMapping("/{solutionId}")
    public Result editSolution(@PathVariable Integer solutionId,
                               @Valid @RequestBody SolutionForm form) {
        questionService.update(solutionId, form);
        return Result.success();
    }

    @GetMapping
    public PageVO<SolutionVO> querySolution(@Valid SolutionQuery urlQuery, @Valid @RequestBody(required = false) SolutionQuery query) {
        return questionService.listAll(query == null ? urlQuery : query);
    }
    
}
