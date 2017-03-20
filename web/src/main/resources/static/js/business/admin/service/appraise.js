layoutApp.controller('contentController', function($scope, $http, $window, errorHandler, $filter) {
	$scope.dateOptions = {
		dateFormat : 'yy/mm/dd',
	};

	$scope.pagingData = {
		pageIndex : 1,
		pageSize : 10
	};

	$scope.modifyAppraiseStatus = function(order) {
		if (order.appraise.status.code == 'A') {
			$http({
				method : "PUT",
				url : "/ajax/user/order/appraise/disable/" + order.uid
			}).success(function(response) {
				order.appraise.status.code = 'D';
			}).error(function(response) {
				errorHandler($scope, response);
			});
		} else {
			$http({
				method : "PUT",
				url : "/ajax/user/order/appraise/active/" + order.uid
			}).success(function(response) {
				order.appraise.status.code = 'A';
			}).error(function(response) {
				errorHandler($scope, response);
			});
		}
	};

	$scope.searchOrders = function() {
		var ajaxUrl = "/ajax/user/order?rsexp=appraise,user,serviceInfo[serviceSupplierClient]";

		ajaxUrl += "&pageIndex=" + ($scope.pagingData.pageIndex - 1);
		ajaxUrl += "&pageSize=" + $scope.pagingData.pageSize;
		ajaxUrl += "&status=S";

		if ($scope.filterData.username != undefined && $scope.filterData.username != "") {
			ajaxUrl += "&receiverUserName=" + $scope.filterData.username;
		}

		if ($scope.filterData.supplierId != undefined && $scope.filterData.supplierId != "") {
			ajaxUrl += "&serviceSupplierClientId=" + $scope.filterData.supplierId;
		}

		if ($scope.filterData.serviceTime != undefined && $scope.filterData.serviceTime != "") {
			ajaxUrl += "&settledDate=" + $filter('date')($scope.filterData.serviceTime, "yyyyMMdd");
		}

		$http({
			method : "GET",
			url : ajaxUrl
		}).success(function(response) {
			$scope.orders = response.data;
			response.meta.paging.pageIndex++;
			$scope.pagingData = response.meta.paging;
		}).error(function(response) {
			errorHandler($scope, response);
		});
	};
});