package com.feyon.codeset.controller.api;

import com.feyon.codeset.controller.result.Result;
import com.feyon.codeset.form.SubmissionForm;
import com.feyon.codeset.service.SubmissionService;
import com.feyon.codeset.vo.SubmissionVO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Feng Yong
 */
@RestController
@RequestMapping("/api/v1/questions")
public class SubmissionController implements SubmissionApi{

    private final SubmissionService submissionService;

    public SubmissionController(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    @PostMapping("/submissions")
    public Result saveSubmission(SubmissionForm submissionForm) {
        submissionService.save(submissionForm);
        return Result.success();
    }

    @GetMapping("/{questionId}/submissions")
    public List<SubmissionVO> listSubmissions(@PathVariable Integer questionId) {
        return submissionService.listAll(questionId);
    }
}
