layoutApp.controller('contentController', function($scope, $http, $window, $filter, errorHandler) {
	$scope.pagingData = {
		pageIndex : 1,
		pageSize : 10
	}

	$scope.dateOptions = {
		dateFormat : 'yy/mm/dd',
	};

	$scope.filter = {
		enableFrom : true,
		from : new Date(),
		enableTo : true,
		to : new Date(),
		yiquanId : ""
	};

	$http({
		method : "GET",
		url : "/ajax/user/yiquan?searchScope=me"
	}).success(function(response) {
		$scope.yiquans = response.data;
	}).error(function(response) {
		errorHandler($scope, response);
	});

	$scope.search = function() {
		var ajaxUrl = "/ajax/account/posting?category=Y&searchScope=me&sortedBy=id_desc&rsexp=transaction,yiquan";

		ajaxUrl += "&pageIndex=" + ($scope.pagingData.pageIndex - 1);
		ajaxUrl += "&pageSize=" + $scope.pagingData.pageSize;

		if ($scope.filter.enableFrom) {
			ajaxUrl += "&fromDate=" + $filter('date')($scope.filter.from, 'yyyyMMdd');
		}

		if ($scope.filter.enableTo) {
			ajaxUrl += "&toDate=" + $filter('date')($scope.filter.to, 'yyyyMMdd');
		}

		if ($scope.filter.yiquanId != "") {
			ajaxUrl += "&yiquanId=" + $scope.filter.yiquanId;
		}

		$http({
			method : "GET",
			url : ajaxUrl
		}).success(function(response) {
			$scope.accountPostings = response.data;
			response.meta.paging.pageIndex++;
			$scope.pagingData = response.meta.paging;
		}).error(function(response) {
			errorHandler($scope, response);
		});
	};
});