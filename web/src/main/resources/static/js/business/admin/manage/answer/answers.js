layoutApp.controller('contentController', function($scope, $http, $window,
		errorHandler) {
	$scope.filterData = {
		questionContent : ""
	};

	$scope.searchQas = function() {
		var ajaxUrl = "/ajax/common/qa?searchAllStatus=true";

		if ($scope.filterData.questionContent != undefined
				&& $scope.filterData.questionContent != "") {
			ajaxUrl += "?question=" + $scope.filterData.questionContent;
		}

		$http({
			method : "GET",
			url : ajaxUrl
		}).success(function(response) {
			$scope.qas = response.data;
		}).error(function(response) {
			errorHandler($scope, response);
		});
	};

	$scope.showQa = function(qa, visible) {
		var status = visible ? 'A' : 'D';
		$http({
			method : "PUT",
			url : "/ajax/common/qa",
			data : {
				data : [ {
					id : qa.id,
					status : {
						code : status
					}
				} ]
			}
		}).success(function(response) {
			qa.status = {
				code : status,
				message : visible ? '可用' : '禁用'
			};
		}).error(function(response) {
			errorHandler($scope, response);
		});
	};

	$scope.searchQas();
});