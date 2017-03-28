layoutApp.controller('contentController', function($scope, $http, $window, errorHandler, $filter) {
	$scope.dateOptions = {
		dateFormat : 'yy/mm/dd',
	};

	$scope.pagingData = {
		pageIndex : 1,
		pageSize : 10
	};

	var toDate = new Date();
	var fromDate = new Date(toDate.getTime() - 30 * 24 * 3600 * 1000);

	$scope.filterData = {
		fromServiceTime : fromDate,
		toServiceTime : toDate
	};

	$scope.searchOrders = function() {
		var ajaxUrl = "/ajax/service/supplier/report?searchScope=all";

		ajaxUrl += "&pageIndex=" + ($scope.pagingData.pageIndex - 1);
		ajaxUrl += "&pageSize=" + $scope.pagingData.pageSize;

		if ($scope.filterData.supplierName != undefined && $scope.filterData.supplierName != "") {
			ajaxUrl += "&username=" + $scope.filterData.supplierName;
		}

		if ($scope.filterData.fromServiceTime != undefined && $scope.filterData.fromServiceTime != "") {
			ajaxUrl += "&fromProposalTime=" + $filter('date')($scope.filterData.fromServiceTime, "yyyyMMdd");
		}

		if ($scope.filterData.toServiceTime != undefined && $scope.filterData.toServiceTime != "") {
			ajaxUrl += "&toProposalTime=" + $filter('date')($scope.filterData.toServiceTime, "yyyyMMdd");
		}

		$http({
			method : "GET",
			url : ajaxUrl
		}).success(function(response) {
			$scope.suppliers = response.data;
			response.meta.paging.pageIndex++;
			$scope.pagingData = response.meta.paging;
		}).error(function(response) {
			errorHandler($scope, response);
		});
	};
});