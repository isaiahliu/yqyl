layoutApp.controller('headerController', function($scope, $http, $window, errorHandler) {
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

	$scope.favorite = function() {
		window.external.addFavorite("http://www.yqyl.com", "益券养老");
	}
});