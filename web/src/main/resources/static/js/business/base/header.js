layoutApp.controller('headerController', function($scope, $http, $window,
		errorHandler) {
	$scope.showLocation = false;
	$scope.selectedCityName = "泰安市";
	$scope.logout = function() {
		$http({
			method : "PUT",
			url : "/ajax/user/logout",
			data : {
				username : $scope.username,
				password : $scope.password
			}
		}).success(function(response) {
			$window.location.href = "/home";
		}).error(function(response) {
			errorHandler($scope, response);
		});
	};

	$http({
		method : "GET",
		url : "/ajax/common/province",
	}).success(function(response) {
		$scope.provinces = response.data;
	}).error(function(response) {
		errorHandler($scope, response);
	});

	$scope.favorite = function() {
		window.external.addFavorite("http://www.yqyl.com", "益券养老");
	}
});