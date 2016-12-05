package org.trinity.yqyl.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.trinity.common.dto.response.DefaultResponse;
import org.trinity.common.exception.IException;
import org.trinity.message.IMessageResolverChain;
import org.trinity.rest.controller.AbstractRestController;
import org.trinity.yqyl.common.accessright.Authorize;
import org.trinity.yqyl.common.message.lookup.AccessRight;

@RestController
@RequestMapping("/common/resource")
public class ResourceRestController extends AbstractRestController {
    @Autowired
    private IMessageResolverChain messageResolver;

    @RequestMapping(value = "", method = RequestMethod.PUT)
    @Authorize(AccessRight.SUPER_USER)
    public ResponseEntity<DefaultResponse> refresh() throws IException {
        final DefaultResponse response = new DefaultResponse();

        messageResolver.refresh();

        return createResponseEntity(response);
    }
}
