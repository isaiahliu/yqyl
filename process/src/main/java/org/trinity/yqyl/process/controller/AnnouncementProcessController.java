package org.trinity.yqyl.process.controller;

import org.springframework.stereotype.Service;
import org.trinity.yqyl.common.message.dto.domain.AnnouncementDto;
import org.trinity.yqyl.common.message.dto.domain.AnnouncementSearchingDto;
import org.trinity.yqyl.process.controller.base.AbstractAutowiredCrudProcessController;
import org.trinity.yqyl.process.controller.base.IAnnouncementProcessController;
import org.trinity.yqyl.repository.business.dataaccess.IAnnouncementRepository;
import org.trinity.yqyl.repository.business.entity.Announcement;

@Service
public class AnnouncementProcessController
        extends AbstractAutowiredCrudProcessController<Announcement, AnnouncementDto, AnnouncementSearchingDto, IAnnouncementRepository>
        implements IAnnouncementProcessController {
}
