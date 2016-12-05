package org.trinity.yqyl.web.controller.ajax.service;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.trinity.common.dto.response.DefaultResponse;
import org.trinity.common.exception.IException;
import org.trinity.rest.controller.AbstractRestController;
import org.trinity.rest.util.IRestfulServiceUtil;
import org.trinity.yqyl.common.accessright.Authorize;
import org.trinity.yqyl.common.message.dto.domain.ContentDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceInfoSearchingDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceOrderSearchingDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceSupplierClientAuditingSearchingDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceSupplierClientDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceSupplierClientMaterialDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceSupplierClientSearchingDto;
import org.trinity.yqyl.common.message.dto.request.ContentRequest;
import org.trinity.yqyl.common.message.dto.request.ServiceSupplierClientRequest;
import org.trinity.yqyl.common.message.dto.response.ContentResponse;
import org.trinity.yqyl.common.message.dto.response.ServiceInfoResponse;
import org.trinity.yqyl.common.message.dto.response.ServiceOrderResponse;
import org.trinity.yqyl.common.message.dto.response.ServiceSupplierClientAuditingResponse;
import org.trinity.yqyl.common.message.dto.response.ServiceSupplierClientResponse;
import org.trinity.yqyl.common.message.lookup.AccessRight;
import org.trinity.yqyl.common.message.lookup.OrderStatus;
import org.trinity.yqyl.common.message.lookup.ServiceStatus;
import org.trinity.yqyl.web.util.Url;

@RestController
@RequestMapping("/ajax/service/supplier")
public class ServiceSupplierAjaxController extends AbstractRestController {
	@Autowired
	private IRestfulServiceUtil restfulServiceUtil;

	@RequestMapping(value = "/audit/{entityId}", method = RequestMethod.PUT)
	@Authorize(AccessRight.ADMINISTRATOR)
	public @ResponseBody DefaultResponse ajaxAuditServiceSupplier(@PathVariable("entityId") final Long entityId) throws IException {
		final ServiceSupplierClientDto serviceSupplierClientDto = new ServiceSupplierClientDto();
		serviceSupplierClientDto.setId(entityId);

		final ServiceSupplierClientRequest request = new ServiceSupplierClientRequest();
		request.getData().add(serviceSupplierClientDto);

		return restfulServiceUtil.callRestService(Url.SUPPLIER_AUDIT, null, request, null, DefaultResponse.class);
	}

	@RequestMapping(value = "/{entityId}", method = RequestMethod.GET)
	public @ResponseBody ServiceSupplierClientResponse ajaxGetServiceSupplier(@PathVariable("entityId") final Long entityId,
			final ServiceSupplierClientSearchingDto request) throws IException {
		return restfulServiceUtil.callRestService(Url.SUPPLIER, String.valueOf(entityId), null, request,
				ServiceSupplierClientResponse.class);
	}

	@RequestMapping(value = "/auditing", method = RequestMethod.GET)
	public @ResponseBody ServiceSupplierClientAuditingResponse ajaxGetServiceSupplierAuditings(
			final ServiceSupplierClientAuditingSearchingDto request) throws IException {
		return restfulServiceUtil.callRestService(Url.AUDITING, null, null, request, ServiceSupplierClientAuditingResponse.class);
	}

	@RequestMapping(value = "/orders", method = RequestMethod.GET)
	public @ResponseBody ServiceOrderResponse ajaxGetServiceSupplierOrders(final ServiceOrderSearchingDto request) throws IException {
		request.getStatus().add(OrderStatus.SETTLED.getMessageCode());

		return restfulServiceUtil.callRestService(Url.ORDER, null, null, request, ServiceOrderResponse.class);
	}

	@RequestMapping(value = "/{entityId}/services", method = RequestMethod.GET)
	public @ResponseBody ServiceInfoResponse ajaxGetServiceSupplierServices(@PathVariable("entityId") final Long entityId,
			final ServiceInfoSearchingDto request) throws IException {
		request.setServiceSupplierClientId(entityId);
		request.getStatus().add(ServiceStatus.ACTIVE.getMessageCode());

		return restfulServiceUtil.callRestService(Url.SERVICE_INFO, null, null, request, ServiceInfoResponse.class);
	}

	@RequestMapping(value = "/propose", method = RequestMethod.PUT)
	public @ResponseBody DefaultResponse ajaxProposeInfo(@RequestBody final ServiceSupplierClientRequest request) throws IException {
		return restfulServiceUtil.callRestService(Url.SUPPLIER_PROPOSE, null, request, null, DefaultResponse.class);
	}

