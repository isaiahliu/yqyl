layoutApp.controller('contentController', function($scope, $http, $window,
		errorHandler) {
	$http({
		method : "GET",
		url :  "/ajax/common/qa"
	}).success(function(response) {
		$scope.qas = response.data;
	}).error(function(response) {
		errorHandler($scope, response);
	});
});