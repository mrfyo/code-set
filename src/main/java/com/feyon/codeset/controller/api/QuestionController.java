package com.feyon.codeset.controller.api;

import com.feyon.codeset.controller.result.Result;
import com.feyon.codeset.form.QuestionForm;
import com.feyon.codeset.query.QuestionQuery;
import com.feyon.codeset.service.QuestionService;
import com.feyon.codeset.vo.PageVO;
import com.feyon.codeset.vo.QuestionVO;
import org.springframework.web.bind.annotation.*;

/**
 * @author Feng Yong
 */
@RestController
public class QuestionController {

    private final QuestionService questionService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping
    public Result addQuestion(@RequestBody QuestionForm form) {
        questionService.save(form);
        return Result.success();
    }

    @PostMapping("/{questionId}")
    public Result deleteQuestion(@PathVariable Integer questionId) {
        questionService.remove(questionId);
        return Result.success();
    }

    @PutMapping("/{questionId}")
    public Result editQuestion(@PathVariable Integer questionId,
                               @RequestBody QuestionForm form) {
        questionService.update(questionId, form);
        return Result.success();
    }

    @GetMapping
    public PageVO<QuestionVO> queryQuestion(@RequestBody QuestionQuery query) {
        return questionService.listAll(query);
    }
}
