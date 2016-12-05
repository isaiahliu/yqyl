package org.trinity.yqyl.process.controller;

import org.springframework.stereotype.Service;
import org.trinity.yqyl.common.message.dto.domain.UserVerifycodeDto;
import org.trinity.yqyl.common.message.dto.domain.UserVerifycodeSearchingDto;
import org.trinity.yqyl.process.controller.base.AbstractAutowiredCrudProcessController;
import org.trinity.yqyl.process.controller.base.IUserVerifycodeProcessController;
import org.trinity.yqyl.repository.business.dataaccess.IUserVerifycodeRepository;
import org.trinity.yqyl.repository.business.entity.UserVerifycode;

@Service
public class UserVerifycodeProcessController
      extends AbstractAutowiredCrudProcessController<UserVerifycode, UserVerifycodeDto, UserVerifycodeSearchingDto, IUserVerifycodeRepository>
      implements IUserVerifycodeProcessController {
}

