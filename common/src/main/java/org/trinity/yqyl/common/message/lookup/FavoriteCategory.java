package org.trinity.yqyl.common.message.lookup;

import org.trinity.message.AbstractFreeTextLookup;

public class FavoriteCategory extends AbstractFreeTextLookup<LookupType> {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private final String messageCode;

    public FavoriteCategory() {
        this("");
    }

    public FavoriteCategory(final String messageCode) {
        this.messageCode = messageCode;
    }

    @Override
    public String getMessageCode() {
        return messageCode;
    }

    @Override
    public LookupType getMessageType() {
        return LookupType.FAVORITE_CATEGORY;
    }

}
