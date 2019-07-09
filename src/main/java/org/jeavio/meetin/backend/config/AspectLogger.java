package org.jeavio.meetin.backend.config;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AspectLogger {

	private static Logger log = LoggerFactory.getLogger(AspectLogger.class);

	@Pointcut("execution(* org.jeavio.meetin.backend.service..*.*(..))")
	public void serviceCalls() {}

	@Pointcut("execution(* org.jeavio.meetin.backend.controller.*.*(..))")
	public void restCalls() {}

	@Before("serviceCalls() || restCalls()")
	public void before(JoinPoint thisJoinPoint) {
		
		log = LoggerFactory.getLogger(thisJoinPoint.getTarget().getClass());
		
		log.debug("Calling " + thisJoinPoint.getSignature().toString());
		if (thisJoinPoint.getArgs().length > 0)
			log.debug("Arguments : " + Arrays.asList(thisJoinPoint.getArgs()));

	}

	@AfterReturning(pointcut = "serviceCalls() || restCalls()" , returning = "retVal")
	public void after(JoinPoint thisJoinPoint, Object retVal) {
		
		log = LoggerFactory.getLogger(thisJoinPoint.getTarget().getClass());
		
		if(retVal!=null)
			log.debug("Returning : " + retVal);
	}
}
