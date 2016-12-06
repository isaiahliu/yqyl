layoutApp.controller('contentController', function($scope, $http, $window, $location, $rootScope, errorHandler) {
	$scope.sorting = {
		name : "default",
		asc : true
	}

	$scope.searchServices = function(newSearch) {
		var paging = $scope.pagingData;
		if (newSearch) {
			paging = {
				pageIndex : 1,
				pageSize : 15
			}
		}

		var ajaxUrl = "/ajax/service?rsexp=serviceSupplierClient,serviceCategory,stastic&searchScope=all";

		ajaxUrl += "&pageIndex=" + (paging.pageIndex - 1);
		ajaxUrl += "&pageSize=" + paging.pageSize;

		if ($scope.categoryId != undefined) {
			ajaxUrl += "&categoryId=" + $scope.categoryId;
		} else if ($scope.parentCategoryId != undefined) {
			ajaxUrl += "&parentCategoryId=" + $scope.parentCategoryId;
		}

		if ($scope.name != undefined && $scope.name != "") {
			ajaxUrl += "&name=" + $scope.name;
		}

		switch ($scope.sorting.name) {
		case 'sales':
			ajaxUrl += "&customSortedBy=sales&customSortedDirection=" + ($scope.sorting.asc ? 'asc' : 'desc');
			break;
		case 'price':
			ajaxUrl += "&sortedBy=price_" + ($scope.sorting.asc ? 'asc' : 'desc')
			break;
		case 'appraise':
			ajaxUrl += "&customSortedBy=appraise&customSortedDirection=" + ($scope.sorting.asc ? 'asc' : 'desc')
			break;
		case 'timestamp':
			ajaxUrl += "&sortedBy=lastModifiedDate_" + ($scope.sorting.asc ? 'asc' : 'desc')
			break;
		default:
			break;
		}
		$http({
			method : "GET",
			url : ajaxUrl
		}).success(function(response) {
			$scope.results = response.data;
			response.meta.paging.pageIndex++;
			$scope.pagingData = response.meta.paging;
		}).error(function(response) {
			errorHandler($scope, response);
		});
	};

	var reloadData = function() {
		if ($scope.categories == undefined) {
			return;
		}
		$scope.parentCategory = undefined;
		$scope.category = undefined;

		$scope.parentCategoryId = $location.search().parentCategory;
		$scope.categoryId = $location.search().category;
		$scope.name = $location.search().name;

		if ($scope.parentCategoryId != undefined || $scope.categoryId != undefined) {
			for (var i = 0; i < $scope.categories.length; i++) {
				var category = $scope.categories[i];
				category.expanding = false;

				if ($scope.parentCategory == undefined) {
					if ($scope.parentCategoryId == undefined) {
						for (var j = 0; j < category.serviceSubCategories.length; j++) {
							if (category.serviceSubCategories[j].id == $scope.categoryId) {
								$scope.category = category.serviceSubCategories[j];
								$scope.parentCategory = category;
								category.expanding = true;
								break;
							}
						}
					} else if (category.id == $scope.parentCategoryId) {
						$scope.parentCategory = category;
						category.expanding = true;
					}
				}
			}
		}

		$scope.searchServices(true);
	};

	$rootScope.$on('$locationChangeSuccess', reloadData);

	$http({
		method : "GET",
		url : "/ajax/service/category?status=A&rsexp=serviceSubCategories"
	}).success(function(response) {
		$scope.categories = response.data;

		reloadData();
	}).error(function(response) {
		errorHandler($scope, response);
	});

	$scope.previousPage = function() {
		if ($scope.pagingData.pageIndex > 1) {
			$scope.pagingData.pageIndex--;
			$scope.searchServices(false);
		}
	};

	$scope.nextPage = function() {
		if ($scope.pagingData.pageIndex * $scope.pagingData.pageSize < $scope.pagingData.itemCount) {
			$scope.pagingData.pageIndex++;
			$scope.searchServices(false);
		}
	};

	$scope.modifySorting = function(name) {
		$scope.sorting.name = name;
		$scope.sorting.asc = !$scope.sorting.asc;

		$scope.searchServices(true);
	};
});