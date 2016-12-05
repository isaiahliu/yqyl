package org.trinity.yqyl.web.controller.page;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.trinity.common.exception.IException;

@RestController
@RequestMapping("/operator")
public class OperatorWebController extends AbstractResourceWebController {
    @RequestMapping({ "", "/answer" })
    public ModelAndView answerPage() throws IException {
        return createModelAndView("operator/answer");
    }

    @RequestMapping("/complaint")
    public ModelAndView complaintPage() throws IException {
        return createModelAndView("operator/complaint");
    }

    @RequestMapping("/record")
    public ModelAndView recordPage() throws IException {
        return createModelAndView("operator/record");
    }

    @Override
    protected ModelAndView createModelAndView(final String viewName) {
        return super.createModelAndView(viewName).addObject("platform", "OPERATOR");
    }
}
