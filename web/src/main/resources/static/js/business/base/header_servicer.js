layoutApp.controller('headerController', function($scope, $http, $window, $interval, isSupplier, errorHandler) {
	$scope.newRequirementCount = 0;
	$scope.requirements = undefined;
	$scope.lastReadTime = null;

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
		var ajaxUrl = "/ajax/user/order/requirement?sortedBy=announceTime_desc&status=A&searchScope=all";

		ajaxUrl += "&pageIndex=" + ($scope.pagingData.pageIndex - 1);
		ajaxUrl += "&pageSize=" + $scope.pagingData.pageSize;
		$http({
			method : "GET",
			url : ajaxUrl
		}).success(function(response) {
			$scope.requirements = response.data;
			if ($scope.lastReadTime == null) {
				$scope.lastReadTime = new Date(response.extraData["lastReadTime"]);
			}

			var newRequirementCount = 0;
			for (var index = 0; index < $scope.requirements.length; index++) {
				$scope.requirements[index].caught = false;
				if ($scope.requirements[index].announceTime > $scope.lastReadTime) {
					newRequirementCount++;
				}
			}

			$scope.newRequirementCount = newRequirementCount;
			response.meta.paging.pageIndex++;
			$scope.pagingData = response.meta.paging;

		}).error(function(response) {
			errorHandler($scope, response);
		});
	};

	$scope.checkingRequirements = false;
	$scope.populating = false;

	$scope.searchRequirements();

	if (isSupplier) {
		var timer = $interval(function() {
			if (!$scope.checkingRequirements && !$scope.populating) {
				$scope.pagingData.pageIndex = 1;
				$scope.searchRequirements();
			}
		}, 3000);
	}

	$scope.changeCheckingRequirementsStatus = function() {
		if ($scope.checkingRequirements) {
			$scope.lastReadTime = null;
		} else {
			$http({
				method : "POST",
				url : "/ajax/service/supplier/read"
			}).success(function(response) {
			}).error(function(response) {
				errorHandler($scope, response);
			});
		}
		$scope.checkingRequirements = !$scope.checkingRequirements;
	};

	$scope.catchRequirement = function(requirement) {
		$http({
			method : "POST",
			url : "/ajax/user/order/requirement/catch/" + requirement.id
		}).success(function(response) {
			requirement.caught = true;
		}).error(function(response) {
			errorHandler($scope, response);
		});
	};
});