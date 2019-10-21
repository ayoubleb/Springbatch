package com.docaposte.toubkal.b2.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.batch.core.annotation.AfterProcess;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class TestAspect {
    //@Before("execution(* com.docaposte.toubkal.b2.B2_Factory.getControl(..))")
    @After("anyPublicMethod()")
    public void logTest(/*ProceedingJoinPoint pjp*//*, RequestMapping requestMapping*/) {
        //Before:
        System.out.println("before doStuff");

    }

    @Pointcut(value="execution(public * *(..))")
    public void anyPublicMethod() {
    }
}