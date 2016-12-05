package org.trinity.yqyl.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.trinity.common.accessright.ISecurityUtil;
import org.trinity.common.exception.IException;
import org.trinity.yqyl.common.message.dto.domain.ContentDto;
import org.trinity.yqyl.common.message.dto.domain.ContentSearchingDto;
import org.trinity.yqyl.common.message.dto.request.ContentRequest;
import org.trinity.yqyl.common.message.dto.response.ContentResponse;
import org.trinity.yqyl.common.message.lookup.AccessRight;
import org.trinity.yqyl.process.controller.base.IContentProcessController;

@RestController
@RequestMapping("/content")
public class ContentRestController extends
        AbstractApplicationAwareCrudRestController<ContentDto, ContentSearchingDto, IContentProcessController, ContentRequest, ContentResponse> {
    @Autowired
    private ISecurityUtil<AccessRight> securityUtil;

    @RequestMapping(value = "/download/{uuid}", method = RequestMethod.GET)
    public ResponseEntity<ContentResponse> downloadContent(@PathVariable("uuid") final String uuid) throws IException {
        final ContentResponse response = new ContentResponse();
        final ContentDto dto = getDomainProcessController().getOneByUuid(uuid);

        response.addData(dto);
        return createResponseEntity(response);
    }

    @RequestMapping(value = "/upload", method = RequestMethod.PUT)
    public ResponseEntity<ContentResponse> uploadContent(@RequestBody final ContentRequest request) throws IException {
        final ContentResponse response = new ContentResponse();
        final List<ContentDto> dtos = getDomainProcessController().addUpdateAll(request.getData());

        response.addData(dtos);
        return createResponseEntity(response);
    }

    @Override
    protected ContentResponse createResponseInstance() {
        return new ContentResponse();
    }

    @Override
    protected void validateAdd() throws IException {
        securityUtil.deny();
    }

    @Override
    protected void validateDelete() throws IException {
        securityUtil.deny();
    }

    @Override
    protected void validateGet() throws IException {
        securityUtil.deny();
    }

    @Override
    protected void validateUpdate() throws IException {
        securityUtil.deny();
    }
}
