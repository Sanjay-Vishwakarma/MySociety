package com.pz.mysociety.logger;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
public class SocietyLog {

//    @Before("execution(public * com.paymentz.controller.UserController.processUser(..))")
//    public void log(){
//       log.info("method invoked processUser");
//    }

    @Pointcut(value = "execution(* com.pz.mysociety.*.*.*(..))")
    public void myPointcut(){}

    @Pointcut(value = "execution(* com.pz.mysociety.*.*.*.*(..))")
    public void myMethodPoint(){}

    @Around("myPointcut()")
    public Object applicationLogger(ProceedingJoinPoint pjp) throws Throwable{
//        ObjectMapper mapper = new ObjectMapper();
        String methodName = pjp.getSignature().getName();
        String className = pjp.getTarget().getClass().toString();
        Object[] argument = pjp.getArgs();
        String request = Arrays.toString(argument);


//        String a = mapper.writeValueAsString(argument);

        log.info("method invoked "+className+" : "+methodName+"()" + "  Request : " + request);

        Object object = pjp.proceed();
        log.info("method invoked "+className+" : "+methodName+"()"+"  Response : " + object);
        return object;
    }

    @Around("myMethodPoint()")
    public Object packageLogger(ProceedingJoinPoint pjp) throws Throwable{
//        ObjectMapper mapper = new ObjectMapper();
        String methodName = pjp.getSignature().getName();
        String className = pjp.getTarget().getClass().toString();
        Object[] argument = pjp.getArgs();
        String request = Arrays.toString(argument);


//        String a = mapper.writeValueAsString(argument);

        log.info("method invoked "+className+" : "+methodName+"()" + "  Request : " + request);

        Object object = pjp.proceed();
        log.info("method invoked "+className+" : "+methodName+"()"+"  Response : " + object);
        return object;
    }
}
