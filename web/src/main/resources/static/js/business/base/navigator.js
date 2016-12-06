layoutApp.controller('navigatorController', function($scope, $http, $window, errorHandler) {
	$scope.showHomeEndowment = false;
	$scope.showPensionIndustry = false;
	$scope.showTalentTraining = false;
	$scope.name = "";

	$scope.publish = function() {
		$window.location.href = "/service/publish";
	}

	$scope.search = function() {
		var url = "/service/search#?";
		if ($scope.name != "") {
			url += "name=" + $scope.name;
		}

		$scope.name = "";
		
		$window.location.href = url;
	}
});