package org.trinity.yqyl.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.trinity.yqyl.common.message.dto.domain.AnnouncementDto;
import org.trinity.yqyl.common.message.dto.domain.AnnouncementSearchingDto;
import org.trinity.yqyl.common.message.dto.request.AnnouncementRequest;
import org.trinity.yqyl.common.message.dto.response.AnnouncementResponse;
import org.trinity.yqyl.process.controller.base.IAnnouncementProcessController;

/**
 * @author Isaiah Liu
 *
 */
@RestController
@RequestMapping("/message/announcement")
public class AnnouncementRestController extends
		AbstractApplicationAwareCrudRestController<AnnouncementDto, AnnouncementSearchingDto, IAnnouncementProcessController, AnnouncementRequest, AnnouncementResponse> {

	@Override
	protected AnnouncementResponse createResponseInstance() {
		return new AnnouncementResponse();
	}
}
