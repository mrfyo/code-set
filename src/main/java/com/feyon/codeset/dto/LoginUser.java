package com.feyon.codeset.dto;

import com.feyon.codeset.entity.Role;
import lombok.Data;

@Data
public class LoginUser {

    private Integer id;

    private Role role;
}
