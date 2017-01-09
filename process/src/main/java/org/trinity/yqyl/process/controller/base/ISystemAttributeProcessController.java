package org.trinity.yqyl.process.controller.base;

import java.util.List;

import org.trinity.common.exception.IException;
import org.trinity.process.controller.IProcessController;
import org.trinity.yqyl.common.message.dto.domain.SystemAttributeDto;
import org.trinity.yqyl.common.message.lookup.SystemAttributeKey;

public interface ISystemAttributeProcessController extends IProcessController {
    String getValue(SystemAttributeKey key) throws IException;

    void updateValues(List<SystemAttributeDto> dtos) throws IException;
}
