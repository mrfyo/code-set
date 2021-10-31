package com.feyon.codeset.service;

import com.feyon.codeset.form.SubmissionForm;
import com.feyon.codeset.query.SubmissionQuery;
import com.feyon.codeset.vo.SubmissionVO;

import java.util.List;

/**
 * @author Feng Yong
 */
public interface SubmissionService {

    /**
     * save a Submission
     * @param form submission form
     */
    void save(SubmissionForm form);

    /**
     * return all {@link SubmissionVO}
     * @param query {@link SubmissionQuery}
     * @return all {@link SubmissionVO}
     */
    List<SubmissionVO> listAll(SubmissionQuery query);
}
