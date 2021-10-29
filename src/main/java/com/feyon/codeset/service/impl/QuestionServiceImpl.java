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
import java.util.Set;
import java.util.function.Predicate;
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
        Predicate<Question> filter = QuestionFilter.build()
                .and(new DifficultyFilter(query))
                .and(new UserStatusFilter(query))
                .and(new TagFilter(query));

        List<Question> questions = listAll().stream()
                .filter(filter)
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

    /**
     * Filter: Question.Difficulty
     */
    private static class DifficultyFilter implements QuestionFilter {

        private final QuestionQuery query;

        private DifficultyFilter(QuestionQuery query) {
            this.query = query;
        }

        @Override
        public boolean test(Question question) {
            return ObjectUtils.isEmpty(query.getDifficulty()) || question.getDifficulty().equals(query.getDifficulty());
        }
    }

    /**
     * Filter: User Status
     */
    private class UserStatusFilter implements QuestionFilter {

        private final QuestionQuery query;

        private Set<Integer> questionSet;

        private UserStatusFilter(QuestionQuery query) {
            this.query = query;
        }

        @Override
        public boolean test(Question question) {
            return ObjectUtils.isEmpty(query.getStatus()) || contains(question, query);
        }

        private boolean contains(Question question, QuestionQuery query) {
            if (questionSet == null) {
                questionSet = questionMapper.listAllForUser(1, query.getStatus());
            }
            return questionSet.contains(question.getId());
        }
    }

    /**
     * Filter: Question Tags
     */
    private class TagFilter implements QuestionFilter {

        private final QuestionQuery query;

        private Set<Integer> questionSet;

        private TagFilter(QuestionQuery query) {
            this.query = query;
        }

        @Override
        public boolean test(Question question) {
            return ObjectUtils.isEmpty(query.getTags()) || contains(question, query);
        }

        private boolean contains(Question question, QuestionQuery query) {
            if (questionSet == null) {
                questionSet = questionMapper.listAllForTag(query.getTags());
            }
            return questionSet.contains(question.getId());
        }
    }

    /**
     * the pass rate of question for user.
     */
    @Component
    public static class SimplePassRateQueryer implements PassRateQueryer {

        private final SubmissionMapper submissionMapper;

        public SimplePassRateQueryer(SubmissionMapper submissionMapper) {
            this.submissionMapper = submissionMapper;
        }

        @Override
        public double query(Integer questionId) {
            long total = submissionMapper.countByExample(new Submission(null, questionId, null));
            if (total == 0) {
                return 0;
            }
            long pass = submissionMapper.countByExample(new Submission(1, questionId, null));
            long result = (pass * 1000 / total);
            return result / 10.0;
        }
    }
}
