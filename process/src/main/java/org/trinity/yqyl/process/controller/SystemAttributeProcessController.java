package org.trinity.yqyl.process.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.trinity.common.exception.IException;
import org.trinity.message.LookupParser;
import org.trinity.yqyl.common.message.dto.domain.SystemAttributeDto;
import org.trinity.yqyl.common.message.lookup.RecordStatus;
import org.trinity.yqyl.common.message.lookup.SystemAttributeKey;
import org.trinity.yqyl.process.controller.base.ISystemAttributeProcessController;
import org.trinity.yqyl.repository.business.dataaccess.ISystemAttributeRepository;
import org.trinity.yqyl.repository.business.entity.SystemAttribute;

@Service
public class SystemAttributeProcessController implements ISystemAttributeProcessController {

    @Autowired
    private ISystemAttributeRepository systemAttributeRepository;

    @Override
    public String getValue(final SystemAttributeKey key) throws IException {
        if (key == null) {
            return null;
        }

        SystemAttribute entity = systemAttributeRepository.findOneByKey(key);
        if (entity == null) {
            entity = new SystemAttribute();
            entity.setFormat("");
            entity.setKey(key);
            entity.setStatus(RecordStatus.ACTIVE);
            entity.setValue(key.getDefaultValue());
            entity.setValueType("");

            systemAttributeRepository.save(entity);
        }
        return entity.getValue();
    }

    @Override
    @Transactional(rollbackOn = IException.class)
    public void updateValues(final List<SystemAttributeDto> dtos) throws IException {
        for (final SystemAttributeDto dto : dtos) {
            final SystemAttributeKey key = LookupParser.parse(SystemAttributeKey.class, dto.getKey());
            if (key == null) {
                continue;
            }
            SystemAttribute attribute = systemAttributeRepository.findOneByKey(key);
            if (attribute == null) {
                attribute = new SystemAttribute();
                attribute.setFormat("");
                attribute.setKey(key);
                attribute.setStatus(RecordStatus.ACTIVE);
                attribute.setValueType("");
            }

            attribute.setValue(dto.getValue());

            systemAttributeRepository.save(attribute);
        }
    }
}
