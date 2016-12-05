layoutApp.controller('contentController', function($scope, $http, $window, errorHandler) {
	$http({
		method : "GET",
		url : "/ajax/user/receiver?rsexp=yiquan"
	}).success(function(response) {
		$scope.members = response.data;
	}).error(function(response) {
		errorHandler($scope, response);
	});

	$scope.apply = function() {
		$http({
			method : "PUT",
			url : "/ajax/user/yiquan/bind",
			data : {
				data : [ $scope.newYiquan ]
			}
		}).success(function(response) {
			$window.location.reload();
		}).error(function(response) {
			errorHandler($scope, response);
		});
	};
});