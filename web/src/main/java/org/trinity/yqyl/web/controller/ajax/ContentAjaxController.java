package org.trinity.yqyl.web.controller.ajax;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.trinity.common.exception.IException;
import org.trinity.rest.controller.AbstractRestController;
import org.trinity.rest.util.IRestfulServiceUtil;
import org.trinity.yqyl.common.message.dto.response.ContentResponse;
import org.trinity.yqyl.web.util.Url;

@RestController
@RequestMapping("/ajax/content")
public class ContentAjaxController extends AbstractRestController {
    @Autowired
    private IRestfulServiceUtil restfulServiceUtil;

    @RequestMapping(value = "image/{uuid}", method = RequestMethod.GET, produces = { "image/jpg", "image/jpeg", "image/bmp", "image/png" })
    public @ResponseBody byte[] ajaxGetFile(@PathVariable("uuid") final String uuid) throws IException {
        final ContentResponse response = restfulServiceUtil.callRestService(Url.CONTENT_DOWNLOAD, uuid, null, null, ContentResponse.class);

        if (!response.getErrors().isEmpty()) {
            return new byte[0];
        } else {
            return response.getData().get(0).getContent();
        }
    }
}
