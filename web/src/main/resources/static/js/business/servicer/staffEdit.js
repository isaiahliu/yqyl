layoutApp.directive('customOnChange', function() {
	return {
		restrict : 'A',
		link : function(scope, element, attrs) {
			var onChangeHandler = scope.$eval(attrs.customOnChange);
			element.bind('change', onChangeHandler);
		}
	};
});

layoutApp.controller('contentController', function($scope, $http, $window, errorHandler, staffId) {
	$scope.dateOptions = {
		dateFormat : 'yy/mm/dd',
	};
	if (staffId > 0) {
		$http({
			method : "GET",
			url : "/ajax/service/supplier/staff?rsexp=serviceCategories&searchAllStatus=true&id=" + staffId
		}).success(function(response) {
			var temp = new Date(response.data[0].dob);
			$scope.staff = response.data[0];
			$scope.staff.dob = temp;
			if ($scope.staff.photo != undefined && $scope.staff.photo != null) {
				$scope.imageUrl = "/ajax/content/image/" + $scope.staff.photo;
			}
			$http({
				method : "GET",
				url : "/ajax/service/category?status=A&rsexp=serviceSubCategories"
			}).success(function(response) {
				for (var i = 0; i < response.data.length; i++) {
					for (var j = 0; j < response.data[i].serviceSubCategories.length; j++) {
						var currentCategory = response.data[i].serviceSubCategories[j];
						for (var k = 0; k < $scope.staff.serviceCategories.length; k++) {
							if (currentCategory.id == $scope.staff.serviceCategories[k].id) {
								currentCategory.checked = true;
								continue;
							}
						}
					}
				}

				$scope.categories = response.data;

			}).error(function(response) {
				errorHandler($scope, response);
			});
		}).error(function(response) {
			errorHandler($scope, response);
		});
	} else {
		$scope.staff = {
			id : null,
			name : "",
			dob : null,
			identityCard : "",
			staffRate : "",
			phoneNo : ""
		};

		$http({
			method : "GET",
			url : "/ajax/service/category?status=A&rsexp=serviceSubCategories"
		}).success(function(response) {
			$scope.categories = response.data;
		}).error(function(response) {
			errorHandler($scope, response);
		});
	}

	$scope.selectImage = function(event) {
		if (event.target.files.length > 0) {
			$scope.newImage = event.target.files[0];
		} else {
			$scope.newImage = {};
		}
	};

	$scope.uploadPhoto = function() {
		var fd = new FormData();
		fd.append("IMAGE", $scope.newImage);
		$http({
			method : "POST",
			url : "/ajax/service/supplier/staff/" + staffId + "/upload",
			transformRequest : angular.identity,
			headers : {
				'Content-Type' : undefined
			},
			data : fd
		}).success(function(response) {
			$scope.imageUrl = '/ajax/content/image/' + $scope.staff.photo + "?ticks=" + new Date().getTime();
		}).error(function(response) {
			errorHandler($scope, response);
		});
	};

	$scope.apply = function() {
		var selectedCategories = new Array();
		for (var i = 0; i < $scope.categories.length; i++) {
			for (var j = 0; j < $scope.categories[i].serviceSubCategories.length; j++) {
				var currentCategory = $scope.categories[i].serviceSubCategories[j];

				if (currentCategory.checked) {
					selectedCategories.push(currentCategory);
				}
			}
		}
		$scope.staff.serviceCategories = selectedCategories;
		if (staffId > 0) {
			$http({
				method : "PUT",
				url : "/ajax/service/supplier/staff",
				data : {
					data : [ $scope.staff ]
				}
			}).success(function(response) {
				$window.location.href = "/servicer/staff"
			}).error(function(response) {
				errorHandler($scope, response);
			});
		} else {
			$http({
				method : "POST",
				url : "/ajax/service/supplier/staff",
				data : {
					data : [ $scope.staff ]
				}
			}).success(function(response) {
				$window.location.href = "/servicer/staff"
			}).error(function(response) {
				errorHandler($scope, response);
			});
		}
	};
	$scope.back = function() {
		$window.location.href = "/servicer/staff";

	};
});