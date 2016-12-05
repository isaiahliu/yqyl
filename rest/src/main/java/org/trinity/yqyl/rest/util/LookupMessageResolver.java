package org.trinity.yqyl.rest.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.trinity.common.dto.object.LookupDto;
import org.trinity.message.GeneralMessageType;
import org.trinity.message.IMessageResolver;
import org.trinity.yqyl.common.message.lookup.Language;
import org.trinity.yqyl.repository.business.dataaccess.ILookupRepository;

@Component
public class LookupMessageResolver implements IMessageResolver {
	@Autowired
	private ILookupRepository lookupRepository;

	private Map<Language, Set<LookupDto>> lookups;

	@Override
	public String getMessage(final String message, final Locale locale, final String... args) {
		if (!message.startsWith(GeneralMessageType.LOOKUP.getMessageTypePrefix())) {
			return null;
		}

		final Optional<Language> language = Arrays.stream(Language.values())
				.filter(item -> item.getLocale().toLanguageTag().equals(locale.toLanguageTag())).findFirst();

		if (language.isPresent()) {
			final Set<LookupDto> lookups2 = getLookups(language.get());
			final Optional<LookupDto> find = lookups2.stream().filter(item -> item.getCode().equals(message)).findFirst();
			if (find.isPresent()) {
				return find.get().getMessage();
			}
		}

		return null;
	}

	@Override
	public void refresh() {
		if (lookups != null) {
			lookups.clear();
		}
	}

	private Set<LookupDto> getLookups(final Language language) {
		if (lookups == null) {
			synchronized (LookupMessageResolver.class) {
				if (lookups == null) {
					lookups = new HashMap<Language, Set<LookupDto>>();
				}
			}
		}

		if (!lookups.containsKey(language)) {
			synchronized (LookupMessageResolver.class) {
				if (!lookups.containsKey(language)) {

					lookups.put(language, lookupRepository.findAllByLanguage(language).stream()
							.map(item -> new LookupDto(item.getCategory().getMessageTypePrefix() + item.getCode(), item.getDescription()))
							.collect(Collectors.toSet()));
				}
			}
		}
		return lookups.get(language);
	}
}
