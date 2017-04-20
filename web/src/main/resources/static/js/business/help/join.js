layoutApp.controller('contentController', function($scope, $http, $window, errorHandler) {
	$http({
		method : "GET",
		url : "/ajax/user/help/join"
	}).success(function(response) {
		$scope.content = "";
		if (response.data.length > 0) {
			$scope.content = response.data[0];
		}
	}).error(function(response) {
		errorHandler($scope, response);
	});
});