layoutApp.controller('contentController', function($scope, $http, $window, errorHandler) {
	$scope.pagingData = {
		pageIndex : 1,
		pageSize : 10
	};
	$scope.filterData = {
		id : "",
		username : ""
	};

	$scope.searchUsers = function() {
		var ajaxUrl = "/ajax/user?rsexp=token";

		ajaxUrl += "&pageIndex=" + ($scope.pagingData.pageIndex - 1);
		ajaxUrl += "&pageSize=" + $scope.pagingData.pageSize;

		if ($scope.filterData.username != undefined && $scope.filterData.username != "") {
			ajaxUrl += "&username=" + $scope.filterData.username;
		}

		if ($scope.filterData.id != undefined && $scope.filterData.id != "") {
			ajaxUrl += "&id=" + $scope.filterData.id;
		}

		$http({
			method : "GET",
			url : ajaxUrl
		}).success(function(response) {
			$scope.users = response.data;
			response.meta.paging.pageIndex++;
			$scope.pagingData = response.meta.paging;
		}).error(function(response) {
			errorHandler($scope, response);
		});
	};
	$scope.assignPermission = function(user) {
		$window.location.href = "/admin/permission/edit/" + user.id;
	};
});