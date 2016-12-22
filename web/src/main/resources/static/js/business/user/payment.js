layoutApp.controller('contentController', function($scope, $http, $window, errorHandler, $filter, orderId) {

	$http({
		method : "GET",
		url : "/ajax/user/order?uid=" + orderId
	}).success(function(response) {
		$scope.order = response.data[0];
		$scope.payment = {
			orderId : $scope.order.id
		};
		$scope.awaitingPayment = $scope.order.status.code == 'A';
	}).error(function(response) {
		errorHandler($scope, response);
	});

	$http({
		method : "GET",
		url : "/ajax/user/receiver?rsexp=yiquan"
	}).success(function(response) {
		$scope.members = new Array();
		for (var index = 0; index < response.data.length; index++) {
			if (response.data[index].yiquan != null) {
				$scope.members.push(response.data[index]);
			}
		}

		$scope.selectedMember = {
			name : "其他",
			yiquan : null
		};
		$scope.members.push($scope.selectedMember);
	}).error(function(response) {
		errorHandler($scope, response);
	});

	$scope.apply = function() {
		if ($scope.selectedMember.yiquan != null) {
			$scope.payment.yiquanCode = $scope.selectedMember.yiquan.code;
		} else {
			$scope.payment.yiquanCode = $scope.customYiquanNo;
		}

		$http({
			method : "POST",
			url : "/ajax/user/order/payment",
			data : {
				payment : $scope.payment
			}
		}).success(function(response) {
			$window.location.reload();
		}).error(function(response) {
			errorHandler($scope, response);
		});
	};

	$scope.cancel = function() {
		$window.location.href = "/user/order";
	};
});