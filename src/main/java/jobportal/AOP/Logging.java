package jobportal.AOP;


import jobportal.Annotation.Authorization;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;



@Aspect
@Component
@Order(2)
public class Logging {
    private static final Logger log= LoggerFactory.getLogger(Logging.class);

    @Before("execution(* jobportal.Service.JobService..*(..))")
    public void BeforeLogging(JoinPoint joinPoint) {
        log.info("Starting Method: {},{}()"
                ,joinPoint.getSignature().getDeclaringType().getSimpleName()
                ,joinPoint.getSignature().getName()
        );


    }


    @AfterReturning(
            value = "within(jobportal.Service.JobService)",
            returning = "result")
    public void AfterLogging(JoinPoint joinPoint, Object result) {
        log.info("Method completed successfully: {},{}()| {}"
                ,joinPoint.getSignature().getDeclaringType().getSimpleName()
                ,joinPoint.getSignature().getName()
                ,result
        );
    }

    @AfterThrowing(value = "bean(jobService)|| bean(jobController)",
    throwing= "exception")
    public void ExceptionLogging(JoinPoint jointPoint,Throwable exception) {
        log.error("Method failed: {}.{}() | Exception: {} | Message: {}",
                jointPoint.getSignature().getDeclaringType().getSimpleName(),
                jointPoint.getSignature().getName(),
                exception.getClass().getSimpleName(),
                exception.getMessage()
        );
    }

    @Around("jobportal.AOP.AllPointCut.jobService()")
    public Object AllAroundLogging(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info(
                "Entering method: {}.{}()",
                joinPoint.getSignature().getDeclaringType().getSimpleName(),
                joinPoint.getSignature().getName()
        );
        long lastTime= System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long executionTime= System.currentTimeMillis()-lastTime;
        log.info(
                "Exiting method: {}.{}(). | Execution time: {} ms",
                joinPoint.getSignature().getDeclaringType().getSimpleName(),
                joinPoint.getSignature().getName(),
                executionTime
        );


        return result;

    }
}