package com.feyon.codeset.service;

import com.feyon.codeset.dto.CodeRunDTO;

/**
 * @author Feng Yong
 */
public interface CodeRunService {

    /**
     * run code in runtime
     *
     * @param code needed to be executed code
     * @return result.
     */
    CodeRunDTO run(String code);
}
