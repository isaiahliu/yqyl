package org.trinity.yqyl.process.controller.base;

import java.util.List;

import org.trinity.common.exception.IException;
import org.trinity.process.controller.ICrudProcessController;
import org.trinity.yqyl.common.message.dto.domain.ServiceOrderDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceOrderSearchingDto;

public interface IServiceOrderProcessController extends ICrudProcessController<ServiceOrderDto, ServiceOrderSearchingDto> {
	List<ServiceOrderDto> cancelOrder(List<ServiceOrderDto> data);

	List<ServiceOrderDto> changePrice(List<ServiceOrderDto> data) throws IException;

	int countUnprocessedOrders(String username) throws IException;

	ServiceOrderDto proposeOrder(ServiceOrderDto serviceOrderDto) throws IException;

	List<ServiceOrderDto> rejectCancelOrder(List<ServiceOrderDto> data) throws IException;

	void releaseOrder(List<ServiceOrderDto> data) throws IException;

	List<ServiceOrderDto> sendTxCode(List<ServiceOrderDto> data) throws IException;

	String uploadReceipt(ServiceOrderDto serviceOrderDto) throws IException;
}
