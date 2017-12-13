layoutApp
		.controller(
				'contentController',
				function($scope, $http, $window, errorHandler, $filter) {
					$scope.dateOptions = {
						dateFormat : 'yy/mm/dd',
						changeMonth : true,
						changeYear : true,
						yearRange : "c-30:c+30",
						showAnim : "fadeIn"
					};

					$scope.pagingData = {
						pageIndex : 1,
						pageSize : 10
					};

					$scope.filterData = {
						appraiseLevel : "0"
					};

					$scope.modifyAppraiseStatus = function(order) {
						if (order.appraise.status.code == 'A') {
							$http(
									{
										method : "PUT",
										url : "/ajax/user/order/appraise/disable/"
												+ order.uid
									}).success(function(response) {
								order.appraise.status.code = 'D';
							}).error(function(response) {
								errorHandler($scope, response);
							});
						} else {
							$http(
									{
										method : "PUT",
										url : "/ajax/user/order/appraise/active/"
												+ order.uid
									}).success(function(response) {
								order.appraise.status.code = 'A';
							}).error(function(response) {
								errorHandler($scope, response);
							});
						}
					};

					$scope.searchOrders = function() {
						var ajaxUrl = "/ajax/user/order?rsexp=appraise,user,serviceInfo[serviceSupplierClient[user]]";

						ajaxUrl += "&pageIndex="
								+ ($scope.pagingData.pageIndex - 1);
						ajaxUrl += "&pageSize=" + $scope.pagingData.pageSize;
						ajaxUrl += "&status=S";

						if ($scope.filterData.username != undefined
								&& $scope.filterData.username != "") {
							ajaxUrl += "&receiverUserName="
									+ $scope.filterData.username;
						}

						if ($scope.filterData.supplierName != undefined
								&& $scope.filterData.supplierName != "") {
							ajaxUrl += "&supplierUserName="
									+ $scope.filterData.supplierName;
						}

						if ($scope.filterData.fromServiceTime != undefined
								&& $scope.filterData.fromServiceTime != "") {
							ajaxUrl += "&fromSettledTime="
									+ $filter('date')(
											$scope.filterData.fromServiceTime,
											"yyyyMMdd");
						}

						if ($scope.filterData.toServiceTime != undefined
								&& $scope.filterData.toServiceTime != "") {
							ajaxUrl += "&toSettledTime="
									+ $filter('date')(
											$scope.filterData.toServiceTime,
											"yyyyMMdd");
						}

						if ($scope.filterData.appraiseLevel != undefined
								&& $scope.filterData.appraiseLevel != "") {
							ajaxUrl += "&appraiseLevel="
									+ $scope.filterData.appraiseLevel;
						}

						if ($scope.filterData.orderUid != undefined
								&& $scope.filterData.orderUid != "") {
							ajaxUrl += "&partialUid="
									+ $scope.filterData.orderUid;
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