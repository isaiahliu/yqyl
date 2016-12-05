layoutApp.controller('contentController', function($scope, $http, $window, errorHandler) {
	$scope.name = "";

	$scope.search = function() {
		var url = "/ajax/service/category?rsexp=serviceSubCategories&searchAllStatus=true";

		if ($scope.name != "") {
			url += "&name=" + $scope.name;
		}

		$http({
			method : "GET",
			url : url
		}).success(function(response) {
			$scope.categories = response.data;
		}).error(function(response) {
			errorHandler($scope, response);
		});
	}

	$scope.search();

	$scope.edit = function() {
		$window.location.href = "/admin/service/category/edit"
	}
});