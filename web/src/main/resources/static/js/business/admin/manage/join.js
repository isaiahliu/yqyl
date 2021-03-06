layoutApp.controller('contentController', function($scope, $http, $window, errorHandler) {
	$scope.content = null;

	$scope.config = {}
	$http({
		method : "GET",
		url : "/ajax/common/systemattribute/JOINUS"
	}).success(function(response) {
		$scope.content = "";
		if (response.data.length > 0) {
			$scope.content = response.data[0];
		}
	}).error(function(response) {
		errorHandler($scope, response);
	});

	$scope.apply = function() {
		$http({
			method : "PUT",
			url : "/ajax/common/systemattribute",
			data : {
				data : [ {
					key : "JOINUS",
					value : $scope.content
				} ]
			}
		}).success(function(response) {
			$window.location.reload();
		}).error(function(response) {
			errorHandler($scope, response);
		});
	}
});