layoutApp.controller('contentController', function($scope, $http, $window, errorHandler) {
	$scope.viewMore = false;

	$scope.healthDataMap = {};

	$http({
		method : "GET",
		url : "/ajax/user/receiver/me"
	}).success(function(response) {
		$scope.members = response.data;
	}).error(function(response) {
		errorHandler($scope, response);
	});

	$scope.bookPhysicalExamination = function() {
		$window.location.href = "/health/examination/book";
	};

	$scope.$watch('selectedMember', function(newValue, oldValue) {
		if (newValue != oldValue && newValue != '') {
			$scope.healthData = {};
			if ($scope.healthDataMap[newValue] == undefined) {
				$http({
					method : "GET",
					url : "/ajax/user/receiver/healthindicator/" + newValue
				}).success(function(response) {
					if (response.data.length > 0) {
						$scope.healthDataMap[newValue] = response.data[0];
						$scope.healthData = $scope.healthDataMap[newValue];
					} else {
						$scope.healthDataMap[newValue] = {};
					}
				}).error(function(response) {
					errorHandler($scope, response);
				});
			} else {
				$scope.healthData = $scope.healthDataMap[newValue];
			}
		}
	});
});