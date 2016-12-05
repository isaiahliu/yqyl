package org.trinity.yqyl.rest.controller.advice;

import javax.validation.ConstraintViolationException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.trinity.common.dto.IResponse;
import org.trinity.common.dto.response.DefaultResponse;
import org.trinity.common.exception.LocalizedException;
import org.trinity.common.exception.factory.IExceptionFactory;
import org.trinity.message.IMessageResolverChain;
import org.trinity.message.exception.GeneralErrorMessage;
import org.trinity.rest.controller.AbstractRestController;

@ControllerAdvice
public class RestControllerAdvice extends AbstractRestController {
	private static Logger logger = LogManager.getLogger(RestControllerAdvice.class);

	@Autowired
	private IExceptionFactory exceptionFactory;

	@Autowired
	private IMessageResolverChain messageResolver;

	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public ResponseEntity<IResponse> processException(final ConstraintViolationException e) {
		final DefaultResponse response = new DefaultResponse();

		e.getConstraintViolations().stream().map(item -> item.getMessage()).forEach(item -> response.addError("", item));

		logger.error(e.getMessage(), e);

		return createResponseEntity(response, HttpStatus.OK);
	}

	@ExceptionHandler(LocalizedException.class)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public ResponseEntity<IResponse> processException(final LocalizedException e) {
		final DefaultResponse response = new DefaultResponse();

		e.getErrorMessages().forEach(
				item -> response.addError(item.getItem1().getMessageCode(), messageResolver.getMessage(item.getItem1(), item.getItem2())));

		logger.error(e.getMessage(), e);

		return createResponseEntity(response, HttpStatus.OK);
	}

	@ExceptionHandler(Throwable.class)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public ResponseEntity<IResponse> processException(final Throwable e) {
		final DefaultResponse response = new DefaultResponse();

		response.addError(GeneralErrorMessage.UNKNOWN_EXCEPTION.getMessageCode(), exceptionFactory
				.createException(GeneralErrorMessage.UNKNOWN_EXCEPTION, e.getClass().getName(), e.getMessage()).getMessage());

		logger.error(e.getMessage(), e);

		return createResponseEntity(response, HttpStatus.OK);
	}

}
