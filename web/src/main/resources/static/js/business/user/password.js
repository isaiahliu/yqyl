layoutApp.controller('contentController', function($scope, $http, $window, errorHandler) {
	$http({
		method : "GET",
		url : "/ajax/user/userinfo"
	}).success(function(response) {
		$scope.id = response.data[0].id;
	}).error(function(response) {
		errorHandler($scope, response);
	});

	$scope.oldPassword = "";
	$scope.newPassword = "";
	$scope.newPasswordAgain = "";

	$scope.apply = function() {
		if ($scope.newPassword == "" || $scope.newPassword != $scope.newPasswordAgain) {

		} else {
			$http({
				method : "PUT",
				url : "/ajax/user/password",
				data : {
					id : $scope.id,
					oldPassword : $scope.oldPassword,
					newPassword : $scope.newPassword
				}
			}).success(function(response) {
				$window.location.reload();
			}).error(function(response) {
				errorHandler($scope, response);
			});
		}
	};
});