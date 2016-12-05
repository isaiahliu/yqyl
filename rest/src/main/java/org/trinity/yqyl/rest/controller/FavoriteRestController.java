package org.trinity.yqyl.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.trinity.yqyl.common.message.dto.domain.FavoriteDto;
import org.trinity.yqyl.common.message.dto.domain.FavoriteSearchingDto;
import org.trinity.yqyl.common.message.dto.request.FavoriteRequest;
import org.trinity.yqyl.common.message.dto.response.FavoriteResponse;
import org.trinity.yqyl.process.controller.base.IFavoriteProcessController;

@RestController
@RequestMapping("/transaction/favorite")
public class FavoriteRestController extends
        AbstractApplicationAwareCrudRestController<FavoriteDto, FavoriteSearchingDto, IFavoriteProcessController, FavoriteRequest, FavoriteResponse> {

    @Override
    protected FavoriteResponse createResponseInstance() {
        return new FavoriteResponse();
    }
}
