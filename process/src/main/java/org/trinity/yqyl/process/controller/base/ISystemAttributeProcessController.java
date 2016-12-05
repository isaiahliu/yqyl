package org.trinity.yqyl.process.controller.base;

import org.trinity.common.exception.IException;
import org.trinity.process.controller.ICrudProcessController;
import org.trinity.yqyl.common.message.dto.domain.SystemAttributeDto;
import org.trinity.yqyl.common.message.dto.domain.SystemAttributeSearchingDto;
import org.trinity.yqyl.common.message.lookup.SystemAttributeKey;

public interface ISystemAttributeProcessController extends ICrudProcessController<SystemAttributeDto, SystemAttributeSearchingDto> {
    String getValue(SystemAttributeKey key) throws IException;

    void refreshAll() throws IException;
}
