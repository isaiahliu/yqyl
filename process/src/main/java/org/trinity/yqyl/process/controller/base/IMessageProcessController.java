package org.trinity.yqyl.process.controller.base;

import org.trinity.process.controller.ICrudProcessController;
import org.trinity.yqyl.common.message.dto.domain.MessageDto;
import org.trinity.yqyl.common.message.dto.domain.MessageSearchingDto;

public interface IMessageProcessController extends ICrudProcessController<MessageDto, MessageSearchingDto> {
}
