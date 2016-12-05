layoutApp.controller('contentController', function($scope, $http, $window, errorHandler) {
	$scope.pagingData = {
		pageIndex : 1,
		pageSize : 10
	};
	$scope.filterData = {
		supplierName : "",
		supplierId : ""
	};

	$scope.searchSuppliers = function() {
		var ajaxUrl = "/ajax/service/supplier?searchAllStatus=true";

		ajaxUrl += "&pageIndex=" + ($scope.pagingData.pageIndex - 1);
		ajaxUrl += "&pageSize=" + $scope.pagingData.pageSize;

		if ($scope.filterData.supplierName != undefined
				&& $scope.filterData.supplierName != "") {
			ajaxUrl += "&name=" + $scope.filterData.supplierName;
		}

		if ($scope.filterData.supplierId != undefined
				&& $scope.filterData.supplierId != "") {
			ajaxUrl += "&id=" + $scope.filterData.supplierId;
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

	$scope.audit = function(supplier) {
		$window.location.href = "/admin/supplier/" + supplier.id;
	};
});