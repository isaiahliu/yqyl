layoutApp.controller('contentController', function($scope, $http, $window,
		errorHandler) {
	$scope.dateOptions = {
		dateFormat : 'yy/mm/dd',
		changeMonth : true,
		changeYear : true,
		yearRange : "c-30:c+30",
		showAnim : "fadeIn"
	};

	$scope.hours = [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16,
			17, 18, 19, 20, 21, 22, 23 ];

	$scope.apply = function() {
		$http({
			method : "POST",
			url : "/ajax/user/order/requirement",
			data : {
				data : [ {
					address : $scope.address,
					comment : $scope.comment,
					phone : $scope.phone,
					price : $scope.price,
					serviceDate : $scope.serviceDate,
					serviceHour : $scope.serviceHour
				} ]
			}
		}).success(function(response) {
			$window.location.href = "/service";
		}).error(function(response) {
			errorHandler($scope, response);
		});
	};
});