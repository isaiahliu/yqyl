layoutApp.controller('headerController', function($scope, $http, $window, $interval, errorHandler) {
	$scope.newRequirementCount = 0;
	$scope.requirements = undefined;

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

	$scope.pagingData = {
		pageIndex : 1,
		pageSize : 10
	};

	$scope.searchRequirements = function() {
		var ajaxUrl = "/ajax/user/order/requirement?sortedBy=announceTime_desc";

		ajaxUrl += "&pageIndex=" + ($scope.pagingData.pageIndex - 1);
		ajaxUrl += "&pageSize=" + $scope.pagingData.pageSize;
		$http({
			method : "GET",
			url : ajaxUrl
		}).success(function(response) {
			$scope.requirements = response.data;
			$scope.lastReadTime = new Date(response.extraData["lastReadTime"]);

			var newRequirementCount = 0;
			for (var index = 0; index < $scope.requirements.length; index++) {
				if ($scope.requirements[index].announceTime > $scope.lastReadTime) {
					newRequirementCount++;
				}
			}

			$scope.newRequirementCount = newRequirementCount;

		}).error(function(response) {
			errorHandler($scope, response);
		});
	};

	$scope.checkingRequirements = false;
	$scope.populating = false;
	var timer = $interval(function() {
		if (!$scope.checkingRequirements && !$scope.populating || ($scope.requirements == undefined)) {
			$scope.pagingData.pageIndex = 1;
			$scope.searchRequirements();
		}
	}, 3000);
});