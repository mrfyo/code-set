package com.feyon.codeset.service;

import com.feyon.codeset.form.SolutionForm;
import com.feyon.codeset.query.SolutionQuery;
import com.feyon.codeset.vo.PageVO;
import com.feyon.codeset.vo.SolutionVO;

/**
 * @author Feng Yong
 */
public interface SolutionService {

    /**
     * save a solution
     * @param form {@link SolutionForm}
     */
    void save(SolutionForm form);

    /**
     * update a solution
     * @param solutionId solution ID
     * @param form {@link SolutionForm}
     */
    void update(Integer solutionId, SolutionForm form);

    /**
     * remove a solution
     * @param solutionId solution ID
     */
    void remove(Integer solutionId);

    /**
     * return all solution info.
     * @param query {@link SolutionQuery}
     * @return all solution info.
     */
    PageVO<SolutionVO> listAll(SolutionQuery query);

}
