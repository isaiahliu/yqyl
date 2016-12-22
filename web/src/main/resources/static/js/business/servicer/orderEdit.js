layoutApp.controller('contentController', function($scope, $http, $window, errorHandler, serviceOrderId) {
	$scope.dateOptions = {
		dateFormat : 'yy/mm/dd',
	};

	$scope.hours = [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23 ];

	$http({
		method : "GET",
		url : "/ajax/user/order?uid=" + serviceOrderId + "&rsexp=serviceInfo[serviceCategory,serviceSupplierClient]"
	}).success(
			function(response) {
				$scope.order = response.data[0];
				$scope.order.serviceDate = new Date($scope.order.serviceDate);

				if ($scope.order.status.code == 'G') {
					$http(
							{
								method : "GET",
								url : "/ajax/service/supplier/" + $scope.order.serviceInfo.serviceSupplierClient.id
										+ "/services?rsexp=serviceCategory"
							}).success(function(response) {
						$scope.services = response.data;
						for (var i = 0; i < $scope.services.length; i++) {
							if ($scope.services[i].id == $scope.order.serviceInfo.id) {
								$scope.order.serviceInfo = $scope.services[i];
								break;
							}
						}

					}).error(function(response) {
						errorHandler($scope, response);
					});
				}
			}).error(function(response) {
		errorHandler($scope, response);
	});

	$scope.back = function() {
		$window.location.href = "/servicer/order";
	}

	$scope.apply = function() {
		$http({
			method : "PUT",
			url : "/ajax/user/order/edit",
			data : {
				data : [ $scope.order ]
			}
		}).success(function(response) {
		}).error(function(response) {
			errorHandler($scope, response);
		});

		$window.location.href = "/servicer/order";
	}
});