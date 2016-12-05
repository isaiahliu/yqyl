package org.trinity.yqyl.web.util;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.trinity.common.dto.response.DefaultResponse;
import org.trinity.common.exception.IException;
import org.trinity.message.LookupParser;
import org.trinity.rest.util.IRestfulServiceUtil;
import org.trinity.yqyl.common.message.lookup.AccessRight;

@Component
public class UserService implements UserDetailsService {
    @Autowired
    private IRestfulServiceUtil restfulServiceUtil;

    @Override
    public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
        final List<AccessRight> authorities = new ArrayList<>();
        try {
            final DefaultResponse response = restfulServiceUtil.callRestService(username, Url.AUTHORITIES, null, null, null,
                    DefaultResponse.class);
            authorities.addAll(response.getData().stream().map(item -> LookupParser.parse(AccessRight.class, item))
                    .filter(item -> item != null).collect(Collectors.toList()));
        } catch (final IException e) {
        }

        return new User(username, username, authorities);
    }
}
