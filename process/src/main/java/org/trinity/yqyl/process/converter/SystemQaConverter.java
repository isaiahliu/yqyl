package org.trinity.yqyl.process.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.trinity.common.dto.object.LookupDto;
import org.trinity.common.dto.object.RelationshipExpression;
import org.trinity.common.util.Tuple2;
import org.trinity.message.ILookupMessage;
import org.trinity.process.converter.AbstractLookupSupportObjectConverter;
import org.trinity.process.converter.IObjectConverter;
import org.trinity.yqyl.common.message.dto.domain.SystemQaDto;
import org.trinity.yqyl.common.message.lookup.RecordStatus;
import org.trinity.yqyl.repository.business.entity.SystemQa;

@Component
public class SystemQaConverter extends AbstractLookupSupportObjectConverter<SystemQa, SystemQaDto> {
	private static enum SystemQaRelationship {
		NA
	}

	@Autowired
	public SystemQaConverter(final IObjectConverter<Tuple2<ILookupMessage<?>, String[]>, LookupDto> lookupConverter) {
		super(lookupConverter);
	}

	@Override
	protected void convertBackInternal(final SystemQaDto source, final SystemQa target, final CopyPolicy copyPolicy) {
		copyObject(source::getId, target::getId, target::setId, copyPolicy);
		copyObject(source::getQuestion, target::getQuestion, target::setQuestion, copyPolicy);
		copyObject(source::getAnswer, target::getAnswer, target::setAnswer, copyPolicy);
		copyLookup(source::getStatus, target::getStatus, target::setStatus, RecordStatus.class, copyPolicy);
	}

	@Override
	protected void convertInternal(final SystemQa source, final SystemQaDto target, final CopyPolicy copyPolicy) {
		copyObject(source::getId, target::getId, target::setId, copyPolicy);
		copyObject(source::getQuestion, target::getQuestion, target::setQuestion, copyPolicy);
		copyObject(source::getAnswer, target::getAnswer, target::setAnswer, copyPolicy);
		copyMessage(source::getStatus, target::getStatus, target::setStatus, copyPolicy);
	}

	@Override
	protected void convertRelationshipInternal(final SystemQa source, final SystemQaDto target,
			final RelationshipExpression relationshipExpression) {
		switch (relationshipExpression.getName(SystemQaRelationship.class)) {
		case NA:
		default:
			break;
		}
	}

	@Override
	protected SystemQa createFromInstance() {
		return new SystemQa();
	}

	@Override
	protected SystemQaDto createToInstance() {
		return new SystemQaDto();
	}
}
