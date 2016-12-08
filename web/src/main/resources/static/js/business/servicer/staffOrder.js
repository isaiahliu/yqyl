layoutApp
		.controller(
				'contentController',
				function($scope, $http, $window, errorHandler) {
					$scope.pagingData = {
						pageIndex : 1,
						pageSize : 10
					};

					$scope.filterData = {
						id : "",
						name : ""
					};

					$scope.searchStaffs =
							function() {
								var ajaxUrl =
										"/ajax/user/order?assigned=true&sortedBy=serviceTime&searchScope=supplier&rsexp=appraise,staff&searchAllStatus=true";

								ajaxUrl += "&pageIndex=" + ($scope.pagingData.pageIndex - 1);
								ajaxUrl += "&pageSize=" + $scope.pagingData.pageSize;

								if ($scope.filterData.id != undefined && $scope.filterData.id != "") {
									ajaxUrl += "&staffNo=" + $scope.filterData.id;
								}

								if ($scope.filterData.name != undefined && $scope.filterData.name != "") {
									ajaxUrl += "&staffName=" + $scope.filterData.name;
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
					$scope.newStaff = function() {
						$window.location.href = "/servicer/staff/new";
					};

					$scope.searchStaffs();
				});