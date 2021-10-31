package com.feyon.codeset.service;

import com.feyon.codeset.query.SolutionQuery;
import com.feyon.codeset.vo.PageVO;
import com.feyon.codeset.vo.SolutionVO;

/**
 * @author Feng Yong
 */
public interface SolutionService {

    /**
     * return all solution info.
     * @param query {@link SolutionQuery}
     * @return all solution info.
     */
    PageVO<SolutionVO> listAll(SolutionQuery query);

}