	@RequestMapping(value = "register", method = RequestMethod.POST)
	public @ResponseBody ServiceSupplierClientResponse ajaxRegister() throws IException {
		return restfulServiceUtil.callRestService(Url.SUPPLIER_REGISTER, null, null, null, ServiceSupplierClientResponse.class);
	}

	@RequestMapping(value = "", method = RequestMethod.GET)
	public @ResponseBody ServiceSupplierClientResponse ajaxServices(final ServiceSupplierClientSearchingDto request) throws IException {
		return restfulServiceUtil.callRestService(Url.SUPPLIER, null, null, request, ServiceSupplierClientResponse.class);
	}

	@RequestMapping(value = "/public", method = RequestMethod.GET)
	public @ResponseBody ServiceSupplierClientResponse ajaxServicesPublicInfo(final ServiceSupplierClientSearchingDto request)
			throws IException {
		return restfulServiceUtil.callRestService(Url.SUPPLIER_PUBLIC, null, null, request, ServiceSupplierClientResponse.class);
	}

	@RequestMapping(value = "/{entityId}/upload", method = RequestMethod.POST)
	public ResponseEntity<DefaultResponse> ajaxUploadLogo(@PathVariable("entityId") final Long entityId,
			final MultipartHttpServletRequest request) throws IException {

		final DefaultResponse response = new DefaultResponse();
		if (request.getFileNames().hasNext()) {
			try {
				final ServiceSupplierClientSearchingDto searchingDto = new ServiceSupplierClientSearchingDto();
				searchingDto.setId(entityId);
				searchingDto.setSearchAllStatus(true);
				final ServiceSupplierClientResponse serviceSupplierClientResponse = restfulServiceUtil.callRestService(Url.SUPPLIER, null,
						null, searchingDto, ServiceSupplierClientResponse.class);

				final String uuid = serviceSupplierClientResponse.getData().get(0).getLogo();

				final ContentRequest contentRequest = new ContentRequest();

				final InputStream stream = request.getFile("IMAGE").getInputStream();
				final byte[] bytes = new byte[stream.available()];
				stream.read(bytes);

				final ContentDto dto = new ContentDto();
				dto.setUuid(uuid);
				dto.setContent(bytes);
				contentRequest.getData().add(dto);

				restfulServiceUtil.callRestService(Url.CONTENT_UPLOAD, null, contentRequest, null, ContentResponse.class);
			} catch (final Exception e) {
			}
		}

		return createResponseEntity(response);
	}

	@RequestMapping(value = "/{entityId}/material/upload", method = RequestMethod.POST)
	public ResponseEntity<DefaultResponse> ajaxUploadMaterial(@PathVariable("entityId") final Long entityId,
			final MultipartHttpServletRequest request) throws IException {

		final DefaultResponse response = new DefaultResponse();
		if (request.getFileNames().hasNext()) {
			try {
				final ServiceSupplierClientSearchingDto searchingDto = new ServiceSupplierClientSearchingDto();
				searchingDto.setId(entityId);
				searchingDto.setRsexp("material");
				searchingDto.setSearchAllStatus(true);
				final ServiceSupplierClientResponse serviceSupplierClientResponse = restfulServiceUtil.callRestService(Url.SUPPLIER, null,
						null, searchingDto, ServiceSupplierClientResponse.class);

				final ServiceSupplierClientMaterialDto material = serviceSupplierClientResponse.getData().get(0).getMaterial();

				request.getFileMap().forEach((fileName, content) -> {
					try {
						final String uuid;
						switch (fileName) {
							case "application":
								uuid = material.getApplicationCopy();
								break;
							case "businessLicense":
								uuid = material.getBusinessLicenseCopy();
								break;
							case "contract":
								uuid = material.getContractCopy();
								break;
							case "corporateChecking":
								uuid = material.getCorporateCheckingCopy();
								break;
							case "license":
								uuid = material.getLicenseCopy();
								break;
							case "businessCertificate":
								uuid = material.getBusinessCertificateCopy();
								break;
							default:
								return;
						}
						final ContentRequest contentRequest = new ContentRequest();

						final InputStream stream = content.getInputStream();
						final byte[] bytes = new byte[stream.available()];
						stream.read(bytes);

						final ContentDto dto = new ContentDto();
						dto.setUuid(uuid);
						dto.setContent(bytes);
						contentRequest.getData().add(dto);

						restfulServiceUtil.callRestService(Url.CONTENT_UPLOAD, null, contentRequest, null, ContentResponse.class);
					} catch (final Exception e) {
					}
				});
			} catch (final Exception e) {
			}
		}

		return createResponseEntity(response);
	}
}
