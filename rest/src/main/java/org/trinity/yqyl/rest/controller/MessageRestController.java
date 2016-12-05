package org.trinity.yqyl.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.trinity.yqyl.common.message.dto.domain.MessageDto;
import org.trinity.yqyl.common.message.dto.domain.MessageSearchingDto;
import org.trinity.yqyl.common.message.dto.request.MessageRequest;
import org.trinity.yqyl.common.message.dto.response.MessageResponse;
import org.trinity.yqyl.process.controller.base.IMessageProcessController;

@RestController
@RequestMapping("/message/message")
public class MessageRestController extends
        AbstractApplicationAwareCrudRestController<MessageDto, MessageSearchingDto, IMessageProcessController, MessageRequest, MessageResponse> {

    @Override
    protected MessageResponse createResponseInstance() {
        return new MessageResponse();
    }
}
