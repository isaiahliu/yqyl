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

					$http({
						method : "GET",
						url : "/ajax/common/lookup/ODSTAT"
					}).success(function(response) {
						$scope.orderStatusLookups = response.data;
					}).error(function(response) {
						errorHandler($scope, response);
					});
					$scope.showOrder = function(uid) {
						$window.location.href = "/admin/service/order/" + uid;
					};

					$scope.searchOrders = function() {
						var ajaxUrl = "/ajax/user/order?rsexp=user,appraise,serviceInfo[serviceSupplierClient[user]]";

						ajaxUrl += "&pageIndex="
								+ ($scope.pagingData.pageIndex - 1);
						ajaxUrl += "&pageSize=" + $scope.pagingData.pageSize;

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

						if ($scope.filterData.status != undefined
								&& $scope.filterData.status != "") {
							ajaxUrl += "&status=" + $scope.filterData.status;
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