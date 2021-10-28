package com.feyon.codeset.service.impl;

import com.feyon.codeset.entity.Question;
import com.feyon.codeset.entity.Solution;
import com.feyon.codeset.entity.Submission;
import com.feyon.codeset.mapper.QuestionMapper;
import com.feyon.codeset.mapper.SolutionMapper;
import com.feyon.codeset.mapper.SubmissionMapper;
import com.feyon.codeset.query.QuestionQuery;
import com.feyon.codeset.service.QuestionService;
import com.feyon.codeset.util.ModelMapperUtil;
import com.feyon.codeset.util.NullUtil;
import com.feyon.codeset.vo.PageVO;
import com.feyon.codeset.vo.QuestionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Feng Yong
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionMapper questionMapper;

    private final SolutionMapper solutionMapper;

    private PassRateQueryer passRateQueryer;


    public QuestionServiceImpl(QuestionMapper questionMapper, SolutionMapper solutionMapper) {
        this.questionMapper = questionMapper;
        this.solutionMapper = solutionMapper;
    }

    @Autowired
    public void setPassRateQueryer(PassRateQueryer passRateQueryer) {
        this.passRateQueryer = passRateQueryer;
    }

    /**
     * query question according to conditions. <br>
     * <p>
     * why find all from data source ? <br>
     * Firstly, when the number of question in database is less, the time consuming can be ignored. <br>
     * Secondly, when the number of question in database is large, we should try cache to reduce the query duration. Because the action that db execute page also need to scan all data. <br>
     * Mostly, the conditions is too much and too flexible !!!
     * </p>
     *
     * @param query {@link QuestionQuery}
     * @return all question.
     */
    @Override
    public PageVO<QuestionVO> listAll(QuestionQuery query) {
        Integer userId = 1;
        List<Question> questions = listAll().stream()
                .filter(question -> ObjectUtils.isEmpty(query.getDifficulty()) || question.getDifficulty().equals(query.getDifficulty()))
                .filter(question -> ObjectUtils.isEmpty(query.getStatus()) || questionMapper.holdStatus(question.getId(), userId, query.getStatus()))
                .filter(question -> ObjectUtils.isEmpty(query.getTags()) || questionMapper.containsTag(question.getId(), query.getTags()))
                .collect(Collectors.toList());

        List<QuestionVO> vos = new ArrayList<>(query.getLimit());
        for (int i = query.getOffset(); i < questions.size(); i++) {
            QuestionVO vo = ModelMapperUtil.map(questions.get(i), QuestionVO.class);
            vo.setStatus(NullUtil.defaultValue(query.getStatus(), 0));
            vo.setResolutionNum(solutionNumberForQuestion(vo.getQuestionId()));
            vo.setPassRate(passRateQueryer.query(vo.getQuestionId()));
            vos.add(vo);
        }
        return PageVO.of(questions.size(), vos);
    }

    public List<Question> listAll() {
        return questionMapper.findAll();
    }

    private long solutionNumberForQuestion(Integer questionId) {
        return solutionMapper.countByExample(new Solution(questionId, null));
    }

    @Component
    public static class SimplePassRateQueryer implements PassRateQueryer {

        private final SubmissionMapper submissionMapper;

        public SimplePassRateQueryer(SubmissionMapper submissionMapper) {
            this.submissionMapper = submissionMapper;
        }

        @Override
        public double query(Integer questionId) {
            long total = submissionMapper.countByExample(new Submission(null, questionId, null));
            if(total == 0) {
                return  0;
            }
            long pass = submissionMapper.countByExample(new Submission(1, questionId, null));
            long result = (pass * 1000 / total);
            return result / 10.0;
        }
    }
}
