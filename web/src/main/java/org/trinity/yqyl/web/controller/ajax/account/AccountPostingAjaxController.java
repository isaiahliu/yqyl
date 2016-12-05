package org.trinity.yqyl.web.controller.ajax.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.trinity.common.exception.IException;
import org.trinity.rest.controller.AbstractRestController;
import org.trinity.rest.util.IRestfulServiceUtil;
import org.trinity.yqyl.common.message.dto.domain.AccountPostingSearchingDto;
import org.trinity.yqyl.common.message.dto.response.AccountPostingResponse;
import org.trinity.yqyl.web.util.Url;

@RestController
@RequestMapping("/ajax/account/posting")
public class AccountPostingAjaxController extends AbstractRestController {
    @Autowired
    private IRestfulServiceUtil restfulServiceUtil;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<AccountPostingResponse> ajaxGetAccountPosting(final AccountPostingSearchingDto request) throws IException {
        final AccountPostingResponse response = restfulServiceUtil.callRestService(Url.ACCOUNT_POSTING, null, null, request,
                AccountPostingResponse.class);

        return createResponseEntity(response);
    }

}
