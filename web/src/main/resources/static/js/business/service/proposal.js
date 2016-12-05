layoutApp.controller('contentController', function($scope, $http, $window, errorHandler, $filter, serviceSupplierClientId,
		selectedServiceInfos, orderId) {
	$scope.dateOptions = {
		dateFormat : 'yy/mm/dd',
	};

	$scope.hours = [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23 ];

	if (orderId > 0) {
		$http({
			method : "GET",
			url : "/ajax/user/order/" + orderId + "?rsexp=serviceInfo[serviceSupplierClient]"
		}).success(function(response) {
			$scope.serviceOrder = response.data[0];
			$scope.serviceOrder.serviceDate = new Date($scope.serviceOrder.serviceDate);
			serviceSupplierClientId = $scope.serviceOrder.serviceInfo.serviceSupplierClient.id;

			$http({
				method : "GET",
				url : "/ajax/service/supplier/" + serviceSupplierClientId
			}).success(function(response) {
				$scope.serviceSupplierClient = response.data[0];
			}).error(function(response) {
				errorHandler($scope, response);
			});

			$http({
				method : "GET",
				url : "/ajax/service/supplier/" + serviceSupplierClientId + "/services?rsexp=serviceCategory"
			}).success(function(response) {
				for (var i = 0; i < response.data.length; i++) {
					var order = $scope.serviceOrder;
					if (order.serviceInfo.id == response.data[i].id) {
						$scope.selectedServices = response.data[i];
						response.data[i].order = {
							serviceDate : order.serviceDate,
							serviceHour : order.serviceHour,
							customAddress : order.address,
							customPhoneNo : order.phone
						};
						$scope.selectedServiceInfo = response.data[i];
						$scope.services = [ response.data[i] ];

						break;
					}
				}

			}).error(function(response) {
				errorHandler($scope, response);
			});
		}).error(function(response) {
			errorHandler($scope, response);
		});
	} else {
		$http({
			method : "GET",
			url : "/ajax/service/supplier/" + serviceSupplierClientId
		}).success(function(response) {
			$scope.serviceSupplierClient = response.data[0];
		}).error(function(response) {
			errorHandler($scope, response);
		});

		$http({
			method : "GET",
			url : "/ajax/service/supplier/" + serviceSupplierClientId + "/services?rsexp=serviceCategory"
		}).success(function(response) {
			$scope.services = response.data;
			$scope.selectedServiceInfo = $scope.services[0];
		}).error(function(response) {
			errorHandler($scope, response);
		});
	}

	$http({
		method : "GET",
		url : "/ajax/user/receiver/me"
	}).success(function(response) {
		$scope.members = response.data;
	}).error(function(response) {
		errorHandler($scope, response);
	});

	$scope.apply = function() {
		var serviceInfo = $scope.selectedServiceInfo;

		var address = serviceInfo.order.customAddress;
		if (address == "" || address == null || address == undefined) {
			address = serviceInfo.order.defaultMember.address;
		}

		var phone = serviceInfo.order.customPhoneNo;
		if (phone == "" || phone == null || phone == undefined) {
			phone = serviceInfo.order.defaultMember.cellphoneNo;
		}

		order = {
			id : null,
			serviceInfo : {
				id : serviceInfo.id
			},
			serviceDate : serviceInfo.order.serviceDate,
			serviceHour : serviceInfo.order.serviceHour,
			phone : phone,
			address : address
		};

		if (orderId > 0) {
			order.id = orderId;
			$http({
				method : "PUT",
				url : "/ajax/user/order/edit",
				data : {
					data : [ order ]
				}
			}).success(function(response) {
				$window.location.href = "/user/order/" + orderId;
			}).error(function(response) {
				errorHandler($scope, response);
			});
		} else {
			$http({
				method : "POST",
				url : "/ajax/user/order/proposal",
				data : {
					data : [ order ]
				}
			}).success(function(response) {
				$window.location.href = "/user/order/" + response.data[0].id;
			}).error(function(response) {
				errorHandler($scope, response);
			});
		}
	};

	$scope.cancel = function() {
		$window.location.href = "/service/info/" + serviceSupplierClientId;
	};
});