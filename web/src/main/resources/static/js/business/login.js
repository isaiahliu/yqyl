layoutApp
		.controller(
				'contentController',
				function($scope, $http, $window, $cookieStore, errorHandler) {
					$scope.mode = "login";
					$scope.rememberUsername = false;
					$scope.verifyCodeSent = false;
					$scope.resetPasswordVerifyCodeSent = false;
					$scope.showAnnouncement = false;
					$scope.agreed = false;
					$scope.loginData = {
						username : $cookieStore.get("YQYL_USERNAME"),
						password : ""
					};
					$scope.login = function() {
						$http({
							method : "PUT",
							url : "/ajax/user/authenticate",
							data : {
								username : $scope.loginData.username,
								password : md5($scope.loginData.password)
							}
						}).success(
								function(response) {
									if ($scope.rememberUsername) {
										$cookieStore.put("YQYL_USERNAME",
												$scope.loginData.username);
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

					$scope.resetPasswordKeydown = function(e) {
						if (e.keyCode == 13) {
							$scope.resetPassword();
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
							data : {
								username : $scope.registerData.username,
								password : md5($scope.registerData.password),
								cellphone : $scope.registerData.cellphone,
								verifyCode : $scope.registerData.verifyCode,
								servicer : $scope.registerData.servicer
							}
						}).success(function(response) {
							$window.location.href = "/login";
						}).error(function(response) {
							errorHandler($scope, response);
						});
					};

					$scope.resetPassword = function() {
						if ($scope.resetPasswordData.password != $scope.repeatResetPasswordPassword) {
							return;
						}

						if ($scope.resetPasswordData.password == undefined
								|| $scope.resetPasswordData.password == null
								|| $scope.resetPasswordData.password.length < 6)

							$http(
									{
										method : "POST",
										url : "/ajax/user/resetpwd",
										data : {
											password : md5($scope.resetPasswordData.password),
											cellphone : $scope.resetPasswordData.cellphone,
											verifyCode : $scope.resetPasswordData.verifyCode
										}
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
							data : {
								cellphone : $scope.registerData.cellphone,
								verifyCode : $scope.registerData.verifyCode
							}
						}).success(function(response) {
							$scope.verifyCodeSent = true;
						}).error(function(response) {
							errorHandler($scope, response);
						});
					};

					$scope.resetPasswordVerify = function() {
						$http(
								{
									method : "POST",
									url : "/ajax/user/resetPasswordVerify",
									data : {
										cellphone : $scope.resetPasswordData.cellphone,
										verifyCode : $scope.resetPasswordData.verifyCode
									}
								}).success(function(response) {
							$scope.resetPasswordVerifyCodeSent = true;
						}).error(function(response) {
							errorHandler($scope, response);
						});
					};
				});