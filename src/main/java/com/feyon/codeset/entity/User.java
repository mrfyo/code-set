package com.feyon.codeset.entity;

import lombok.Data;
import org.springframework.lang.Nullable;

/**
 * @author Feng Yong
 */
@Data
public class User {

    private Integer id;

    private String username;

    private String password;

    @Nullable
    private UserProfile profile;
}
