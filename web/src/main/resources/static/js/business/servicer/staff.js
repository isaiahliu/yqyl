layoutApp.controller('contentController', function($scope, $http, $window, errorHandler) {
	$scope.pagingData = {
		pageIndex : 1,
		pageSize : 10
	};

	$scope.filterData = {
		code : "",
		name : ""
	};

	$scope.searchStaffs = function() {
		var ajaxUrl = "/ajax/service/supplier/staff?rsexp=serviceCategories&searchScope=me&searchAllStatus=true";

		ajaxUrl += "&pageIndex=" + ($scope.pagingData.pageIndex - 1);
		ajaxUrl += "&pageSize=" + $scope.pagingData.pageSize;

		if ($scope.filterData.code != undefined && $scope.filterData.code != "") {
			ajaxUrl += "&code=" + $scope.filterData.code;
		}

		if ($scope.filterData.name != undefined && $scope.filterData.name != "") {
			ajaxUrl += "&name=" + $scope.filterData.name;
		}

		$http({
			method : "GET",
			url : ajaxUrl
		}).success(function(response) {
			$scope.staffs = response.data;
			response.meta.paging.pageIndex++;
			$scope.pagingData = response.meta.paging;
		}).error(function(response) {
			errorHandler($scope, response);
		});
	};
	$scope.newStaff = function() {
		$window.location.href = "/servicer/staff/new";
	};
	$scope.away = function(staff) {
		$http({
			method : "DELETE",
			url : "/ajax/service/supplier/staff/away/" + staff.id
		}).success(function(response) {
			staff.status.code = 'F';
		}).error(function(response) {
			errorHandler($scope, response);
		});
	};

	$scope.back = function(staff) {
		$http({
			method : "POST",
			url : "/ajax/service/supplier/staff/return/" + staff.id
		}).success(function(response) {
			staff.status.code = 'A';
		}).error(function(response) {
			errorHandler($scope, response);
		});
	};
	$scope.edit = function(staff) {
		$window.location.href = "/servicer/staff/edit/" + staff.id;
	};
	$scope.searchStaffs();
});