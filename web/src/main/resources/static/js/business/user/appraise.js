layoutApp.controller('contentController', function($scope, $http, $window, errorHandler, orderId) {
	$http({
		method : "GET",
		url : "/ajax/user/order?uid=" + orderId + "&rsexp=appraise,serviceInfo"
	}).success(function(response) {
		$scope.order = response.data[0];
		if ($scope.order.appraise == undefined) {
			$scope.order.appraise = {
				uid : $scope.order.uid
			};
		}
		if ($scope.order.appraise.attitudeRate == undefined || $scope.order.appraise.attitudeRate == null) {
			$scope.order.appraise.attitudeRate = 5;
		}
		if ($scope.order.appraise.qualityRate == undefined || $scope.order.appraise.qualityRate == null) {
			$scope.order.appraise.qualityRate = 5;
		}
		if ($scope.order.appraise.onTimeRate == undefined || $scope.order.appraise.onTimeRate == null) {
			$scope.order.appraise.onTimeRate = 5;
		}
		if ($scope.order.appraise.staffRate == undefined || $scope.order.appraise.staffRate == null) {
			$scope.order.appraise.staffRate = 5;
		}

		$scope.tempAttitude = $scope.order.appraise.attitudeRate;
		$scope.tempQuality = $scope.order.appraise.qualityRate;
		$scope.tempOnTime = $scope.order.appraise.onTimeRate;
		$scope.tempStaff = $scope.order.appraise.staffRate;

	}).error(function(response) {
		errorHandler($scope, response);
	});

	$scope.updateTempAttitude = function(rate) {
		$scope.tempAttitude = rate;
	};

	$scope.recoverAttitude = function() {
		$scope.tempAttitude = $scope.order.appraise.attitudeRate;
	};

	$scope.updateAttitude = function() {
		$scope.order.appraise.attitudeRate = $scope.tempAttitude;
	};

	$scope.updateTempQuality = function(rate) {
		$scope.tempQuality = rate;
	};

	$scope.recoverQuality = function() {
		$scope.tempQuality = $scope.order.appraise.qualityRate;
	};

	$scope.updateQuality = function() {
		$scope.order.appraise.qualityRate = $scope.tempQuality;
	};

	$scope.updateTempOnTime = function(rate) {
		$scope.tempOnTime = rate;
	};

	$scope.recoverOnTime = function() {
		$scope.tempOnTime = $scope.order.appraise.onTimeRate;
	};

	$scope.updateOnTime = function() {
		$scope.order.appraise.onTimeRate = $scope.tempOnTime;
	};

	$scope.updateTempStaff = function(rate) {
		$scope.tempStaff = rate;
	};

	$scope.recoverStaff = function() {
		$scope.tempStaff = $scope.order.appraise.staffRate;
	};

	$scope.updateStaff = function() {
		$scope.order.appraise.staffRate = $scope.tempStaff;
	};

	$scope.apply = function() {
		$http({
			method : "POST",
			url : "/ajax/user/order/appraise",
			data : {
				data : [ $scope.order.appraise ]
			}
		}).success(function(response) {
			$window.location.href = "/user/order";
		}).error(function(response) {
			errorHandler($scope, response);
		});
	};
	$scope.cancel = function() {
		$window.location.href = "/user/order";
	};
});