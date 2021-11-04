package com.feyon.codeset.controller.api;

import com.feyon.codeset.controller.result.Result;
import com.feyon.codeset.form.QuestionForm;
import com.feyon.codeset.query.QuestionQuery;
import com.feyon.codeset.service.QuestionLikeService;
import com.feyon.codeset.service.QuestionService;
import com.feyon.codeset.vo.PageVO;
import com.feyon.codeset.vo.QuestionDetailVO;
import com.feyon.codeset.vo.QuestionVO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @author Feng Yong
 */
@RestController
@RequestMapping(value = "/api/v1/questions", produces = MediaType.APPLICATION_JSON_VALUE)
public class QuestionController implements QuestionApi {

    private final QuestionService questionService;

    private final QuestionLikeService questionLikeService;

    public QuestionController(QuestionService questionService, QuestionLikeService questionLikeService) {
        this.questionService = questionService;
        this.questionLikeService = questionLikeService;
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
                               @Valid @RequestBody QuestionForm form) {
        questionService.update(questionId, form);
        return Result.success();
    }

    @GetMapping("/{questionId}")
    public QuestionDetailVO queryQuestion(@PathVariable Integer questionId) {
        return questionService.findOne(questionId);
    }

    @GetMapping
    public PageVO<QuestionVO> listQuestions(@Valid QuestionQuery urlQuery, @Valid @RequestBody(required = false) QuestionQuery query) {
        return questionService.listAll(query == null ? urlQuery : query);
    }

    @PostMapping("/like/{questionId}")
    public Result likeQuestion(@PathVariable Integer questionId) {
        questionLikeService.like(questionId);
        return Result.success();
    }

    @DeleteMapping("/like/{questionId}")
    public Result unlikeQuestion(@PathVariable Integer questionId) {
        questionLikeService.unlike(questionId);
        return Result.success();
    }
}
