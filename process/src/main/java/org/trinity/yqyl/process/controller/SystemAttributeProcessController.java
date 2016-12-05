package org.trinity.yqyl.process.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.trinity.common.exception.IException;
import org.trinity.yqyl.common.message.dto.domain.SystemAttributeDto;
import org.trinity.yqyl.common.message.dto.domain.SystemAttributeSearchingDto;
import org.trinity.yqyl.common.message.lookup.RecordStatus;
import org.trinity.yqyl.common.message.lookup.SystemAttributeKey;
import org.trinity.yqyl.process.controller.base.AbstractAutowiredCrudProcessController;
import org.trinity.yqyl.process.controller.base.ISystemAttributeProcessController;
import org.trinity.yqyl.repository.business.dataaccess.ISystemAttributeRepository;
import org.trinity.yqyl.repository.business.entity.SystemAttribute;

@Service
public class SystemAttributeProcessController extends
        AbstractAutowiredCrudProcessController<SystemAttribute, SystemAttributeDto, SystemAttributeSearchingDto, ISystemAttributeRepository>
        implements ISystemAttributeProcessController {

    @Override
    @Transactional(rollbackOn = IException.class)
    public void refreshAll() throws IException {
        final Iterable<SystemAttribute> allAttributes = getDomainEntityRepository().findAll();

        final List<SystemAttributeKey> allKeys = Arrays.asList(SystemAttributeKey.values());

        final List<SystemAttributeKey> existingKeys = StreamSupport.stream(allAttributes.spliterator(), false).map(item -> item.getKey())
                .collect(Collectors.toList());
        allKeys.removeAll(existingKeys);

        final List<SystemAttribute> attributes = allKeys.stream().map(item -> {
            final SystemAttribute attribute = new SystemAttribute();
            attribute.setFormat("");
            attribute.setKey(item);
            attribute.setStatus(RecordStatus.ACTIVE);
            attribute.setValue("");
            attribute.setValueType("");
            return attribute;
        }).collect(Collectors.toList());

        getDomainEntityRepository().save(attributes);
    }
}
