layoutApp.controller('contentController', function($scope, $http, $window, errorHandler) {
	$scope.selectedCode = "";
	$scope.paymentMethod = "bank";
	$http({
		method : "GET",
		url : "/ajax/user/receiver?searchScope=me&rsexp=yiquan"
	}).success(function(response) {
		$scope.clients = new Array();
		for (var index = 0; index < response.data.length; index++) {
			if (response.data[index].yiquan != null) {
				$scope.clients.push(response.data[index]);
			}
		}
	}).error(function(response) {
		errorHandler($scope, response);
	});

	$scope.apply = function() {
		var code = $scope.selectedCode;
		if (code == "") {
			code = $scope.customCode;
		}

		$http({
			method : "POST",
			url : "/ajax/user/yiquan/topup",
			data : {
				data : [ {
					code : code,
					topUpAmount : $scope.topUpAmount
				} ]
			}
		}).success(function(response) {
			$window.location.href = "/user/yiquan/topup";
		}).error(function(response) {
			errorHandler($scope, response);
		});
	};
});