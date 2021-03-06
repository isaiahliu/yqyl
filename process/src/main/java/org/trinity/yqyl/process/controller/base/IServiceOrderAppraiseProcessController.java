package org.trinity.yqyl.process.controller.base;

import java.util.List;

import org.trinity.common.exception.IException;
import org.trinity.process.controller.ICrudProcessController;
import org.trinity.yqyl.common.message.dto.domain.ServiceOrderAppraiseDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceOrderAppraiseSearchingDto;

public interface IServiceOrderAppraiseProcessController
		extends ICrudProcessController<ServiceOrderAppraiseDto, ServiceOrderAppraiseSearchingDto> {

	int countAppraises(Long serviceSupplierClientId);

	int countAppraisesForRate(Long serviceSupplierClientId, int from, int to);

	void reply(List<ServiceOrderAppraiseDto> data) throws IException;
}
