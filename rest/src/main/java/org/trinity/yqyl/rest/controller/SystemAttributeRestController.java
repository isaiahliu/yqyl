package org.trinity.yqyl.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.trinity.common.dto.response.DefaultResponse;
import org.trinity.common.exception.IException;
import org.trinity.message.LookupParser;
import org.trinity.rest.controller.AbstractRestController;
import org.trinity.yqyl.common.accessright.Authorize;
import org.trinity.yqyl.common.message.dto.request.SystemAttributeRequest;
import org.trinity.yqyl.common.message.lookup.AccessRight;
import org.trinity.yqyl.common.message.lookup.SystemAttributeKey;
import org.trinity.yqyl.process.controller.base.ISystemAttributeProcessController;

@RestController
@RequestMapping("/common/systemattribute")
public class SystemAttributeRestController extends AbstractRestController {
    @Autowired
    private ISystemAttributeProcessController systemAttributeProcessController;

    @RequestMapping(value = "", method = RequestMethod.PUT)
    @Authorize(AccessRight.ADMINISTRATOR)
    public @ResponseBody ResponseEntity<DefaultResponse> ajaxUpdateSystemAttribute(@RequestBody final SystemAttributeRequest request)
            throws IException {
        systemAttributeProcessController.updateValues(request.getData());
        return createResponseEntity();
    }

    @RequestMapping(value = "/{key}", method = RequestMethod.GET)
    public ResponseEntity<DefaultResponse> getLookupsByType(@PathVariable("key") final String key) throws IException {
        final DefaultResponse response = new DefaultResponse();

        response.addData(systemAttributeProcessController.getValue(LookupParser.parse(SystemAttributeKey.class, key)));

        return createResponseEntity(response);
    }
}
