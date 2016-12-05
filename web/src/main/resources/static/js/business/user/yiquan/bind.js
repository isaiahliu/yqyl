layoutApp.controller('contentController', function($scope, $http, $window, clientId, errorHandler) {
	$scope.binded = true;
	$scope.unbinding = false;
	$http({
		method : "GET",
		url : "/ajax/user/receiver?rsexp=yiquan&id=" + clientId
	}).success(function(response) {
		$scope.client = response.data[0];
		if ($scope.client.yiquan == null) {
			$scope.binded = false;
			$scope.client.yiquan = {};
		}

		$scope.client.yiquan.serviceReceiverClientId = clientId;

	}).error(function(response) {
		errorHandler($scope, response);
	});

	$scope.apply = function() {
		$http({
			method : "PUT",
			url : "/ajax/user/yiquan/bind",
			data : {
				data : [ $scope.client.yiquan ]
			}
		}).success(function(response) {
			$window.location.reload();
		}).error(function(response) {
			errorHandler($scope, response);
		});
	};

	$scope.unbind = function() {
		$http({
			method : "PUT",
			url : "/ajax/user/yiquan/unbind",
			data : {
				data : [ $scope.client.yiquan ]
			}
		}).success(function(response) {
			$window.location.href = "/user/yiquan";
		}).error(function(response) {
			errorHandler($scope, response);
		});
	};
});