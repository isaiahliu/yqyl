package org.trinity.yqyl.web.controller.ajax;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.trinity.common.dto.object.AccessrightResponse;
import org.trinity.common.dto.object.LookupResponse;
import org.trinity.common.dto.response.DefaultResponse;
import org.trinity.common.exception.IException;
import org.trinity.message.IMessageResolverChain;
import org.trinity.rest.controller.AbstractRestController;
import org.trinity.rest.util.IRestfulServiceUtil;
import org.trinity.yqyl.common.accessright.Authorize;
import org.trinity.yqyl.common.message.dto.domain.SystemQaSearchingDto;
import org.trinity.yqyl.common.message.dto.request.SystemAttributeRequest;
import org.trinity.yqyl.common.message.dto.request.SystemQaRequest;
import org.trinity.yqyl.common.message.dto.response.SystemQaResponse;
import org.trinity.yqyl.common.message.lookup.AccessRight;
import org.trinity.yqyl.web.util.Url;

@RestController
@RequestMapping("/ajax/common")
public class CommonAjaxController extends AbstractRestController {
	@Autowired
	private IRestfulServiceUtil restfulServiceUtil;

	@Autowired
	protected IMessageResolverChain messageResolver;

	@RequestMapping(value = "/qa", method = RequestMethod.POST)
	@Authorize(AccessRight.ADMINISTRATOR)
	public @ResponseBody SystemQaResponse ajaxAddSystemQa(@RequestBody final SystemQaRequest request)
			throws IException {
		return restfulServiceUtil.callRestService(Url.QA_ADD, null, request, null, SystemQaResponse.class);
	}

	@RequestMapping(value = "/accessright", method = RequestMethod.GET)
	@Authorize(AccessRight.ADMINISTRATOR)
	public @ResponseBody AccessrightResponse ajaxGetAccessrights() throws IException {
		return restfulServiceUtil.callRestService(Url.ACCESSRIGHT, null, null, null, AccessrightResponse.class);
	}

	@RequestMapping(value = "/lookup/{lookupType}", method = RequestMethod.GET)
	public @ResponseBody LookupResponse ajaxGetLookups(@PathVariable("lookupType") final String lookupType)
			throws IException {
		return restfulServiceUtil.callRestService(Url.LOOKUP_TYPE, lookupType, null, null, LookupResponse.class);
	}

	@RequestMapping(value = "/qa", method = RequestMethod.GET)
	public @ResponseBody SystemQaResponse ajaxGetQa(final SystemQaSearchingDto request) throws IException {
		return restfulServiceUtil.callRestService(Url.QA_GET, null, null, request, SystemQaResponse.class);
	}

	@RequestMapping(value = "/systemattribute/{key}", method = RequestMethod.GET)
	@Authorize(AccessRight.ADMINISTRATOR)
	public @ResponseBody DefaultResponse ajaxGetSystemAttribute(@PathVariable("key") final String key)
			throws IException {
		return restfulServiceUtil.callRestService(Url.SYSTEM_ATTRIBUTE_GET, key, null, null, DefaultResponse.class);
	}

	@RequestMapping(value = "/resource", method = RequestMethod.PUT)
	public @ResponseBody DefaultResponse ajaxRefreshResource() throws IException {
		messageResolver.refresh();
		return restfulServiceUtil.callRestService(Url.RESOURCE_REFRESH, null, null, null, DefaultResponse.class);
	}

	@RequestMapping(value = "/systemattribute", method = RequestMethod.PUT)
	@Authorize(AccessRight.ADMINISTRATOR)
	public @ResponseBody DefaultResponse ajaxUpdateSystemAttribute(@RequestBody final SystemAttributeRequest request)
			throws IException {
		return restfulServiceUtil.callRestService(Url.SYSTEM_ATTRIBUTE_UPDATE, null, request, null,
				DefaultResponse.class);
	}

	@RequestMapping(value = "/qa", method = RequestMethod.PUT)
	@Authorize(AccessRight.ADMINISTRATOR)
	public @ResponseBody SystemQaResponse ajaxUpdateSystemQa(@RequestBody final SystemQaRequest request)
			throws IException {
		return restfulServiceUtil.callRestService(Url.QA_UPDATE, null, request, null, SystemQaResponse.class);
	}
}
