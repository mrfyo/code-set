package com.feyon.codeset.entity;

import com.feyon.codeset.util.NullUtil;
import lombok.Data;

/**
 * @author Feng Yong
 */
@Data
public class QuestionStatistic {

    private Integer id;

    private Long solution;

    private Long successSubmission;

    private Long failSubmission;



    public double getPassRate() {
        long success = NullUtil.defaultValue(successSubmission, 0L);
        long total = success + NullUtil.defaultValue(failSubmission, 0L);
        return total == 0 ? 0 : Math.round(success * 1000.0 / total) / 10.0;
    }
}
