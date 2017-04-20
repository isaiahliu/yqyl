layoutApp.controller('contentController', function($scope, $http, $window,
		errorHandler, entityId) {
	$scope.config = {};

	if (entityId != 0) {
		var ajaxUrl = "/ajax/common/qa?searchAllStatus=true&id=" + entityId;

		$http({
			method : "GET",
			url : ajaxUrl
		}).success(function(response) {
			$scope.qa = response.data[0];
		}).error(function(response) {
			errorHandler($scope, response);
		});
	} else {
		$scope.qa = {
			id : null,
			question : "",
			answer : "",
			sequence : 0,
			status : {
				code : 'A'
			}
		}
	}

	$scope.apply = function() {
		if (entityId == 0) {
			$http({
				method : "POST",
				url : "/ajax/common/qa",
				data : {
					data : [ $scope.qa ]
				}
			}).success(function(response) {
				$window.location.href = "/admin/manage/answers";
			}).error(function(response) {
				errorHandler($scope, response);
			});
		} else {
			$http({
				method : "PUT",
				url : "/ajax/common/qa",
				data : {
					data : [ $scope.qa ]
				}
			}).success(function(response) {
				$window.location.href = "/admin/manage/answers";
			}).error(function(response) {
				errorHandler($scope, response);
			});
		}
	};
});