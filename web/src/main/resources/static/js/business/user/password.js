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
	$scope.finished = false;
	$scope.apply = function() {
		if ($scope.newPassword == "" || $scope.newPassword != $scope.newPasswordAgain) {

		} else {
			$http({
				method : "PUT",
				url : "/ajax/user/password",
				data : {
					id : $scope.id,
					oldPassword : md5($scope.oldPassword),
					newPassword : md5($scope.newPassword)
				}
			}).success(function(response) {
				$scope.finished = true;
			}).error(function(response) {
				if (response.errors != undefined) {
					$scope.message = response.errors[0].message;
				} else {
					$scope.message = "请求失败";
				}
			});
		}
	};
});