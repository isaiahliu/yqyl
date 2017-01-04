package org.trinity.yqyl.web.controller.page;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.trinity.common.exception.IException;

@RestController
@RequestMapping("/help")
public class HelpWebController extends AbstractResourceWebController {
    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public ModelAndView getAboutPage() throws IException {
        return createModelAndView("help/about").addObject("currentPage", "ABOUT");
    }

    @RequestMapping(value = "/answer", method = RequestMethod.GET)
    public ModelAndView getAnswerPage() throws IException {
        return createModelAndView("help/answer").addObject("currentPage", "ANSWER");
    }

    @RequestMapping(value = "/join", method = RequestMethod.GET)
    public ModelAndView getJoinPage() throws IException {
        return createModelAndView("help/join").addObject("currentPage", "JOIN");
    }
}
