layoutApp.directive('customOnChange', function() {
	return {
		restrict : 'A',
		link : function(scope, element, attrs) {
			var onChangeHandler = scope.$eval(attrs.customOnChange);
			element.bind('change', onChangeHandler);
		}
	};
});

layoutApp.controller('contentController', function($scope, $http, $window, errorHandler, categoryId) {
	var url = "/ajax/service/category?searchAllStatus=true"

	if (categoryId > 0) {
		url += "&rsexp=serviceSubCategories&id=" + categoryId;
	}
	$http({
		method : "GET",
		url : url
	}).success(function(response) {
		if (categoryId > 0) {
			$scope.parentCategory = response.data[0];
			$scope.imageUrl = "/ajax/content/image/" + $scope.parentCategory.image;
			$scope.categories = $scope.parentCategory.serviceSubCategories;

		} else {
			$scope.categories = response.data;
		}
	}).error(function(response) {
		errorHandler($scope, response);
	});

	$scope.editing = false;
	$scope.edit = function(category) {
		category.editing = true;
		category.newValue = category.name;
	};

	$scope.ok = function(category) {
		category.name = category.newValue;
		category.editing = false;
	};

	$scope.cancel = function(category) {
		category.editing = false;
	};

	$scope.add = function() {
		$scope.categories.push({
			id : 0,
			name : "新服务",
			status : {
				code : "N"
			},
			parent : {
				id : categoryId
			}
		});
	};

	$scope.remove = function(category) {
		$scope.categories.pop(category);
	};

	$scope.active = function(category) {
		category.status.code = 'A';
	};

	$scope.disable = function(category) {
		category.status.code = 'D';
	};

	$scope.apply = function() {
		$http({
			method : "PUT",
			url : "/ajax/service/category",
			data : {
				data : $scope.categories
			}
		}).success(function(response) {
			$window.location.href = "/admin/service/category";
		}).error(function(response) {
			errorHandler($scope, response);
		});
	};
	$scope.back = function() {
		$window.location.href = "/admin/service/category";
	};

	$scope.selectImage = function(event) {
		if (event.target.files.length > 0) {
			$scope.newImage = event.target.files[0];
			$scope.uploadPhoto();
		} else {
			$scope.newImage = {};
		}
	};

	$scope.uploadPhoto = function() {
		var fd = new FormData();
		fd.append("IMAGE", $scope.newImage);
		$http({
			method : "POST",
			url : "/ajax/service/category/" + $scope.parentCategory.id + "/upload",
			transformRequest : angular.identity,
			headers : {
				'Content-Type' : undefined
			},
			data : fd
		}).success(function(response) {
			$scope.imageUrl = '/ajax/content/image/' + $scope.parentCategory.image + "?ticks=" + new Date().getTime();
		}).error(function(response) {
			errorHandler($scope, response);
		});
	};
});