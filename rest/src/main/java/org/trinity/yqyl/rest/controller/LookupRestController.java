package org.trinity.yqyl.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.trinity.common.dto.object.LookupResponse;
import org.trinity.common.dto.response.DefaultResponse;
import org.trinity.common.exception.IException;
import org.trinity.rest.controller.AbstractRestController;
import org.trinity.yqyl.common.accessright.Authorize;
import org.trinity.yqyl.common.message.lookup.AccessRight;
import org.trinity.yqyl.process.controller.base.ILookupProcessController;

@RestController
@RequestMapping("/common/lookup")
public class LookupRestController extends AbstractRestController {
    @Autowired
    private ILookupProcessController lookupProcessController;

    @RequestMapping(value = "/{type}", method = RequestMethod.GET)
    public ResponseEntity<LookupResponse> getLookupsByType(@PathVariable("type") final String lookupType) throws IException {
        final LookupResponse response = new LookupResponse();

        response.getData().addAll(lookupProcessController.getLookupsByType(lookupType));

        return createResponseEntity(response);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    @Authorize(AccessRight.SUPER_USER)
    public ResponseEntity<DefaultResponse> refresh() throws IException {
        final DefaultResponse response = new DefaultResponse();

        lookupProcessController.refresh();

        return createResponseEntity(response);
    }
}
