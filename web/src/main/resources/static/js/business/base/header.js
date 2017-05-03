layoutApp.controller('headerController', function($scope, $http, $window,
		$cookieStore, errorHandler) {
	$scope.showLocation = false;

	$scope.selectedCityName = $cookieStore.get("YQYL_CITY_MESSAGE");

	if ($scope.selectedCityName == undefined) {
		$scope.selectedCityName = "泰安市";

		$cookieStore.put("YQYL_CITY_CODE", "3709");
		$cookieStore.put("YQYL_CITY_MESSAGE", "泰安市");
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
		$cookieStore.put("YQYL_CITY_CODE", position.code);
		$cookieStore.put("YQYL_CITY_MESSAGE", position.message);
		$scope.selectedCityName = position.message;
		$scope.showLocation = false;
	};

	$scope.locateCurrent = function() {
		$scope.overrideCurrent({
			code : "3709",
			message : "泰安市"
		});
	};
});