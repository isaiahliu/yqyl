package org.trinity.yqyl.process.controller;

import org.springframework.stereotype.Service;
import org.trinity.yqyl.common.message.dto.domain.SystemQaDto;
import org.trinity.yqyl.common.message.dto.domain.SystemQaSearchingDto;
import org.trinity.yqyl.process.controller.base.AbstractAutowiredCrudProcessController;
import org.trinity.yqyl.process.controller.base.ISystemQaProcessController;
import org.trinity.yqyl.repository.business.dataaccess.ISystemQaRepository;
import org.trinity.yqyl.repository.business.entity.SystemQa;

@Service
public class SystemQaProcessController
      extends AbstractAutowiredCrudProcessController<SystemQa, SystemQaDto, SystemQaSearchingDto, ISystemQaRepository>
      implements ISystemQaProcessController {
}

