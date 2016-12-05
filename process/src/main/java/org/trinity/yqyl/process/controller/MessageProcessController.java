package org.trinity.yqyl.process.controller;

import org.springframework.stereotype.Service;
import org.trinity.yqyl.common.message.dto.domain.MessageDto;
import org.trinity.yqyl.common.message.dto.domain.MessageSearchingDto;
import org.trinity.yqyl.process.controller.base.AbstractAutowiredCrudProcessController;
import org.trinity.yqyl.process.controller.base.IMessageProcessController;
import org.trinity.yqyl.repository.business.dataaccess.IMessageRepository;
import org.trinity.yqyl.repository.business.entity.Message;

@Service
public class MessageProcessController
        extends AbstractAutowiredCrudProcessController<Message, MessageDto, MessageSearchingDto, IMessageRepository>
        implements IMessageProcessController {
}
