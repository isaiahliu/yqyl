package org.trinity.yqyl.process.controller.base;

import org.springframework.data.domain.Page;
import org.trinity.common.exception.IException;
import org.trinity.process.controller.ICrudProcessController;
import org.trinity.yqyl.common.message.dto.domain.AccountPostingSearchingDto;
import org.trinity.yqyl.common.message.dto.domain.YiquanDto;
import org.trinity.yqyl.common.message.dto.domain.YiquanSearchingDto;
import org.trinity.yqyl.common.message.dto.domain.YiquanTxDto;
import org.trinity.yqyl.repository.business.entity.Yiquan;

public interface IYiquanProcessController extends ICrudProcessController<YiquanDto, YiquanSearchingDto> {
    void bindMe(YiquanDto yiquanCode) throws IException;

    Yiquan create(String yiquanCode) throws IException;

    Page<YiquanTxDto> listDetails(AccountPostingSearchingDto request) throws IException;

    void topup(YiquanDto yiquanDto) throws IException;

    void unbindMe(YiquanDto yiquanDto) throws IException;
}
