package org.trinity.yqyl.web.controller.page;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.trinity.common.exception.IException;

@RestController
@RequestMapping
public class HomeWebController extends AbstractResourceWebController {
    @RequestMapping("")
    public ModelAndView defaultPage() throws IException {
        return createModelAndView("redirect:/home");
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView getLoginPage() throws IException {
        return createModelAndView("login");
    }

    @RequestMapping("/home")
    public ModelAndView home() throws IException {
        return createModelAndView("home");
    }
}
