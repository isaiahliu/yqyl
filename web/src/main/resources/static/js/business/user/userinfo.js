layoutApp.controller('contentController', function($scope, $http, $window, errorHandler) {
	$http({
		method : "GET",
		url : "/ajax/user/receiver"
	}).success(function(response) {
		$scope.members = response.data;
	}).error(function(response) {
		errorHandler($scope, response);
	});

	$scope.addFamilyMember = function() {
		$window.location.href = "/user/userinfo/new";
	};

	$scope.disable = function(member) {
		if (confirm("确定要注销\"" + member.name + "\"这个账号吗?")) {
			$http({
				method : "DELETE",
				url : "/ajax/user/receiver/disable/" + member.id
			}).success(function(response) {
				$window.location.reload();
			}).error(function(response) {
				errorHandler($scope, response);
			});
		}
	};
});