package com.swm.lito.api.support.security;

import com.swm.lito.core.user.domain.enums.Authority;
import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.*;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@WithSecurityContext(factory = WithMockJwtSecurityContextFactory.class)
public @interface WithMockJwt {

    long id() default 1L;

    String email() default "test@test.com";

    Authority authority() default Authority.ROLE_USER;
}
