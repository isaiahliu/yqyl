layoutApp.controller('contentController', function($scope, $http, $window, errorHandler) {
	$scope.filter = {
		selectedStatus : "",
		selectedPaymentMethod : ""
	};

	$scope.checkingRequirements = false;

	$scope.orderPaging = {
		pageIndex : 1,
		pageSize : 10
	};

	$http({
		method : "GET",
		url : "/ajax/common/lookup/ODSTAT"
	}).success(function(response) {
		$scope.statuses = response.data;
		$http({
			method : "GET",
			url : "/ajax/common/lookup/PMMTHD"
		}).success(function(response) {
			$scope.paymentMethods = response.data;

			$scope.populateOrders();
		}).error(function(response) {
			errorHandler($scope, response);
		});
	}).error(function(response) {
		errorHandler($scope, response);
	});

	$scope.populateOrders =
			function() {
				var ajaxUrl =
						"/ajax/user/order?searchScope=me&rsexp=serviceInfo"
								+ "[serviceCategory,serviceSupplierClient]&fetchUnprocessedCount=true";

				ajaxUrl += "&pageIndex=" + ($scope.orderPaging.pageIndex - 1);
				ajaxUrl += "&pageSize=" + $scope.orderPaging.pageSize;
				ajaxUrl += "&sortedBy=id_desc";

				if ($scope.filter.selectedStatus != "") {
					ajaxUrl += "&status=" + $scope.filter.selectedStatus;
				}

				if ($scope.filter.selectedPaymentMethod != "") {
					ajaxUrl += "&paymentMethod=" + $scope.filter.selectedPaymentMethod;
				}

				$http({
					method : "GET",
					url : ajaxUrl
				}).success(function(response) {
					$scope.orders = response.data;
					$scope.unprocessedCount = response.extraData.unprocessedOrderCount;
					response.meta.paging.pageIndex++;
					$scope.orderPaging = response.meta.paging;
				}).error(function(response) {
					errorHandler($scope, response);
				});
			};

	$scope.appraise = function(id) {
		$window.location.href = "/user/order/appraise/" + id;
	};

	$scope.$watch('filter.selectedStatus', function(newValue, oldValue) {
		if (newValue != oldValue) {
			$scope.orderPaging = {
				pageIndex : 1,
				pageSize : 10
			};
			$scope.populateOrders();
		}
	});

	$scope.$watch('filter.selectedPaymentMethod', function(newValue, oldValue) {
		if (newValue != oldValue) {
			$scope.orderPaging = {
				pageIndex : 1,
				pageSize : 10
			};
			$scope.populateOrders();
		}
	});

	$scope.cancel = function(order) {
		var reason = prompt("请输入取消原因", "");
		if (reason != null) {
			$scope.applyCancel(order, reason);
		}
	};

	$scope.applyCancel = function(order, reason) {
		$http({
			method : "POST",
			url : "/ajax/user/order/cancel",
			data : {
				data : [ {
					uid : order.uid,
					operations : [ {
						params : [ reason ]
					} ]
				} ]
			}
		}).success(function(response) {
			order.status = response.data[0].status;
		}).error(function(response) {
			errorHandler($scope, response);
		});
	};

	$scope.confirmRequirement = function(order) {
		$http({
			method : "POST",
			url : "/ajax/user/order/confirmRequirement",
			data : {
				data : [ {
					uid : order.uid
				} ]
			}
		}).success(function(response) {
			$scope.populateOrders();
		}).error(function(response) {
			errorHandler($scope, response);
		});
	};

	$scope.rejectConfirmRequirement = function(order) {
		$http({
			method : "POST",
			url : "/ajax/user/order/rejectConfirmRequirement",
			data : {
				data : [ {
					uid : order.uid
				} ]
			}
		}).success(function(response) {
			$scope.populateOrders();
		}).error(function(response) {
			errorHandler($scope, response);
		});
	};

	$scope.cancelRequirement = function(requirement) {
		$http({
			method : "DELETE",
			url : "/ajax/user/order/requirement/" + requirement.id
		}).success(function(response) {
			$scope.populateRequirements();
		}).error(function(response) {
			errorHandler($scope, response);
		});
	};

	$scope.pagingData = {
		pageIndex : 1,
		pageSize : 10
	};

	$scope.populateRequirements = function() {
		var ajaxUrl = "/ajax/user/order/requirement?sortedBy=announceTime_desc&searchScope=me&status=A";

		ajaxUrl += "&pageIndex=" + ($scope.pagingData.pageIndex - 1);
		ajaxUrl += "&pageSize=" + $scope.pagingData.pageSize;
		$http({
			method : "GET",
			url : ajaxUrl
		}).success(function(response) {
			$scope.requirements = response.data;
			response.meta.paging.pageIndex++;
			$scope.pagingData = response.meta.paging;

		}).error(function(response) {
			errorHandler($scope, response);
		});
	};

	$scope.changeCheckingRequirements = function() {
		if (!$scope.checkingRequirements) {
			$scope.populateRequirements();
		}
		$scope.checkingRequirements = !$scope.checkingRequirements;
	};
});