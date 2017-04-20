package org.trinity.yqyl.web.controller.ajax;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.trinity.common.exception.IException;
import org.trinity.rest.controller.AbstractRestController;
import org.trinity.rest.util.IRestfulServiceUtil;
import org.trinity.yqyl.common.message.dto.domain.ContentDto;
import org.trinity.yqyl.common.message.dto.request.ContentRequest;
import org.trinity.yqyl.common.message.dto.response.ContentResponse;
import org.trinity.yqyl.web.util.Url;

@RestController
@RequestMapping("/ajax/content")
public class ContentAjaxController extends AbstractRestController {
	@Autowired
	private IRestfulServiceUtil restfulServiceUtil;

	@RequestMapping(value = "/image/{uuid}", method = RequestMethod.GET, produces = { "image/jpg", "image/jpeg",
			"image/bmp", "image/png" })
	public @ResponseBody byte[] ajaxGetFile(@PathVariable("uuid") final String uuid) throws IException {
		final ContentResponse response = restfulServiceUtil.callRestService(Url.CONTENT_DOWNLOAD, uuid, null, null,
				ContentResponse.class);

		if (!response.getErrors().isEmpty()) {
			return new byte[0];
		} else {
			return response.getData().get(0).getContent();
		}
	}

	@RequestMapping(value = "/image/upload", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Map<String, String>> ajaxUploadImage(final MultipartHttpServletRequest request,
			final String editorid) throws IException {

		final Map<String, String> result = new HashMap<>();
		if (request.getFileNames().hasNext()) {
			try {
				final ContentRequest contentRequest = new ContentRequest();

				final InputStream stream = request.getFile("IMAGE").getInputStream();
				final byte[] bytes = new byte[stream.available()];
				stream.read(bytes);

				final ContentDto dto = new ContentDto();
				dto.setUuid(editorid);
				dto.setContent(bytes);
				contentRequest.getData().add(dto);

				restfulServiceUtil.callRestService(Url.CONTENT_UPLOAD, null, contentRequest, null,
						ContentResponse.class);
				result.put("url", editorid);
				result.put("state", "SUCCESS");
			} catch (final Exception e) {
				result.put("state", "FAILED");
			}
		}

		return createResponseEntity(result);
	}
}
