layoutApp.directive('customOnChange', function() {
	return {
		restrict : 'A',
		link : function(scope, element, attrs) {
			var onChangeHandler = scope.$eval(attrs.customOnChange);
			element.bind('change', onChangeHandler);
		}
	};
});

layoutApp.controller('contentController', function($scope, $http, $window, errorHandler, serviceInfoId) {
	$scope.serviceInfoId = serviceInfoId;

	$scope.getServiceInfo = function() {
		if ($scope.serviceInfoId > 0) {
			$http({
				method : "GET",
				url : "/ajax/service?id=" + $scope.serviceInfoId + "&rsexp=images,serviceCategory[parent]"
			}).success(function(response) {
				$scope.serviceInfo = response.data[0];
				$scope.serviceInfo.active = $scope.serviceInfo.status.code == 'A'
				$scope.populateSubCategory();
				if ($scope.serviceInfo.image != undefined && $scope.serviceInfo.image != null) {
					$scope.imageUrl = "/ajax/content/image/" + $scope.serviceInfo.image;
				}

				if ($scope.serviceInfo.province != null && $scope.serviceInfo.province != undefined) {
					for (var index = 0; index < $scope.provinces.length; index++) {
						if ($scope.provinces[index].code == $scope.serviceInfo.province.code) {
							$scope.selectedProvinceAndCity.province = $scope.provinces[index];

							if ($scope.serviceInfo.city != null && $scope.serviceInfo.city != undefined) {
								for (var index2 = 0; index2 < $scope.selectedProvinceAndCity.province.cities.length; index2++) {
									if ($scope.selectedProvinceAndCity.province.cities[index2].code == $scope.serviceInfo.city.code) {
										$scope.selectedProvinceAndCity.city = $scope.selectedProvinceAndCity.province.cities[index2];
										break;
									}
								}
							}

							break;
						}
					}
				}

			}).error(function(response) {
				errorHandler($scope, response);
			});
		} else {
			$scope.serviceInfo = {
				name : "",
				price : "",
				status : {
					code : 'A'
				},
				serviceCategory : {
					id : null,
					parent : {
						id : null
					}
				}
			};

			$scope.serviceInfo.active = true;
		}
	};

	$http({
		method : "GET",
		url : "/ajax/common/lookup/PMMTHD"
	}).success(function(response) {
		$scope.paymentMethods = response.data;
		$http({
			method : "GET",
			url : "/ajax/common/lookup/PMTYPE"
		}).success(function(response) {
			$scope.paymentTypes = response.data;
			$http({
				method : "GET",
				url : "/ajax/service/category?status=A"
			}).success(function(response) {
				$scope.categories = response.data;
				$http({
					method : "GET",
					url : "/ajax/common/province",
				}).success(function(response) {
					$scope.provinces = response.data;
					$scope.getServiceInfo();
				}).error(function(response) {
					errorHandler($scope, response);
				});
			}).error(function(response) {
				errorHandler($scope, response);
			});
		}).error(function(response) {
			errorHandler($scope, response);
		});
	}).error(function(response) {
		errorHandler($scope, response);
	});

	var subCategoryMapping = {};

	$scope.populateSubCategory = function() {
		if (subCategoryMapping[$scope.serviceInfo.serviceCategory.parent.id] == undefined) {
			$scope.subCategories = [];
			$http({
				method : "GET",
				url : "/ajax/service/category?status=A&parentId=" + $scope.serviceInfo.serviceCategory.parent.id
			}).success(function(response) {
				subCategoryMapping[$scope.serviceInfo.serviceCategory.parent.id] = response.data;
				$scope.subCategories = subCategoryMapping[$scope.serviceInfo.serviceCategory.parent.id];
			}).error(function(response) {
				errorHandler($scope, response);
			});
		} else {
			$scope.subCategories = subCategoryMapping[$scope.serviceInfo.serviceCategory.parent.id];
		}
	}

	$scope.selectedProvinceAndCity = {
		province : null,
		city : null
	}

	$scope.selectImage = function(event) {
		if (event.target.files.length > 0) {
			$scope.newImage = event.target.files[0];
		} else {
			$scope.newImage = {};
		}
	};

	$scope.uploadPhoto = function() {
		var fd = new FormData();
		fd.append("IMAGE", $scope.newImage);
		$http({
			method : "POST",
			url : "/ajax/service/" + $scope.serviceInfoId + "/upload",
			transformRequest : angular.identity,
			headers : {
				'Content-Type' : undefined
			},
			data : fd
		}).success(function(response) {
			$scope.serviceInfo.images.push(response.data[0]);
		}).error(function(response) {
			errorHandler($scope, response);
		});
	};

	$scope.deletePhoto = function(index) {
		var uuid = $scope.serviceInfo.images[index];
		$http({
			method : "DELETE",
			url : "/ajax/service/" + $scope.serviceInfoId + "/" + uuid,
		}).success(function(response) {
			$scope.serviceInfo.images.splice(index, 1);
		}).error(function(response) {
			errorHandler($scope, response);
		});
	};

	$scope.apply = function() {
		$scope.serviceInfo.status.code = $scope.serviceInfo.active ? 'A' : 'O';

		if ($scope.selectedProvinceAndCity.province == null && $scope.selectedProvinceAndCity.province == undefined) {
			$scope.serviceInfo.province = null;
		} else {
			$scope.serviceInfo.province = {
				code : $scope.selectedProvinceAndCity.province.code
			};
		}

		if ($scope.selectedProvinceAndCity.city == null && $scope.selectedProvinceAndCity.city == undefined) {
			$scope.serviceInfo.city = null;
		} else {
			$scope.serviceInfo.city = {
				code : $scope.selectedProvinceAndCity.city.code
			};
		}

		if ($scope.serviceInfoId > 0) {
			$http({
				method : "PUT",
				url : "/ajax/service/",
				data : {
					data : [ $scope.serviceInfo ]
				}
			}).success(function(response) {
				$window.location.href = "/servicer/service";
			}).error(function(response) {
				errorHandler($scope, response);
			});
		} else {
			$http({
				method : "POST",
				url : "/ajax/service/",
				data : {
					data : [ $scope.serviceInfo ]
				}
			}).success(function(response) {
				$window.location.href = "/servicer/service/edit/" + response.data[0].id;
			}).error(function(response) {
				errorHandler($scope, response);
			});
		}
	};
	$scope.back = function() {
		$window.location.href = "/servicer/service"
	};
});