package org.trinity.yqyl.rest.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.trinity.common.accessright.ISecurityUtil;
import org.trinity.common.dto.IResponse;
import org.trinity.common.dto.object.AccessrightDto;
import org.trinity.common.dto.object.AccessrightResponse;
import org.trinity.common.dto.response.DefaultResponse;
import org.trinity.common.exception.IException;
import org.trinity.message.IMessageResolverChain;
import org.trinity.rest.controller.AbstractRestController;
import org.trinity.yqyl.common.accessright.Authorize;
import org.trinity.yqyl.common.message.dto.domain.CityDto;
import org.trinity.yqyl.common.message.dto.domain.ProvinceDto;
import org.trinity.yqyl.common.message.dto.response.ProvinceResponse;
import org.trinity.yqyl.common.message.lookup.AccessRight;
import org.trinity.yqyl.common.message.lookup.City;
import org.trinity.yqyl.common.message.lookup.Province;

@RestController
@RequestMapping("/common")
public class CommonRestController extends AbstractRestController {
	@Autowired
	private IMessageResolverChain messageResolver;
	@Autowired
	private ISecurityUtil<AccessRight> securityUtil;

	@RequestMapping(value = "/accessright", method = RequestMethod.GET)
	@Authorize(AccessRight.ADMINISTRATOR)
	public ResponseEntity<AccessrightResponse> accessright() throws IException {
		final AccessrightResponse response = new AccessrightResponse();

		final AccessrightDto superUserDto = new AccessrightDto(AccessRight.SUPER_USER.getMessageCode(),
				messageResolver.getMessage(AccessRight.SUPER_USER));

		final List<AccessRight> allAccessrights = Arrays.stream(AccessRight.values()).collect(Collectors.toList());
		allAccessrights.remove(AccessRight.SUPER_USER);

		populateAccessrightDto(superUserDto, AccessRight.SUPER_USER, allAccessrights);

		if (securityUtil.hasAccessRight(AccessRight.SUPER_USER)) {
			response.addData(superUserDto);
		} else {
			response.addData(superUserDto.getChildren());
		}

		return createResponseEntity(response);
	}

	@RequestMapping(value = "/province", method = RequestMethod.GET)
	public ResponseEntity<ProvinceResponse> getProvinces() throws IException {
		final ProvinceResponse response = new ProvinceResponse();
		final List<ProvinceDto> provinces = Arrays.stream(Province.values()).map(item -> {
			final ProvinceDto provinceDto = new ProvinceDto();

			provinceDto.setCode(item.getMessageCode());
			provinceDto.setMessage(messageResolver.getMessage(item));

			provinceDto.setCities(Arrays.stream(City.values()).filter(city -> city.getProvince() == item).map(city -> {
				final CityDto cityDto = new CityDto();
				cityDto.setCode(city.getMessageCode());
				cityDto.setMessage(messageResolver.getMessage(city));

				return cityDto;
			}).collect(Collectors.toList()));

			return provinceDto;
		}).collect(Collectors.toList());

		response.getData().addAll(provinces);

		return createResponseEntity(response);
	}

	@RequestMapping(value = "/ping", method = RequestMethod.GET)
	public ResponseEntity<IResponse> ping() throws IException {
		final DefaultResponse response = new DefaultResponse();

		response.addData("Ping Successfully!");

		return createResponseEntity(response);
	}

	private void populateAccessrightDto(final AccessrightDto dto, final AccessRight accessright,
			final List<AccessRight> allAccessrights) {
		final List<AccessRight> children = allAccessrights.stream()
				.filter(item -> item.getParentAccessRight() == accessright).collect(Collectors.toList());

		allAccessrights.removeAll(children);

		for (final AccessRight child : children) {
			allAccessrights.remove(child);

			final AccessrightDto childDto = new AccessrightDto(child.getMessageCode(),
					messageResolver.getMessage(child));
			dto.getChildren().add(childDto);

			populateAccessrightDto(childDto, child, allAccessrights);
		}
	}
}
