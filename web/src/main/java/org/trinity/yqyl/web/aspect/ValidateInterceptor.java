package org.trinity.yqyl.web.aspect;

import javax.validation.ConstraintViolationException;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.trinity.aspect.AbstractValidateInterceptor;

@Aspect
@Component
public class ValidateInterceptor extends AbstractValidateInterceptor {
	private static final String EXECUTION = "execution(* org.trinity..*.*(..,@org.trinity.common.dto.validator.OnValid (*),..))";

	@Autowired
	public ValidateInterceptor(final LocalValidatorFactoryBean validator) {
		super(validator);
	}

	@Override
	@Pointcut(EXECUTION)
	public void findValidateAnnotation() {
	}

	@Override
	@Before("findValidateAnnotation()")
	public void validate(final JoinPoint jp) throws ConstraintViolationException {
		super.validate(jp);
	}
}
