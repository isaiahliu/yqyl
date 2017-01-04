layoutApp.controller('contentController', function($scope, $http, $window, errorHandler) {
	$scope.qas = [ {
		question : "服务商入网流程",
		image : "/static/images/pic/supplierJoin.png"
	}, {
		question : "用户入网流程",
		answer : "凭会计师事务所出具的...",
		image : "/static/images/pic/receiverJoin.png"
	} ];
});