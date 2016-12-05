package org.trinity.yqyl.web.controller.page;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.trinity.common.exception.IException;

@RestController
@RequestMapping("/user")
public class UserWebController extends AbstractResourceWebController {

    @RequestMapping(value = "/family", method = RequestMethod.GET)
    public ModelAndView familyPage() throws IException {
        return createModelAndView("user/family");
    }

    @RequestMapping(value = "/health", method = RequestMethod.GET)
    public ModelAndView healthPage() throws IException {
        return createModelAndView("user/health");
    }

    @RequestMapping(value = "/order/appraise/{entityId}", method = RequestMethod.GET)
    public ModelAndView orderAppraisePage(@PathVariable("entityId") final Long entityId) throws IException {
        return createModelAndView("user/appraise").addObject("orderId", entityId).addObject("currentPage", "ORDER");
    }

    @RequestMapping(value = "/order/{entityId}", method = RequestMethod.GET)
    public ModelAndView orderDetailPage(@PathVariable("entityId") final Long entityId) throws IException {
        return createModelAndView("service/detail").addObject("orderId", entityId).addObject("mode", "receiver").addObject("currentPage",
                "ORDER");
    }

    @RequestMapping(value = "/order/edit/{entityId}", method = RequestMethod.GET)
    public ModelAndView orderEditPage(@PathVariable("entityId") final Long entityId) throws IException {
        return createModelAndView("service/proposal").addObject("orderId", entityId);
    }

    @RequestMapping(value = { "", "/order" }, method = RequestMethod.GET)
    public ModelAndView orderPage() throws IException {
        return createModelAndView("user/order").addObject("currentPage", "ORDER");
    }

    @RequestMapping(value = "/password", method = RequestMethod.GET)
    public ModelAndView passwordPage() throws IException {
        return createModelAndView("user/password").addObject("currentPage", "PASSWORD");
    }

    @RequestMapping(value = "/realname/{entityId}", method = RequestMethod.GET)
    public ModelAndView realnamePage(@PathVariable("entityId") final Long entityId) throws IException {
        return createModelAndView("user/realname").addObject("clientId", entityId);
    }

    @RequestMapping(value = "/userinfo/{entityId}", method = RequestMethod.GET)
    public ModelAndView userinfoEditPage(@PathVariable("entityId") final Long entityId) throws IException {
        return createModelAndView("user/userinfoEdit").addObject("currentPage", "INFO").addObject("clientId", entityId);
    }

    @RequestMapping(value = "/userinfo/new", method = RequestMethod.GET)
    public ModelAndView userinfoNewPage() throws IException {
        return createModelAndView("user/userinfoNew").addObject("currentPage", "INFO");
    }

    @RequestMapping(value = "/userinfo", method = RequestMethod.GET)
    public ModelAndView userinfoPage() throws IException {
        return createModelAndView("user/userinfo").addObject("currentPage", "INFO");
    }

    @RequestMapping(value = "/volunteer", method = RequestMethod.GET)
    public ModelAndView volunteerPage() throws IException {
        return createModelAndView("user/volunteer").addObject("currentPage", "VOLUNTEER");
    }

    @RequestMapping(value = "/yiquan/bind/{entityId}", method = RequestMethod.GET)
    public ModelAndView yiquanBindPage(@PathVariable("entityId") final Long entityId) throws IException {
        return createModelAndView("user/yiquan/bind").addObject("currentPage", "YIQUAN").addObject("currentSubPage", "BIND")
                .addObject("clientId", entityId);
    }

    @RequestMapping(value = "/yiquan/branch", method = RequestMethod.GET)
    public ModelAndView yiquanBranchPage() throws IException {
        return createModelAndView("user/yiquan/branch").addObject("currentPage", "YIQUAN").addObject("currentSubPage", "BRANCH");
    }

    @RequestMapping(value = { "/yiquan", "/yiquan/list" }, method = RequestMethod.GET)
    public ModelAndView yiquanListPage() throws IException {
        return createModelAndView("user/yiquan/list").addObject("currentPage", "YIQUAN").addObject("currentSubPage", "BIND");
    }

    @RequestMapping(value = "/yiquan/point", method = RequestMethod.GET)
    public ModelAndView yiquanPointPage() throws IException {
        return createModelAndView("user/yiquan/point").addObject("currentPage", "YIQUAN").addObject("currentSubPage", "POINT");
    }

    @RequestMapping(value = "/yiquan/search", method = RequestMethod.GET)
    public ModelAndView yiquanSearchPage() throws IException {
        return createModelAndView("user/yiquan/search").addObject("currentPage", "YIQUAN").addObject("currentSubPage", "SEARCH");
    }

    @RequestMapping(value = "/yiquan/topup", method = RequestMethod.GET)
    public ModelAndView yiquanTopupPage() throws IException {
        return createModelAndView("user/yiquan/topup").addObject("currentPage", "YIQUAN").addObject("currentSubPage", "TOPUP");
    }
}
