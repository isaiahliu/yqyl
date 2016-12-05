layoutApp.controller('contentController', function($scope, $http, $window, errorHandler) {
	$http({
		method : "GET",
		url : "/ajax/service/supplier/auditing?searchScope=me&searchAllStatus=true"
	}).success(function(response) {
		$scope.auditings = response.data;
	}).error(function(response) {
		errorHandler($scope, response);
	});
});