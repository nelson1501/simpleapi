package com.kalkivera.apps.simpleapi.config;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 
 * Aspect for logging
 * 
 * This class will log entering, exiting and execution time of methods and log
 * exceptions
 *
 */
@Component
@Aspect
public class LoggerAspect {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Pointcut("within(com.kalkivera.apps.simpleapi.service..*)")
	public void allServiceMethods() {
	};

	@Pointcut("within(com.kalkivera.apps.simpleapi.dao..*)")
	public void allRepositoryMethods() {
	};

	@Pointcut("within(com.kalkivera.apps.simpleapi.resource..*)")
	public void allControllerMethods() {
	};

	@Pointcut("within(com.kalkivera.apps.simpleapi.util..*)")
	public void allUtilMethods() {
	};

	@Pointcut("allServiceMethods()")
	public void exceptionGroup() {
	};

	@Pointcut("allRepositoryMethods() || allControllerMethods() || allServiceMethods()")
	public void executionTimeGroup() {
	};

	/**
	 * This method will log entering, exiting, execution time
	 * 
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	@Around("executionTimeGroup()")
	public Object logEnterExitExecution(ProceedingJoinPoint pjp) throws Throwable {
		String arguments = Arrays.toString(pjp.getArgs());
		log.debug("Entering {}.{} args:{} ", pjp.getTarget().getClass().getName(), pjp.getSignature().getName(),
				arguments);
		long start = System.currentTimeMillis();
		Object retval = pjp.proceed();
		log.info("Execution of {}.{} args:{} took {} ms", pjp.getTarget().getClass(), pjp.getSignature().getName(),
				arguments, System.currentTimeMillis() - start);
		log.debug("Exiting {}.{} args:{} ", pjp.getTarget().getClass().getName(), pjp.getSignature().getName(),
				arguments);
		return retval;
	}

	/**
	 * Log exceptions
	 * 
	 * @param joinPoint
	 * @param ex
	 */
	@AfterThrowing(pointcut = "exceptionGroup()", throwing = "ex")
	public void afterThrowingException(JoinPoint joinPoint, Throwable ex) {
		String arguments = Arrays.toString(joinPoint.getArgs());
		log.error("Exception Occurred in {}.{} args:{} ", joinPoint.getTarget().getClass().getName(),
				joinPoint.getSignature().getName(), arguments, ex);

	}

}
