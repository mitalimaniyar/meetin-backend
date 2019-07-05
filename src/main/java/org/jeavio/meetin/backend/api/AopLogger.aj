package org.jeavio.meetin.backend.analyticsaop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public aspect AopLogger{
    
	private static Logger log = LoggerFactory.getLogger("Analytics Logger");
    pointcut move() : execution(public * org.jeavio.meetin.*.*.*(..));
    
    pointcut exc() : handler(java.lang.Exception);
    
    before() : exc() {
        log.info("Handling Exception occured : "+thisJoinPoint);
    }
    
    before() : move() {
        log.info("Entering : "+thisJoinPoint.getSignature().getName());
        if(thisJoinPoint.getArgs().length>0)
            for(Object arg:thisJoinPoint.getArgs())
            {
            	log.info("Arguments: "+arg);
            }
    }
        	
    after() : move() {
    	log.info("Exiting : "+thisJoinPoint.getSignature().getName());
    }
    
    after() returning(Object result) : move() {
        log.info("Returns "+thisJoinPoint.getSignature().getName()+"  : "+(result==null?"Null":result.getClass().getSimpleName()));
    }
    
}



