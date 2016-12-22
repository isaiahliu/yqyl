package org.trinity.yqyl.web.controller.page;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.trinity.common.exception.IException;
import org.trinity.yqyl.common.accessright.Authorize;
import org.trinity.yqyl.common.message.lookup.AccessRight;

@RestController
@RequestMapping("/servicer")
public class ServicerWebController extends AbstractResourceWebController {
	@RequestMapping("/afterSale")
	@Authorize(value = AccessRight.SERVICE_SUPPLIER, checkAncestors = false)
	public ModelAndView afterSalePage() throws IException {
		return createModelAndView("servicer/afterSale").addObject("currentPage", "AFTER_SALE");
	}

	@RequestMapping("/auditing")
	@Authorize(value = AccessRight.SERVICE_SUPPLIER_REGISTER)
	public ModelAndView auditingPage() throws IException {
		return createModelAndView("servicer/auditing").addObject("currentPage", "AUDITING");
	}

	@RequestMapping({ "", "/info" })
	@Authorize(value = AccessRight.SERVICE_SUPPLIER_REGISTER)
	public ModelAndView infoPage() throws IException {
		return createModelAndView("servicer/info").addObject("serviceSupplierClientId", 0).addObject("currentPage", "INFO");
	}

	@RequestMapping("/operating")
	@Authorize(value = AccessRight.SERVICE_SUPPLIER, checkAncestors = false)
	public ModelAndView operatingPage() throws IException {
		return createModelAndView("servicer/operating").addObject("currentPage", "OPERATING");
	}

	@RequestMapping("/order/{entityId}")
	@Authorize(value = AccessRight.SERVICE_SUPPLIER, checkAncestors = false)
	public ModelAndView orderDetailPage(@PathVariable("entityId") final String entityId) throws IException {
		return createModelAndView("service/detail").addObject("orderId", entityId).addObject("mode", "supplier").addObject("currentPage",
				"ORDER");
	}

	@RequestMapping("/order/edit/{entityId}")
	@Authorize(value = AccessRight.SERVICE_SUPPLIER, checkAncestors = false)
	public ModelAndView orderEditPage(@PathVariable("entityId") final Long entityId) throws IException {
		return createModelAndView("servicer/orderEdit").addObject("serviceOrderId", entityId).addObject("currentPage", "ORDER");
	}

	@RequestMapping("/order")
	@Authorize(value = AccessRight.SERVICE_SUPPLIER, checkAncestors = false)
	public ModelAndView orderPage() throws IException {
		return createModelAndView("servicer/order").addObject("currentPage", "ORDER");
	}

	@RequestMapping("/sale")
	@Authorize(value = AccessRight.SERVICE_SUPPLIER, checkAncestors = false)
	public ModelAndView salePage() throws IException {
		return createModelAndView("servicer/sale").addObject("currentPage", "SALE");
	}

	@RequestMapping("/service/edit/{entityId}")
	@Authorize(value = AccessRight.SERVICE_SUPPLIER, checkAncestors = false)
	public ModelAndView serviceEditPage(@PathVariable("entityId") final Long entityId) throws IException {
		return createModelAndView("servicer/serviceEdit").addObject("serviceInfoId", entityId).addObject("currentPage", "SERVICE");
	}

	@RequestMapping("/service/new")
	@Authorize(value = AccessRight.SERVICE_SUPPLIER, checkAncestors = false)
	public ModelAndView serviceNewPage() throws IException {
		return createModelAndView("servicer/serviceEdit").addObject("serviceInfoId", 0).addObject("currentPage", "SERVICE");
	}

	@RequestMapping("/service")
	@Authorize(value = AccessRight.SERVICE_SUPPLIER, checkAncestors = false)
	public ModelAndView servicePage() throws IException {
		return createModelAndView("servicer/service").addObject("currentPage", "SERVICE");
	}

	@RequestMapping("/staff/edit/{entityId}")
	@Authorize(value = AccessRight.SERVICE_SUPPLIER, checkAncestors = false)
	public ModelAndView staffEditPage(@PathVariable("entityId") final Long entityId) throws IException {
		return createModelAndView("servicer/staffEdit").addObject("staffId", entityId).addObject("currentPage", "STAFF")
				.addObject("currentSubPage", "INFO");
	}

	@RequestMapping("/staff/new")
	@Authorize(value = AccessRight.SERVICE_SUPPLIER, checkAncestors = false)
	public ModelAndView staffNewPage() throws IException {
		return createModelAndView("servicer/staffEdit").addObject("staffId", 0).addObject("currentPage", "STAFF")
				.addObject("currentSubPage", "INFO");
	}

	@RequestMapping("/staffOrder")
	@Authorize(value = AccessRight.SERVICE_SUPPLIER, checkAncestors = false)
	public ModelAndView staffOrderPage() throws IException {
		return createModelAndView("servicer/staffOrder").addObject("currentPage", "STAFF").addObject("currentSubPage", "ORDER");
	}

	@RequestMapping("/staff")
	@Authorize(value = AccessRight.SERVICE_SUPPLIER, checkAncestors = false)
	public ModelAndView staffPage() throws IException {
		return createModelAndView("servicer/staff").addObject("currentPage", "STAFF").addObject("currentSubPage", "INFO");
	}

	@RequestMapping("/training")
	@Authorize(value = AccessRight.SERVICE_SUPPLIER, checkAncestors = false)
	public ModelAndView trainingPage() throws IException {
		return createModelAndView("servicer/training").addObject("currentPage", "TRAINING");
	}

	@RequestMapping("/transaction")
	@Authorize(value = AccessRight.SERVICE_SUPPLIER, checkAncestors = false)
	public ModelAndView transactionPage() throws IException {
		return createModelAndView("servicer/transaction").addObject("currentPage", "TRANSACTION");
	}

	@Override
	@Authorize(value = AccessRight.SERVICE_SUPPLIER, checkAncestors = false)
	protected ModelAndView createModelAndView(final String viewName) {
		return super.createModelAndView(viewName).addObject("platform", "SERVICER");
	}
}
