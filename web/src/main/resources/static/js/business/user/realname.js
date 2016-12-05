layoutApp.directive('customOnChange', function() {
	return {
		restrict : 'A',
		link : function(scope, element, attrs) {
			var onChangeHandler = scope.$eval(attrs.customOnChange);
			element.bind('change', onChangeHandler);
		}
	};
});

layoutApp.controller('contentController', function($scope, $http, $window, clientId, errorHandler) {
	$http({
		method : "GET",
		url : "/ajax/common/lookup/CDTYPE"
	}).success(function(response) {
		$scope.credentialTypes = response.data;
	}).error(function(response) {
		errorHandler($scope, response);
	});

	$http({
		method : "GET",
		url : "/ajax/user/receiver?id=" + clientId
	}).success(function(response) {
		if (response.data.length > 0) {
			$scope.client = response.data[0];
			$scope.verified = $scope.client.status.code == 'R';
			$scope.credentialCopyUrl = '/ajax/content/image/' + $scope.client.identityCardCopy;
		}
	}).error(function(response) {
		errorHandler($scope, response);
	});

	$scope.apply = function() {
		$http({
			method : "POST",
			url : "/ajax/user/receiver/realname",
			data : {
				data : [ $scope.client ]
			}
		}).success(function(response) {
			$window.location.href = "/user/userinfo";
		}).error(function(response) {
			errorHandler($scope, response);
		});
	};

	$scope.selectCredentialCopy = function(event) {
		if (event.target.files.length > 0) {
			$scope.newIdentityCardCopy = event.target.files[0];
		} else {
			$scope.newIdentityCardCopy = {};
		}
	};

	$scope.upload =
			function() {
				var fd = new FormData();
				fd.append("IDENTITY_CARD_COPY", $scope.newIdentityCardCopy);
				$http({
					method : "POST",
					url : "/ajax/user/receiver/" + clientId + "/upload",
					transformRequest : angular.identity,
					headers : {
						'Content-Type' : undefined
					},
					data : fd
				}).success(
						function(response) {
							$scope.credentialCopyUrl =
									'/ajax/content/image/' + $scope.client.identityCardCopy + "?ticks="
											+ new Date().getTime();
						}).error(function(response) {
					errorHandler($scope, response);
				});
			};
});