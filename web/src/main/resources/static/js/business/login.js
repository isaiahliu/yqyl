layoutApp.controller('contentController', function($scope, $http, $window, $cookieStore, errorHandler) {
	$scope.isReg = false;
	$scope.rememberUsername = false;
	$scope.verifyCodeSent = false;

	$scope.loginData = {
		username : $cookieStore.get("YQYL_USERNAME"),
		password : ""
	};
	$scope.login = function() {
		$http({
			method : "PUT",
			url : "/ajax/user/authenticate",
			data : $scope.loginData
		}).success(function(response) {
			if ($scope.rememberUsername) {
				$cookieStore.put("YQYL_USERNAME", $scope.loginData.username);
			} else {
				$cookieStore.put("YQYL_USERNAME", "");
			}

			if (response.data[0].servicer) {
				$window.location.href = "/servicer";
			} else {
				$window.location.href = "/home";
			}
		}).error(function(response) {
			if (response.errors != undefined) {
				$scope.message = response.errors[0].message;
			} else {
				$scope.message = "请求失败";
			}
		});
	};

	$scope.loginKeydown = function(e) {
		if (e.keyCode == 13) {
			$scope.login();
		}
	}

	$scope.regKeydown = function(e) {
		if (e.keyCode == 13) {
			$scope.register();
		}
	}

	$scope.registerData = {
		username : "",
		password : "",
		servicer : false
	};

	$scope.repeatPassword = "";

	$scope.register = function() {
		if ($scope.registerData.password != $scope.repeatPassword) {
			return;
		}

		$http({
			method : "POST",
			url : "/ajax/user/register",
			data : $scope.registerData
		}).success(function(response) {
			$window.location.href = "/login";
		}).error(function(response) {
			errorHandler($scope, response);
		});
	};

	$scope.registerVerify = function() {
		$http({
			method : "POST",
			url : "/ajax/user/registerVerify",
			data : $scope.registerData
		}).success(function(response) {
			$scope.verifyCodeSent = true;
		}).error(function(response) {
			errorHandler($scope, response);
		});
	};
});