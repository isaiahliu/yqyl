layoutApp.controller('contentController', function($scope, $http, $window, errorHandler) {
	$scope.pagingData = {
		pageIndex : 1,
		pageSize : 10
	};

	$http({
		method : "GET",
		url : "/ajax/common/lookup/SRCSTAT"
	}).success(function(response) {
		$scope.statuses = response.data;
	}).error(function(response) {
		errorHandler($scope, response);
	});

	$scope.filterData = {
		userId : "",
		name : "",
		status : "A",
		identity : ""
	};

	$scope.searchUsers = function() {
		var ajaxUrl = "/ajax/user/receiver?rsexp=user";

		ajaxUrl += "&pageIndex=" + ($scope.pagingData.pageIndex - 1);
		ajaxUrl += "&pageSize=" + $scope.pagingData.pageSize;

		if ($scope.filterData.name != undefined && $scope.filterData.name != "") {
			ajaxUrl += "&name=" + $scope.filterData.name;
		}

		if ($scope.filterData.userId != undefined && $scope.filterData.userId != "") {
			ajaxUrl += "&userId=" + $scope.filterData.userId;
		}

		if ($scope.filterData.status != undefined && $scope.filterData.status != "") {
			ajaxUrl += "&status=" + $scope.filterData.status;
		}

		if ($scope.filterData.identity != undefined && $scope.filterData.identity != "") {
			ajaxUrl += "&identity=" + $scope.filterData.identity;
		}

		$http({
			method : "GET",
			url : ajaxUrl
		}).success(function(response) {
			$scope.receivers = response.data;
			response.meta.paging.pageIndex++;
			$scope.pagingData = response.meta.paging;
		}).error(function(response) {
			errorHandler($scope, response);
		});
	};
	$scope.view = function(receiver) {
		$window.location.href = "/admin/receiver/" + receiver.id;
	};

	$scope.searchUsers();
});