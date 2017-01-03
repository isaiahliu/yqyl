layoutApp.controller('headerController', function($scope, $http, $window, $interval, errorHandler) {
	$scope.newRequirementCount = 0;

	$scope.logout = function() {
		$http({
			method : "PUT",
			url : "/ajax/user/logout",
			data : {
				username : $scope.username,
				password : $scope.password
			}
		}).success(function(response) {
			$window.location.href = "/home";
		}).error(function(response) {
			errorHandler($scope, response);
		});
	};

	$http({
		method : "GET",
		url : "/ajax/user/order/requirement"
	}).success(function(response) {
		$scope.requirements = response.data;
		$scope.lastReadTime = new Date(response.extraData["lastReadTime"])
	}).error(function(response) {
		errorHandler($scope, response);
	});

	var timer = $interval(function() {
		$scope.newRequirementCount++;
	}, 3000);
});