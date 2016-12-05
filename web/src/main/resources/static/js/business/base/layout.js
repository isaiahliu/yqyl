var layoutApp = angular.module('layoutApp', [ 'ui.bootstrap', 'ui.date', 'textAngular', 'ngCookies' ]);

layoutApp.value("errorHandler", function(scope, response) {
	if (response.errors != undefined) {
		scope.errorMessage = response.errors[0].message;
	} else {
		scope.errorMessage = "请求失败";
	}
});

layoutApp.filter('unique', function() {
	return function(collection, keyname) {
		var output = [], keys = [];
		angular.forEach(collection, function(item) {
			var key = item[keyname];
			if (keys.indexOf(key) === -1) {
				keys.push(key);
				output.push(item);
			}
		});
		return output;
	};
});
