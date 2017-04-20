package org.trinity.yqyl.web.controller.page;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.trinity.common.exception.IException;
import org.trinity.yqyl.common.accessright.Authorize;
import org.trinity.yqyl.common.message.lookup.AccessRight;

@RestController
@RequestMapping("/admin")
public class AdminWebController extends AbstractResourceWebController {

	@RequestMapping("/manage/about")
	@Authorize(AccessRight.ADMINISTRATOR)
	public ModelAndView aboutPage() throws IException {
		return createModelAndView("admin/manage/about").addObject("currentPage", "MANAGE").addObject("currentSubPage",
				"ABOUT");
	}

	@RequestMapping("/manage/agreement")
	@Authorize(AccessRight.ADMINISTRATOR)
	public ModelAndView agreementPage() throws IException {
		return createModelAndView("admin/manage/agreement").addObject("currentPage", "MANAGE")
				.addObject("currentSubPage", "AGREEMENT");
	}

	@RequestMapping("/manage/answers")
	@Authorize(AccessRight.ADMINISTRATOR)
	public ModelAndView answersPage() throws IException {
		return createModelAndView("admin/manage/answer/answers").addObject("currentPage", "MANAGE")
				.addObject("currentSubPage", "ANSWERS");
	}

	@Override
	@Authorize(AccessRight.ADMINISTRATOR)
	protected ModelAndView createModelAndView(final String viewName) {
		return super.createModelAndView(viewName).addObject("platform", "ADMIN");
	}

	@RequestMapping("/supplier/{entityId}")
	@Authorize(AccessRight.ADMINISTRATOR)
	public ModelAndView infoPage(@PathVariable("entityId") final Long entityId) throws IException {
		return createModelAndView("servicer/info").addObject("currentPage", "SUPPLIER")
				.addObject("serviceSupplierClientId", entityId);
	}

	@RequestMapping("/manage/join")
	@Authorize(AccessRight.ADMINISTRATOR)
	public ModelAndView joinPage() throws IException {
		return createModelAndView("admin/manage/join").addObject("currentPage", "MANAGE").addObject("currentSubPage",
				"JOIN");
	}

	@RequestMapping({ "", "/news" })
	@Authorize(AccessRight.ADMINISTRATOR)
	public ModelAndView newsPage() throws IException {
		return createModelAndView("admin/news").addObject("currentPage", "NEWS");
	}

	@RequestMapping("/permission/edit/{entityId}")
	@Authorize(AccessRight.ADMINISTRATOR)
	public ModelAndView permissionEditPage(@PathVariable("entityId") final Long entityId) throws IException {
		return createModelAndView("admin/permissionEdit").addObject("currentPage", "PERMISSION").addObject("userId",
				entityId);
	}

	@RequestMapping("/permission")
	@Authorize(AccessRight.ADMINISTRATOR)
	public ModelAndView permissionPage() throws IException {
		return createModelAndView("admin/permission").addObject("currentPage", "PERMISSION");
	}

	@RequestMapping("/manage/readme")
	@Authorize(AccessRight.ADMINISTRATOR)
	public ModelAndView readmePage() throws IException {
		return createModelAndView("admin/manage/readme").addObject("currentPage", "MANAGE").addObject("currentSubPage",
				"README");
	}

	@RequestMapping("/receiver/{entityId}")
	@Authorize(AccessRight.ADMINISTRATOR)
	public ModelAndView receiverAuditPage(@PathVariable("entityId") final Long entityId) throws IException {
		return createModelAndView("user/realname").addObject("currentPage", "RECEIVER").addObject("clientId", entityId);
	}

	@RequestMapping("/receiver")
	@Authorize(AccessRight.ADMINISTRATOR)
	public ModelAndView receiverPage() throws IException {
		return createModelAndView("admin/receiver").addObject("currentPage", "RECEIVER");
	}

	@RequestMapping("/role")
	@Authorize(AccessRight.ADMINISTRATOR)
	public ModelAndView rolePage() throws IException {
		return createModelAndView("admin/role").addObject("currentPage", "ROLE");
	}

	@RequestMapping("/service/appraise")
	@Authorize(AccessRight.ADMINISTRATOR)
	public ModelAndView serviceAppraisePage() throws IException {
		return createModelAndView("admin/service/appraise").addObject("currentPage", "SERVICE")
				.addObject("currentSubPage", "APPRAISE");
	}

	@RequestMapping("/service/category/edit")
	@Authorize(AccessRight.ADMINISTRATOR)
	public ModelAndView serviceCategoryEditPage() throws IException {
		return createModelAndView("admin/service/categoryEdit").addObject("currentPage", "SERVICE")
				.addObject("currentSubPage", "CATEGORY");
	}

	@RequestMapping("/service/category")
	@Authorize(AccessRight.ADMINISTRATOR)
	public ModelAndView serviceCategoryPage() throws IException {
		return createModelAndView("admin/service/category").addObject("currentPage", "SERVICE")
				.addObject("currentSubPage", "CATEGORY");
	}

	@RequestMapping("/service/order/{entityId}")
	@Authorize(AccessRight.ADMINISTRATOR)
	public ModelAndView serviceOrderPage(@PathVariable("entityId") final String entityId) throws IException {
		return createModelAndView("service/detail").addObject("orderId", entityId).addObject("mode", "admin")
				.addObject("currentPage", "SERVICE").addObject("currentSubPage", "ORDER");
	}

	@RequestMapping("/service/order")
	@Authorize(AccessRight.ADMINISTRATOR)
	public ModelAndView serviceOrdersPage() throws IException {
		return createModelAndView("admin/service/order").addObject("currentPage", "SERVICE").addObject("currentSubPage",
				"ORDER");
	}

	@RequestMapping("/service/category/edit/{entityId}")
	@Authorize(AccessRight.ADMINISTRATOR)
	public ModelAndView serviceSubCategoryEditPage(@PathVariable("entityId") final Long entityId) throws IException {
		return createModelAndView("admin/service/categoryEdit").addObject("currentPage", "SERVICE")
				.addObject("currentSubPage", "CATEGORY").addObject("categoryId", entityId);
	}

	@RequestMapping("/service/supplier")
	@Authorize(AccessRight.ADMINISTRATOR)
	public ModelAndView serviceSupplierPage() throws IException {
		return createModelAndView("admin/service/supplier").addObject("currentPage", "SERVICE")
				.addObject("currentSubPage", "SUPPLIER");
	}

	@RequestMapping("/supplier")
	@Authorize(AccessRight.ADMINISTRATOR)
	public ModelAndView supplierPage() throws IException {
		return createModelAndView("admin/supplier").addObject("currentPage", "SUPPLIER");
	}

	@RequestMapping("/training")
	@Authorize(AccessRight.ADMINISTRATOR)
	public ModelAndView trainingPage() throws IException {
		return createModelAndView("admin/training").addObject("currentPage", "TRAINING");
	}
}
