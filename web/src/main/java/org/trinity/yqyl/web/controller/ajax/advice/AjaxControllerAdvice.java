package org.trinity.yqyl.web.controller.ajax.advice;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
import org.trinity.message.IMessageResolverChain;
import org.trinity.rest.controller.AbstractRestController;

@ControllerAdvice("org.trinity.yqyl.web.controller.ajax")
public class AjaxControllerAdvice extends AbstractRestController {
	private static Logger logger = LogManager.getLogger(AjaxControllerAdvice.class);

	@Autowired
	private IMessageResolverChain messageResolver;

	@ExceptionHandler(LocalizedException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ResponseBody
	public ResponseEntity<IResponse> processException(final LocalizedException e) {
		final DefaultResponse response = new DefaultResponse();

		if (e.getErrorMessages().isEmpty()) {
			e.getErrorMessages().forEach(item -> response.addError(item.getItem1().getMessageCode(),
					messageResolver.getMessage(item.getItem1(), item.getItem2())));
		} else {
			response.addError("", e.getMessage());
		}
		logger.error(e.getMessage());

		return createResponseEntity(response, HttpStatus.BAD_REQUEST);
	}
}
