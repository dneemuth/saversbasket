package com.sb.web.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;

@Aspect
public class AuthenticationManagerAspect {
	
	@AfterReturning(pointcut="execution(* org.springframework.security.authentication.AuthenticationManager.authenticate(..))"
            ,returning="result")
    public void after(JoinPoint joinPoint,Object result) throws Throwable {
        System.out.println(">>> user: " + ((Authentication) result).getName());
    }
}
