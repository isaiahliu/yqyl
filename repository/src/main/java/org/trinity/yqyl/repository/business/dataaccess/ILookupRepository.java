package org.trinity.yqyl.repository.business.dataaccess;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.trinity.yqyl.common.message.lookup.Language;
import org.trinity.yqyl.common.message.lookup.LookupType;
import org.trinity.yqyl.repository.business.entity.Lookup;

public interface ILookupRepository extends CrudRepository<Lookup, Long> {
    List<Lookup> findAllByCategory(LookupType category);

    List<Lookup> findAllByLanguage(Language language);

    Lookup findOneByLanguageAndCategoryAndCode(Language language, LookupType category, String code);
}
