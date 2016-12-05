package org.trinity.yqyl.process.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.trinity.common.dto.object.LookupDto;
import org.trinity.common.exception.IException;
import org.trinity.message.ILookupMessage;
import org.trinity.message.IMessageResolverChain;
import org.trinity.message.LookupParser;
import org.trinity.yqyl.common.message.lookup.Language;
import org.trinity.yqyl.common.message.lookup.LookupType;
import org.trinity.yqyl.common.message.lookup.RecordStatus;
import org.trinity.yqyl.process.controller.base.ILookupProcessController;
import org.trinity.yqyl.repository.business.dataaccess.ILookupRepository;
import org.trinity.yqyl.repository.business.entity.Lookup;

@Service
public class LookupProcessController implements ILookupProcessController {
    @Autowired
    private ILookupRepository lookupRepository;

    @Autowired
    private IMessageResolverChain messageResolver;

    @Override
    public List<LookupDto> getLookupsByType(final String lookupType) {
        final Class<? extends ILookupMessage<?>> lookupEnumClass = LookupParser
                .parseLookupClass(LookupParser.parse(LookupType.class, lookupType));

        if (lookupEnumClass == null || !lookupEnumClass.isEnum()) {
            return Collections.emptyList();
        }

        final ILookupMessage<?>[] items = lookupEnumClass.getEnumConstants();

        return Arrays.stream(items).map(item -> new LookupDto(item.getMessageCode(), messageResolver.getMessage(item)))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackOn = IException.class)
    public void refresh() {
        final List<Lookup> newLookups = new ArrayList<>();
        for (final Class<?> registeredType : LookupParser.getRegisteredType()) {
            if (!ILookupMessage.class.isAssignableFrom(registeredType) && !registeredType.isEnum()) {
                continue;
            }

            final Object[] lookups = registeredType.getEnumConstants();
            for (final Language language : Language.values()) {
                for (final Object o : lookups) {
                    final ILookupMessage<?> lookup = (ILookupMessage<?>) o;
                    final LookupType messageType = (LookupType) lookup.getMessageType();
                    Lookup lookupEntity = lookupRepository.findOneByLanguageAndCategoryAndCode(language, messageType,
                            lookup.getMessageCode());
                    if (lookupEntity == null) {
                        lookupEntity = new Lookup();
                        lookupEntity.setCategory(messageType);
                        lookupEntity.setDescription(lookup.getMessageCodeWithPrefix());
                        lookupEntity.setCode(lookup.getMessageCode());
                        lookupEntity.setLanguage(language);
                        lookupEntity.setStatus(RecordStatus.ACTIVE);

                        newLookups.add(lookupEntity);
                    }
                }
            }
        }
        if (!newLookups.isEmpty()) {
            lookupRepository.save(newLookups);
        }
        messageResolver.refresh();
    }
}
