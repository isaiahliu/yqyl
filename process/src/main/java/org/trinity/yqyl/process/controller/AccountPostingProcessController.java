package org.trinity.yqyl.process.controller;

import org.springframework.stereotype.Service;
import org.trinity.yqyl.common.message.dto.domain.AccountPostingDto;
import org.trinity.yqyl.common.message.dto.domain.AccountPostingSearchingDto;
import org.trinity.yqyl.process.controller.base.AbstractAutowiredCrudProcessController;
import org.trinity.yqyl.process.controller.base.IAccountPostingProcessController;
import org.trinity.yqyl.repository.business.dataaccess.IAccountPostingRepository;
import org.trinity.yqyl.repository.business.entity.AccountPosting;

@Service
public class AccountPostingProcessController
      extends AbstractAutowiredCrudProcessController<AccountPosting, AccountPostingDto, AccountPostingSearchingDto, IAccountPostingRepository>
      implements IAccountPostingProcessController {
}

