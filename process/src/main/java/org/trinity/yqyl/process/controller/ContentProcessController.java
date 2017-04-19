package org.trinity.yqyl.process.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.trinity.common.exception.IException;
import org.trinity.yqyl.common.message.dto.domain.ContentDto;
import org.trinity.yqyl.common.message.dto.domain.ContentSearchingDto;
import org.trinity.yqyl.common.message.lookup.RecordStatus;
import org.trinity.yqyl.process.controller.base.AbstractAutowiredCrudProcessController;
import org.trinity.yqyl.process.controller.base.IContentProcessController;
import org.trinity.yqyl.repository.business.dataaccess.IContentRepository;
import org.trinity.yqyl.repository.business.entity.Content;

@Service
public class ContentProcessController
		extends AbstractAutowiredCrudProcessController<Content, ContentDto, ContentSearchingDto, IContentRepository>
		implements IContentProcessController {

	@Override
	@Transactional(rollbackOn = IException.class)
	public List<ContentDto> addUpdateAll(final List<ContentDto> data) {
		final List<Content> items = new ArrayList<>();

		for (final ContentDto item : data) {
			Content content;
			if (StringUtils.isEmpty(item.getUuid())) {
				content = new Content();
				content.setUuid(UUID.randomUUID().toString());
				content.setStatus(RecordStatus.ACTIVE);
			} else {
				content = getDomainEntityRepository().findOneByUuid(item.getUuid());

				if (content == null) {
					content = new Content();
					content.setUuid(item.getUuid());
					content.setStatus(RecordStatus.ACTIVE);
				}
			}

			content.setContent(item.getContent());

			items.add(content);
		}

		getDomainEntityRepository().save(items);

		return items.stream().map(item -> {
			final ContentDto dto = new ContentDto();
			dto.setUuid(item.getUuid());
			return dto;
		}).collect(Collectors.toList());
	}

	@Override
	@Transactional(rollbackOn = IException.class)
	public String create() {
		final Content content = new Content();
		content.setContent(new byte[0]);
		content.setStatus(RecordStatus.ACTIVE);
		content.setUuid(UUID.randomUUID().toString());

		getDomainEntityRepository().save(content);
		return content.getUuid();
	}

	@Override
	public ContentDto getOneByUuid(final String uuid) {
		final Content content = getDomainEntityRepository().findOneByUuid(uuid);
		return getDomainObjectConverter().convert(content);
	}
}
