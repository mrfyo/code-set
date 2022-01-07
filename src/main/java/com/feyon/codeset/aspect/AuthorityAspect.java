package com.feyon.codeset.aspect;

import com.feyon.codeset.annotion.Authority;
import com.feyon.codeset.entity.Role;
import com.feyon.codeset.exception.AdminException;
import com.feyon.codeset.util.UserContext;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * 授权拦截切面
 * @author Feng Yong
 */
@Component
@Aspect
public class AuthorityAspect {

    @Before("@annotation(authority)")
    public void checkRole(Authority authority) {
        Role role = UserContext.getUser().getRole();
        if(role.getId() > authority.role()) {
            throw new AdminException("权限不够");
        }
    }
}
