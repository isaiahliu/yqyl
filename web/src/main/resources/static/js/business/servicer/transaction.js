layoutApp
		.controller(
				'contentController',
				function($scope, $http, $window, $filter, errorHandler) {
					$scope.pagingData = {
						pageIndex : 1,
						pageSize : 10
					};

					$scope.dateOptions = {
						dateFormat : 'yy/mm/dd',
						changeMonth : true,
						changeYear : true,
						yearRange : "c-30:c+30",
						showAnim : "fadeIn"
					};

					$scope.filterData = {
						fromDate : new Date(),
						toDate : new Date(),
						staffNo : "",
						orderId : "",
						paymentCode : ""
					};

					$scope.searchTransactions = function() {
						var ajaxUrl = "/ajax/user/order?searchScope=supplier&paid=true&rsexp=paymentTransaction,drawbackTransaction";

						ajaxUrl += "&pageIndex="
								+ ($scope.pagingData.pageIndex - 1);
						ajaxUrl += "&pageSize=" + $scope.pagingData.pageSize;

						if ($scope.filterData.fromDate != undefined
								&& $scope.filterData.fromDate != null) {
							ajaxUrl += "&paymentFromDate="
									+ $filter('date')(
											$scope.filterData.fromDate,
											'yyyyMMdd');
						}

						if ($scope.filterData.toDate != undefined
								&& $scope.filterData.toDate != null) {
							ajaxUrl += "&paymentToDate="
									+ $filter('date')($scope.filterData.toDate,
											'yyyyMMdd');
						}

						if ($scope.filterData.staffNo != undefined
								&& $scope.filterData.staffNo != "") {
							ajaxUrl += "&staffNo=" + $scope.filterData.staffNo;
						}

						if ($scope.filterData.orderId != undefined
								&& $scope.filterData.orderId != "") {
							ajaxUrl += "&uid=" + $scope.filterData.orderId;
						}

						if ($scope.filterData.paymentCode != undefined
								&& $scope.filterData.paymentCode != "") {
							ajaxUrl += "&paymentCode="
									+ $scope.filterData.paymentCode;
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
					$scope.searchTransactions();
				});