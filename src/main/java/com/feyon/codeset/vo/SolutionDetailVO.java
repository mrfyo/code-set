package com.feyon.codeset.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author Feng Yong
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SolutionDetailVO extends SolutionVO{

    private String solutionContent;

}
