layoutApp.controller('navigatorController', function($scope, $http, $window, errorHandler) {
	$scope.showHomeEndowment = false;
	$scope.showPensionIndustry = false;
	$scope.showTalentTraining = false;

	$scope.publish = function() {
		$window.location.href = "/service/publish"
	}
});