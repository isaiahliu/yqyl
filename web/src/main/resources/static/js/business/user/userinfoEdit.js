layoutApp.controller('contentController', function($scope, $http, $window, clientId, errorHandler) {
	$scope.dateOptions = {
		dateFormat : 'yy/mm/dd',
	};
	$scope.editingClient = false;
	$scope.editingHealth = false;
	$scope.editingOther = false;
	$scope.editingInterest = false;

	$http({
		method : "GET",
		url : "/ajax/common/lookup/GENDER"
	}).success(function(response) {
		$scope.genders = response.data;
	}).error(function(response) {
		errorHandler($scope, response);
	});

	$http({
		method : "GET",
		url : "/ajax/user/receiver?rsexp=healthInformation,interest,other&id=" + clientId
	}).success(function(response) {
		$scope.client = response.data[0];
		if ($scope.client.dob) {
			$scope.client.dob = new Date($scope.client.dob);
		}
	}).error(function(response) {
		errorHandler($scope, response);
	});

	$scope.saveClient = function() {
		$http({
			method : "PUT",
			url : "/ajax/user/receiver",
			data : {
				data : [ $scope.client ]
			}
		}).success(function(response) {
			if ($scope.client.gender != null) {
				for (var index = 0; index < $scope.genders.length; index++) {
					if ($scope.client.gender.code == $scope.genders[index].code) {
						$scope.client.gender.message = $scope.genders[index].message;
						break;
					}
				}
			}

			$scope.editingClient = false;
		}).error(function(response) {
			errorHandler($scope, response);
		});
	};

	$scope.saveHealthInfo = function() {
		$http({
			method : "PUT",
			url : "/ajax/user/receiver/health",
			data : {
				data : [ $scope.client.healthInformation ]
			}
		}).success(function(response) {
			$scope.editingHealthInfo = false;
		}).error(function(response) {
			errorHandler($scope, response);
		});
	};

	$scope.saveInterest = function() {
		$http({
			method : "PUT",
			url : "/ajax/user/receiver/interest",
			data : {
				data : [ $scope.client.interest ]
			}
		}).success(function(response) {
			$scope.editingInterest = false;
		}).error(function(response) {
			errorHandler($scope, response);
		});
	};

	$scope.saveOther = function() {
		$http({
			method : "PUT",
			url : "/ajax/user/receiver/other",
			data : {
				data : [ $scope.client.other ]
			}
		}).success(function(response) {
			$scope.editingOther = false;
		}).error(function(response) {
			errorHandler($scope, response);
		});
	};
});