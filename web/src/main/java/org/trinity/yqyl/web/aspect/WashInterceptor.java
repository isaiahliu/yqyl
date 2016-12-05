package org.trinity.yqyl.web.aspect;

import javax.validation.ConstraintViolationException;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.trinity.aspect.AbstractWashInterceptor;

@Component
@Aspect
public class WashInterceptor extends AbstractWashInterceptor {
	private static final String EXECUTION = "execution(* org.trinity..*.*(..,@org.trinity.common.dto.washer.OnWash (*),..))";

	@Override
	@Pointcut(EXECUTION)
	public void findWashAnnotation() {
	}

	@Override
	@Before("findWashAnnotation()")
	public void wash(final JoinPoint jp) throws ConstraintViolationException {
		super.wash(jp);
	}
}
