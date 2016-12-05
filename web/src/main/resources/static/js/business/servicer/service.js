layoutApp.controller('contentController', function($scope, $http, $window, errorHandler) {
	$scope.filterData = {
		name : ""
	};

	$scope.searchServices = function() {
		var ajaxUrl = "/ajax/service/me";

		if ($scope.filterData.name != undefined && $scope.filterData.name != "") {
			ajaxUrl += "?name=" + $scope.filterData.name;
		}

		$http({
			method : "GET",
			url : ajaxUrl
		}).success(function(response) {
			$scope.services = response.data;
		}).error(function(response) {
			errorHandler($scope, response);
		});
	};

	$scope.newService = function() {
		$window.location.href = "/servicer/service/new";
	};

	$scope.editService = function(service) {
		$window.location.href = "/servicer/service/edit/" + service.id;
	};
});