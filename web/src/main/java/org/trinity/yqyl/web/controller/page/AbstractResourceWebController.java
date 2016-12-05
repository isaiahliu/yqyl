package org.trinity.yqyl.web.controller.page;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.trinity.common.accessright.ISecurityUtil;
import org.trinity.message.IMessageResolverChain;
import org.trinity.rest.controller.AbstractWebController;
import org.trinity.rest.util.IRestfulServiceUtil;
import org.trinity.yqyl.common.message.lookup.AccessRight;

public class AbstractResourceWebController extends AbstractWebController {
    @Autowired
    protected IMessageResolverChain messageResolver;

    @Autowired
    protected ISecurityUtil<AccessRight> securityUtil;

    @Autowired
    protected IRestfulServiceUtil restfulServiceUtil;

    @Override
    protected ModelAndView createModelAndView(final String viewName) {
        return super.createModelAndView(viewName).addObject("messageResolver", messageResolver)
                .addObject("authentication", SecurityContextHolder.getContext().getAuthentication()).addObject("security", securityUtil);
    }

    protected ModelAndView createModelAndView(final String viewName, final String errorMessage) {
        return this.createModelAndView(viewName).addObject("error", errorMessage);
    }
}
