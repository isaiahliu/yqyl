layoutApp.controller('contentController', function($scope, $http, $window, errorHandler) {
	$scope.qas = [ {
		question : "服务商如何注册?",
		answer : "凭会计师事务所出具的..."
	},{
		question : "另一个问题?",
		answer : "答案..."
	},{
		question : "还有一个问题?",
		answer : "答案是..."
	} ];
});