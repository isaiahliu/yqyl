package org.trinity.yqyl.rest.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.trinity.common.dto.object.PagingDto;
import org.trinity.common.dto.response.DefaultResponse;
import org.trinity.common.exception.IException;
import org.trinity.yqyl.common.message.dto.domain.AccountPostingSearchingDto;
import org.trinity.yqyl.common.message.dto.domain.YiquanDto;
import org.trinity.yqyl.common.message.dto.domain.YiquanSearchingDto;
import org.trinity.yqyl.common.message.dto.domain.YiquanTxDto;
import org.trinity.yqyl.common.message.dto.request.YiquanRequest;
import org.trinity.yqyl.common.message.dto.response.YiquanResponse;
import org.trinity.yqyl.common.message.dto.response.YiquanTxResponse;
import org.trinity.yqyl.process.controller.base.IYiquanProcessController;

@RestController
@RequestMapping("/yiquan")
public class YiquanRestController extends
        AbstractApplicationAwareCrudRestController<YiquanDto, YiquanSearchingDto, IYiquanProcessController, YiquanRequest, YiquanResponse> {

    @RequestMapping(value = "/bind", method = RequestMethod.PUT)
    public ResponseEntity<DefaultResponse> ajaxBindMe(@RequestBody final YiquanRequest request) throws IException {
        getDomainProcessController().bindMe(request.getData().get(0));

        return createResponseEntity(new DefaultResponse());
    }

    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public ResponseEntity<YiquanTxResponse> ajaxListDetails(final AccountPostingSearchingDto request)
            throws IException {
        final YiquanTxResponse response = new YiquanTxResponse();

        final Page<YiquanTxDto> data = getDomainProcessController().listDetails(request);

        final PagingDto responsePaging = new PagingDto();
        responsePaging.setPageIndex(data.getNumber());
        responsePaging.setPageSize(data.getSize());
        responsePaging.setPageCount(data.getTotalPages());
        responsePaging.setItemCount(data.getTotalElements());

        response.addData(data.getContent());
        response.getMeta().setPaging(responsePaging);

        return createResponseEntity(response);
    }

    @RequestMapping(value = "/topup", method = RequestMethod.POST)
    public ResponseEntity<DefaultResponse> ajaxTopupMe(@RequestBody final YiquanRequest request) throws IException {
        getDomainProcessController().topup(request.getData().get(0));

        return createResponseEntity(new DefaultResponse());
    }

    @RequestMapping(value = "/unbind", method = RequestMethod.PUT)
    public ResponseEntity<DefaultResponse> ajaxUnbindMe(@RequestBody final YiquanRequest request) throws IException {
        getDomainProcessController().unbindMe(request.getData().get(0));

        return createResponseEntity(new DefaultResponse());
    }

    @Override
    protected YiquanResponse createResponseInstance() {
        return new YiquanResponse();
    }
}
