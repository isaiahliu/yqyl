layoutApp.controller('contentController', function($scope, $http, $window, errorHandler) {

	$http({
		method : "GET",
		url : "/ajax/service/category?status=A&rsexp=serviceSubCategories"
	}).success(function(response) {
		$scope.categories = response.data;
		for (var index = 0; index < $scope.categories.length; index++) {
			$scope.searchServices($scope.categories[index]);
		}
	}).error(function(response) {
		errorHandler($scope, response);
	});

	$scope.myInterval = 4000;
	var slides = $scope.slides = [];
	slides.push({
		image : '/static/images/banner/1.jpg',
		text : ''
	});
	slides.push({
		image : '/static/images/banner/2.jpg',
		text : ''
	});
	slides.push({
		image : '/static/images/banner/3.jpg',
		text : ''
	});

	$scope.searchServices = function(category) {
		var ajaxUrl = "/ajax/service?rsexp=serviceSupplierClient&status=A&searchScope=all&pageIndex=0&pageSize=8&parentCategoryId=" + category.id;

		$http({
			method : "GET",
			url : ajaxUrl
		}).success(function(response) {
			category.services = response.data;
		}).error(function(response) {
			errorHandler($scope, response);
		});
	};
});