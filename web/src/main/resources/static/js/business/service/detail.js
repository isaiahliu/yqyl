layoutApp
		.controller(
				'contentController',
				function($scope, $http, $window, errorHandler, serviceOrderId,
						mode) {
					$scope.dateOptions = {
						dateFormat : 'yy/mm/dd',
						changeMonth : true,
						changeYear : true,
						yearRange : "c-30:c+30",
						showAnim : "fadeIn"
					};

					$scope.hours = [ 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12,
							13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23 ];

					$scope.mode = mode;

					$scope.modifying = false;

					var url = "/ajax/user/order?uid="
							+ serviceOrderId
							+ "&rsexp=serviceInfo[serviceSupplierClient,serviceCategory],operations,appraise";

					if (mode == 'receiver') {
						url += "&searchScope=me";
					} else if (mode == 'supplier') {
						url += "&searchScope=supplier";
					} else {
						url += "&searchScope=all";
					}
					$http({
						method : "GET",
						url : url
					}).success(function(response) {
						$scope.serviceOrder = response.data[0];
					}).error(function(response) {
						errorHandler($scope, response);
					});

					$scope.orderMore = function() {
						$window.location.href = "/service";
					};

					$scope.back = function() {
						if (mode == 'receiver') {
							$window.location.href = "/user";
						} else {
							$window.location.href = "/servicer/order";
						}
					};

					$scope.edit = function() {
						$window.location.href = "/user/order/edit/"
								+ serviceOrderId;
					};

					$scope.pay = function() {
						$http({
							method : "POST",
							url : "/ajax/user/order/pay",
							data : {
								data : [ $scope.serviceOrder ]
							}
						}).success(function(response) {
							$window.location.href = "/user";
						}).error(function(response) {
							errorHandler($scope, response);
						});
					};

					$scope.reply = function() {
						$http(
								{
									method : "POST",
									url : "/ajax/user/order/appraise/reply",
									data : {
										data : [ {
											uid : serviceOrderId,
											reply : $scope.serviceOrder.appraise.newReply
										} ]
									}
								})
								.success(
										function(response) {
											$scope.replying = false;
											$scope.serviceOrder.appraise.reply = $scope.serviceOrder.appraise.newReply
										}).error(function(response) {
									errorHandler($scope, response);
								});
					};

					$scope.prepareModify = function() {
						$scope.serviceOrder.serviceDate = new Date(
								$scope.serviceOrder.serviceDate);

						$scope.serviceOrder.newAddress = $scope.serviceOrder.address;
						$scope.serviceOrder.newPhone = $scope.serviceOrder.phone;
						$scope.serviceOrder.newServiceDate = new Date(
								$scope.serviceOrder.serviceDate);
						$scope.serviceOrder.newServiceHour = $scope.serviceOrder.serviceHour;
						$scope.modifying = true;
					};
					$scope.applyModify = function() {
						var data = {
							uid : $scope.serviceOrder.uid
						};

						var modifyed = false;
						if ($scope.serviceOrder.newAddress != $scope.serviceOrder.address) {
							data.address = $scope.serviceOrder.newAddress;
							modifyed = true;
						}

						if ($scope.serviceOrder.newPhone != $scope.serviceOrder.phone) {
							data.phone = $scope.serviceOrder.newPhone;
							modifyed = true;
						}

						if ($scope.serviceOrder.newServiceDate.getTime() != $scope.serviceOrder.serviceDate
								.getTime()) {
							data.serviceDate = $scope.serviceOrder.newServiceDate;
							modifyed = true;
						}

						if ($scope.serviceOrder.newServiceHour != $scope.serviceOrder.serviceHour) {
							data.serviceHour = $scope.serviceOrder.newServiceHour;
							modifyed = true;
						}

						if (modifyed) {
							$http({
								method : "PUT",
								url : "/ajax/user/order/proposal",
								data : {
									data : [ data ]
								}
							}).success(function(response) {
								$window.location.reload();
							}).error(function(response) {
								errorHandler($scope, response);
							});
						} else {
							$scope.cancelModify();
						}
					};
					$scope.cancelModify = function() {
						$scope.modifying = false;
					};

					$scope.print = function() {
						window.print();
					};
				});