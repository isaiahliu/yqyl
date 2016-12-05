layoutApp.directive('uploadLogo', function() {
	return {
		restrict : 'A',
		link : function(scope, element, attrs) {
			var onChangeHandler = scope.$eval(attrs.uploadLogo);
			element.bind('change', onChangeHandler);
		}
	};
});

layoutApp.directive('uploadPhoto', function() {
	return {
		restrict : 'A',
		link : function(scope, element, attrs) {
			var onChangeHandler = scope.$eval(attrs.uploadPhoto);
			element.bind('change', onChangeHandler);
		}
	};
});

layoutApp.controller('contentController', function($scope, $http, $window, errorHandler, serviceSupplierClientId) {
	$scope.audited = true;

	var url = "/ajax/service/supplier?rsexp=bankAccount,material&searchAllStatus=true";
	if (serviceSupplierClientId > 0) {
		url += "&id=" + serviceSupplierClientId;
	} else {
		url += "&searchScope=me";
	}

	$http({
		method : "GET",
		url : url
	}).success(function(response) {
		if (response.data.length > 0) {
			$scope.serviceSupplierClient = response.data[0];
			$scope.logoUrl = "/ajax/content/image/" + $scope.serviceSupplierClient.logo;
			$scope.licenseUrl = "/ajax/content/image/" + $scope.serviceSupplierClient.material.licenseCopy;
			$scope.corporateCheckingUrl = "/ajax/content/image/" + $scope.serviceSupplierClient.material.corporateCheckingCopy;
			$scope.contractUrl = "/ajax/content/image/" + $scope.serviceSupplierClient.material.contractCopy;
			$scope.applicationUrl = "/ajax/content/image/" + $scope.serviceSupplierClient.material.applicationCopy;
			$scope.businessLicenseUrl = "/ajax/content/image/" + $scope.serviceSupplierClient.material.businessLicenseCopy;
			$scope.businessCertificateUrl = "/ajax/content/image/" + $scope.serviceSupplierClient.material.businessCertificateCopy;
			$scope.audited = $scope.serviceSupplierClient.status.code == 'A' || $scope.serviceSupplierClient.status.code == 'D';
			$scope.auditing = serviceSupplierClientId > 0;
		} else if (serviceSupplierClientId == 0) {
			$scope.step = "ANNOUNCEMENT1";
			$scope.serviceSupplierClient = undefined;
		}
	}).error(function(response) {
		errorHandler($scope, response);
	});

	$scope.accept = function() {
		$scope.step = "ANNOUNCEMENT2"
	};

	$http({
		method : "GET",
		url : "/ajax/common/lookup/BANK"
	}).success(function(response) {
		$scope.banks = response.data;
	}).error(function(response) {
		errorHandler($scope, response);
	});

	$http({
		method : "GET",
		url : "/ajax/common/lookup/CMTYPE"
	}).success(function(response) {
		$scope.companyTypes = response.data;
	}).error(function(response) {
		errorHandler($scope, response);
	});

	$http({
		method : "GET",
		url : "/ajax/common/lookup/ACCTYPE"
	}).success(function(response) {
		$scope.accountTypes = response.data;
	}).error(function(response) {
		errorHandler($scope, response);
	});

	$scope.registerSupplier = function() {
		$http({
			method : "POST",
			url : "/ajax/service/supplier/register"
		}).success(function(response) {
			$window.location.reload();
		}).error(function(response) {
			errorHandler($scope, response);
		});
	};

	$scope.uploadLogo = function(event) {
		if (event.target.files.length > 0) {
			var fd = new FormData();
			fd.append("IMAGE", event.target.files[0]);
			$http({
				method : "POST",
				url : "/ajax/service/supplier/" + $scope.serviceSupplierClient.id + "/upload",
				transformRequest : angular.identity,
				headers : {
					'Content-Type' : undefined
				},
				data : fd
			}).success(function(response) {
				$scope.logoUrl = '/ajax/content/image/' + $scope.serviceSupplierClient.logo + "?ticks=" + new Date().getTime();
			}).error(function(response) {
				errorHandler($scope, response);
			});
		}
	};

	$scope.uploadLicense = function(event) {
		$scope.uploadPhoto(event, "license", function() {
			$scope.licenseUrl = "/ajax/content/image/" + $scope.serviceSupplierClient.material.licenseCopy + "?ticks="
					+ new Date().getTime();
		});
	};
	$scope.uploadCorporateChecking = function(event) {
		$scope.uploadPhoto(event, "corporateChecking", function() {
			$scope.corporateCheckingUrl = "/ajax/content/image/" + $scope.serviceSupplierClient.material.corporateCheckingCopy + "?ticks="
					+ new Date().getTime();
		});
	};
	$scope.uploadContract = function(event) {
		$scope.uploadPhoto(event, "contract", function() {
			$scope.contractUrl = "/ajax/content/image/" + $scope.serviceSupplierClient.material.contractCopy + "?ticks="
					+ new Date().getTime();
		});
	};
	$scope.uploadApplication = function(event) {
		$scope.uploadPhoto(event, "application", function() {
			$scope.applicationUrl = "/ajax/content/image/" + $scope.serviceSupplierClient.material.applicationCopy + "?ticks="
					+ new Date().getTime();
		});
	};
	$scope.uploadBusinessLicense = function(event) {
		$scope.uploadPhoto(event, "businessLicense", function() {
			$scope.businessLicenseUrl = "/ajax/content/image/" + $scope.serviceSupplierClient.material.businessLicenseCopy + "?ticks="
					+ new Date().getTime();
		});
	};
	$scope.uploadBusinessCertificate = function(event) {
		$scope.uploadPhoto(event, "businessCertificate", function() {
			$scope.businessCertificateUrl = "/ajax/content/image/" + $scope.serviceSupplierClient.material.businessCertificateCopy
					+ "?ticks=" + new Date().getTime();
		});
	};
	$scope.uploadPhoto = function(event, file, urlHandler) {
		if (event.target.files.length > 0) {
			var fd = new FormData();
			fd.append(file, event.target.files[0]);
			$http({
				method : "POST",
				url : "/ajax/service/supplier/" + $scope.serviceSupplierClient.id + "/material/upload",
				transformRequest : angular.identity,
				headers : {
					'Content-Type' : undefined
				},
				data : fd
			}).success(function(response) {
				urlHandler();
			}).error(function(response) {
				errorHandler($scope, response);
			});
		}
	};

	$scope.save = function() {
		$http({
			method : "PUT",
			url : "/ajax/service/supplier/propose",
			data : {
				data : [ $scope.serviceSupplierClient ]
			}
		}).success(function(response) {
			$window.location.href = "/servicer";
		}).error(function(response) {
			errorHandler($scope, response);
		});
	};

	$scope.apply = function() {
		$scope.serviceSupplierClient.status.code = 'P';
		$scope.save();
	};

	$scope.cancel = function() {
		$window.location.href = "/";
	};

	$scope.auditingApply = function() {
		$http({
			method : "PUT",
			url : "/ajax/service/supplier/audit/" + serviceSupplierClientId
		}).success(function(response) {
			$window.location.href = "/admin/supplier";
		}).error(function(response) {
			errorHandler($scope, response);
		});
	};

	$scope.auditingReject = function() {
		$window.location.href = "/admin/supplier";
	};

	$scope.auditingCancel = function() {
		$window.location.href = "/admin/supplier";
	};
});