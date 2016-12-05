layoutApp.controller('contentController', function($scope, $http, $window, errorHandler, serviceSupplierClientId) {
	$scope.status = 'desc';

	$http({
		method : "GET",
		url : "/ajax/service/supplier/" + serviceSupplierClientId + "?searchAllStatus=true&rsexp=images"
	}).success(
			function(response) {
				$scope.serviceSupplierClient = response.data[0];
				$scope.imageBar = {
					selectedImage : $scope.serviceSupplierClient.logo,
					pageIndex : 0,
					pageCount : ($scope.serviceSupplierClient.images.length - 1) / 4,
					previous : function() {
						if ($scope.imageBar.pageIndex > 0) {
							$scope.imageBar.pageIndex--;
						}
					},
					next : function() {
						if ($scope.imageBar.pageIndex < $scope.imageBar.pageCount - 1) {
							$scope.imageBar.pageIndex++;
						}
					}
				};
				$http(
						{
							method : "GET",
							url : "/ajax/service/supplier/" + serviceSupplierClientId
									+ "/services?rsexp=serviceCategory,stastic&searchAllStatus=true"
						}).success(function(response) {
					$scope.services = response.data;

					$scope.searchAppraises();
				}).error(function(response) {
					errorHandler($scope, response);
				});
			}).error(function(response) {
		errorHandler($scope, response);
	});

	$scope.searchAppraises =
			function() {
				var ajaxUrl =
						"/ajax/user/order/appraise?rsexp=serviceOrder[serviceInfo[serviceCategory]],&searchScope=all&serviceSupplierClientId="
								+ serviceSupplierClientId;

				ajaxUrl += "&pageIndex=" + ($scope.pagingData.pageIndex - 1);
				ajaxUrl += "&pageSize=" + $scope.pagingData.pageSize;

				$http({
					method : "GET",
					url : ajaxUrl
				}).success(function(response) {
					$scope.appraises = response.data;
					response.meta.paging.pageIndex++;
					$scope.pagingData = response.meta.paging;
				}).error(function(response) {
					errorHandler($scope, response);
				});
			};

	$scope.pagingData = {
		pageIndex : 1,
		pageSize : 10
	};

	$scope.apply = function() {
		$window.location.href = "/service/proposal/" + serviceSupplierClientId;
	};

	$scope.login = function() {
		$window.location.href = "/login";
	};
});