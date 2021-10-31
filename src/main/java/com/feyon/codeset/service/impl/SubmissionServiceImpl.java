package com.feyon.codeset.service.impl;

import com.feyon.codeset.dto.CodeRunDTO;
import com.feyon.codeset.entity.Submission;
import com.feyon.codeset.entity.SubmissionDetail;
import com.feyon.codeset.entity.SubmissionLanguage;
import com.feyon.codeset.exception.EntityException;
import com.feyon.codeset.form.SubmissionForm;
import com.feyon.codeset.mapper.QuestionMapper;
import com.feyon.codeset.mapper.SubmissionDetailMapper;
import com.feyon.codeset.mapper.SubmissionLanguageMapper;
import com.feyon.codeset.mapper.SubmissionMapper;
import com.feyon.codeset.query.SubmissionQuery;
import com.feyon.codeset.service.CodeRunService;
import com.feyon.codeset.service.SubmissionService;
import com.feyon.codeset.util.ModelMapperUtil;
import com.feyon.codeset.vo.SubmissionVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Feng Yong
 */
@Service
@RequiredArgsConstructor
public class SubmissionServiceImpl implements SubmissionService {

    private final SubmissionMapper submissionMapper;

    private final SubmissionLanguageMapper submissionLanguageMapper;

    private final SubmissionDetailMapper submissionContentMapper;

    private final CodeRunService codeRunService;

    private final QuestionMapper questionMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(SubmissionForm form) {
        submissionLanguageMapper.findById(form.getLanguageId()).orElseThrow(() -> new EntityException("提交语言不存在"));
        if (!questionMapper.existsById(form.getQuestionId())) {
            throw new EntityException("问题不存在");
        }

        CodeRunDTO codeRunDTO = runCode(form.getContent());

        Submission submission = new Submission();
        submission.setUserId(form.getUserId());
        submission.setQuestionId(form.getQuestionId());
        submission.setLanguageId(form.getLanguageId());
        submission.setCreateAt(LocalDateTime.now());
        submission.setResult(codeRunDTO.getResult());
        submission.setTimeCost(codeRunDTO.getTimeCost());
        submission.setMemoryCost(codeRunDTO.getMemoryCost());
        submissionMapper.insert(submission);

        SubmissionDetail detail = new SubmissionDetail();
        detail.setSubmissionId(submission.getId());
        detail.setContent(form.getContent());
        submissionContentMapper.insert(detail);
    }

    public CodeRunDTO runCode(String code) {
        return codeRunService.run(code);
    }


    @Override
    public List<SubmissionVO> listAll(SubmissionQuery query) {
        Submission example = new Submission();
        example.setQuestionId(query.getQuestionId());
        example.setUserId(query.getUserId());
        example.setLanguageId(query.getLanguageId());
        List<Submission> submissions = submissionMapper.findAllByExample(example);
        Set<Integer> langIds = submissions.stream()
                .map(Submission::getLanguageId)
                .collect(Collectors.toSet());

        if (langIds.size() == 1) {
            String name = submissionLanguageMapper.findById(langIds.iterator().next())
                    .map(SubmissionLanguage::getName)
                    .orElse("");
            return submissions.stream()
                    .map(submission -> ModelMapperUtil.map(submission, SubmissionVO.class))
                    .peek(submissionVO -> submissionVO.setLanguage(name))
                    .collect(Collectors.toList());
        } else {
            List<SubmissionLanguage> languages = submissionLanguageMapper.findAllById(langIds);
            List<SubmissionVO> vos = new ArrayList<>(submissions.size());
            for (Submission submission : submissions) {
                SubmissionVO vo = ModelMapperUtil.map(submission, SubmissionVO.class);
                for (SubmissionLanguage lang : languages) {
                    if (submission.getLanguageId().equals(lang.getId())) {
                        vo.setLanguage(lang.getName());
                        break;
                    } else {
                        vo.setLanguage("");
                    }
                }
                vos.add(vo);
            }
            return vos;
        }
    }
}
