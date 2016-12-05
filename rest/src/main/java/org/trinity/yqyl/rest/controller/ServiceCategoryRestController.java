package org.trinity.yqyl.rest.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.trinity.yqyl.common.message.dto.domain.ServiceCategoryDto;
import org.trinity.yqyl.common.message.dto.domain.ServiceCategorySearchingDto;
import org.trinity.yqyl.common.message.dto.request.ServiceCategoryRequest;
import org.trinity.yqyl.common.message.dto.response.ServiceCategoryResponse;
import org.trinity.yqyl.process.controller.base.IServiceCategoryProcessController;

@RestController
@RequestMapping("/common/servicecategory")
public class ServiceCategoryRestController extends
        AbstractApplicationAwareCrudRestController<ServiceCategoryDto, ServiceCategorySearchingDto, IServiceCategoryProcessController, ServiceCategoryRequest, ServiceCategoryResponse> {
    @Override
    protected ServiceCategoryResponse createResponseInstance() {
        return new ServiceCategoryResponse();
    }
}
