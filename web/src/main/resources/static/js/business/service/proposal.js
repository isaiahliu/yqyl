layoutApp.controller('contentController', function($scope, $http, $window, errorHandler, $filter, serviceSupplierClientId) {
	$scope.dateOptions = {
		dateFormat : 'yy/mm/dd',
	};

	$scope.hours = [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23 ];

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

	$http({
		method : "GET",
		url : "/ajax/user/receiver"
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
			serviceInfo : {
				id : serviceInfo.id
			},
			serviceDate : serviceInfo.order.serviceDate,
			serviceHour : serviceInfo.order.serviceHour,
			phone : phone,
			address : address
		};

		$http({
			method : "POST",
			url : "/ajax/user/order/proposal",
			data : {
				data : [ order ]
			}
		}).success(function(response) {
			if (serviceInfo.paymentMethod.code == 'O') {
				$window.location.href = "/user/order/payment/" + response.data[0].uid;
			} else {
				$window.location.href = "/user/order/" + response.data[0].uid;
			}
		}).error(function(response) {
			errorHandler($scope, response);
		});
	};

	$scope.cancel = function() {
		$window.location.href = "/service/info/" + serviceSupplierClientId;
	};
});