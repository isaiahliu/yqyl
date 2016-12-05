layoutApp.controller('contentController', function($scope, $http, $window, errorHandler) {
	$http({
		method : "GET",
		url : "/ajax/common/lookup/FARELTN"
	}).success(function(response) {
		$scope.relationships = response.data;
	}).error(function(response) {
		errorHandler($scope, response);
	});

	$scope.client = {
		familyRelationship : {}
	};

	$scope.apply = function() {
		$http({
			method : "POST",
			url : "/ajax/user/receiver",
			data : {
				data : [ $scope.client ]
			}
		}).success(function(response) {
			$window.location.href = "/user/userinfo/" + response.data[0].id;
		}).error(function(response) {
			errorHandler($scope, response);
		});
	}
});