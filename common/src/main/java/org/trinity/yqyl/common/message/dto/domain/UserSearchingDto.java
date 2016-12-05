package org.trinity.yqyl.common.message.dto.domain;

import java.util.ArrayList;
import java.util.List;

import org.trinity.common.dto.object.AbstractSearchingDto;

public class UserSearchingDto extends AbstractSearchingDto {
    private String username;
    private List<Long> exceptUserIds;

    public List<Long> getExceptUserIds() {
        if (exceptUserIds == null) {
            exceptUserIds = new ArrayList<>();
        }
        return exceptUserIds;
    }

    public String getUsername() {
        return username;
    }

    public void setExceptUserIds(final List<Long> exceptUserIds) {
        this.exceptUserIds = exceptUserIds;
    }

    public void setUsername(final String username) {
        this.username = username;
    }
}
