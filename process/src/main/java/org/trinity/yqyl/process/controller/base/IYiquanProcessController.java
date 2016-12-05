package org.trinity.yqyl.process.controller.base;

import org.trinity.common.exception.IException;
import org.trinity.process.controller.ICrudProcessController;
import org.trinity.yqyl.common.message.dto.domain.YiquanDto;
import org.trinity.yqyl.common.message.dto.domain.YiquanSearchingDto;

public interface IYiquanProcessController extends ICrudProcessController<YiquanDto, YiquanSearchingDto> {
    void bindMe(YiquanDto yiquanCode) throws IException;

    void topup(YiquanDto yiquanDto) throws IException;

    void unbindMe(YiquanDto yiquanDto) throws IException;
}
