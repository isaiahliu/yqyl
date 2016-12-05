package org.trinity.yqyl.process.controller.base;

import java.util.List;

import org.trinity.process.controller.ICrudProcessController;
import org.trinity.yqyl.common.message.dto.domain.ContentDto;
import org.trinity.yqyl.common.message.dto.domain.ContentSearchingDto;

public interface IContentProcessController extends ICrudProcessController<ContentDto, ContentSearchingDto> {
    List<ContentDto> addUpdateAll(List<ContentDto> data);

    String create();

    ContentDto getOneByUuid(String uuid);
}
