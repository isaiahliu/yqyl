layoutApp.controller('contentController', function($scope, $http, $window, errorHandler, userId) {
	$http({
		method : "GET",
		url : "/ajax/common/accessright"
	}).success(function(response) {
		var allAccessrights = response.data;
		$scope.initCheckState(allAccessrights);

		$http({
			method : "GET",
			url : "/ajax/user?rsexp=accessrights&id=" + userId
		}).success(function(response) {
			$scope.user = response.data[0];
			$scope.populateCheckState(allAccessrights, $scope.user.accessrights);
			$scope.accessrights = allAccessrights;
		}).error(function(response) {
			errorHandler($scope, response);
		});
	}).error(function(response) {
		errorHandler($scope, response);
	});

	$scope.initCheckState = function(accessrights) {
		for (var i = 0; i < accessrights.length; i++) {
			accessrights[i].checked = false;
			accessrights[i].hide = false;
			$scope.initCheckState(accessrights[i].children);
		}
	}

	$scope.populateCheckState = function(accessrights, ownedAccessrights) {
		for (var i = 0; i < accessrights.length; i++) {
			for (var j = 0; j < ownedAccessrights.length; j++) {
				if (accessrights[i].code == ownedAccessrights[j].code) {
					accessrights[i].checked = true;
					$scope.change(accessrights[i], false);
					break;
				}
			}

			if (!accessrights[i].checked) {
				$scope.populateCheckState(accessrights[i].children, ownedAccessrights);
			}
		}
	}

	$scope.back = function() {
		$window.location.href = "/admin/permission";
	};

	$scope.apply = function() {
		var result = new Array();
		$scope.collectSelectedAccessright($scope.accessrights, result);

		$http({
			method : "PUT",
			url : "/ajax/user/userinfo",
			data : {
				data : [ {
					id : $scope.user.id,
					accessrights : result
				} ]
			}
		}).success(function(response) {
			$scope.back();
		}).error(function(response) {
			errorHandler($scope, response);
		});
	};

	$scope.setVisible = function(accessright, visible) {
		accessright.hide = !visible;
		$scope.change(accessright, !visible);
	};

	$scope.change = function(accessright, forceHide) {
		for (var i = 0; i < accessright.children.length; i++) {
			$scope.setVisible(accessright.children[i], !forceHide && !accessright.checked);
		}
	};

	$scope.collectSelectedAccessright = function(accessrights, result) {
		for (var i = 0; i < accessrights.length; i++) {
			if (accessrights[i].checked) {
				result.push(accessrights[i]);
			} else {
				$scope.collectSelectedAccessright(accessrights[i].children, result);
			}
		}
	}
});