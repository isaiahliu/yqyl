layoutApp.controller('contentController', function($scope, $http, $window, $filter, errorHandler) {
	$scope.pagingData = {
		pageIndex : 1,
		pageSize : 10
	}

	$scope.filter = {
		name : "",
		address : ""
	};

	$scope.search = function() {
		var ajaxUrl = "/ajax/service/supplier/public";

		ajaxUrl += "?pageIndex=" + ($scope.pagingData.pageIndex - 1);
		ajaxUrl += "&pageSize=" + $scope.pagingData.pageSize;

		if ($scope.filter.name != "") {
			ajaxUrl += "&name=" + $scope.filter.name;
		}

		if ($scope.filter.address != "") {
			ajaxUrl += "&address=" + $scope.filter.address;
		}

		$http({
			method : "GET",
			url : ajaxUrl
		}).success(function(response) {
			$scope.servicers = response.data;
			response.meta.paging.pageIndex++;
			$scope.pagingData = response.meta.paging;
		}).error(function(response) {
			errorHandler($scope, response);
		});
	};
});