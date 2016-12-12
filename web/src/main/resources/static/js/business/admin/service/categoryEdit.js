layoutApp.controller('contentController', function($scope, $http, $window, errorHandler, categoryId) {
	var url = "/ajax/service/category?searchAllStatus=true"

	if (categoryId > 0) {
		url += "&parentId=" + categoryId;
	}
	$http({
		method : "GET",
		url : url
	}).success(function(response) {
		$scope.categories = response.data;
	}).error(function(response) {
		errorHandler($scope, response);
	});

	$scope.editing = false;
	$scope.edit = function(category) {
		category.editing = true;
		category.newValue = category.name;
	};

	$scope.ok = function(category) {
		category.name = category.newValue;
		category.editing = false;
	};

	$scope.cancel = function(category) {
		category.editing = false;
	};

	$scope.add = function() {
		$scope.categories.push({
			id : 0,
			name : "新服务",
			status : {
				code : "N"
			},
			parent : {
				id : categoryId
			}
		});
	};

	$scope.remove = function(category) {
		$scope.categories.pop(category);
	};

	$scope.active = function(category) {
		category.status.code = 'A';
	};

	$scope.disable = function(category) {
		category.status.code = 'D';
	};

	$scope.apply = function() {
		$http({
			method : "PUT",
			url : "/ajax/service/category",
			data : {
				data : $scope.categories
			}
		}).success(function(response) {
			$window.location.href = "/admin/service/category";
		}).error(function(response) {
			errorHandler($scope, response);
		});
	};
	$scope.back = function() {
		$window.location.href = "/admin/service/category";
	};
});