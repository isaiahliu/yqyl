layoutApp.controller('headerController', function($scope, $http, $window,
		$cookieStore, errorHandler) {
	$scope.showLocation = false;

	$scope.selectedCityName = $cookieStore.get("YQYL_CITY");

	if ($scope.selectedCityName == undefined) {
		$scope.selectedCityName = "泰安市";

		$cookieStore.put("YQYL_CITY", "泰安市");
	}

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
	};

	$scope.overrideCurrent = function(position) {
		$cookieStore.put("YQYL_CITY", position);
		$scope.selectedCityName = position;
		$scope.showLocation = false;
	};

	$scope.locateCurrent = function() {
		$scope.overrideCurrent("泰安市");
	};
});