package org.trinity.yqyl.rest.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.stereotype.Component;
import org.trinity.common.accessright.ISecurityUtil;
import org.trinity.common.exception.IException;
import org.trinity.repository.auditing.AbstractEntityAuditorAware;
import org.trinity.yqyl.common.message.lookup.AccessRight;

@Component
@EnableJpaAuditing
public class AuditorAware extends AbstractEntityAuditorAware {
    @Autowired
    private ISecurityUtil<AccessRight> securityUtil;

    @Override
    protected String getAuditorName() {
        try {
            return securityUtil.getCurrentToken().getUsername();
        } catch (final IException e) {
            return "Anonymous";
        }
    }
}
