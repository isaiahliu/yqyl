layoutApp.directive('receiptUpload', function() {
	return {
		restrict : 'A',
		link : function(scope, element, attrs) {
			var uploadReceipt = scope.$eval(attrs.receiptUpload);
			element.bind('change', function(event) {
				if (event.target.files.length > 0) {
					uploadReceipt(scope.order, event.target.files[0]);
				}
			});
		}
	};
});

layoutApp.controller('contentController', function($scope, $http, $window, errorHandler) {
	$http({
		method : "GET",
		url : "/ajax/common/lookup/ODSTAT"
	}).success(function(response) {
		$scope.statuses = response.data;
	}).error(function(response) {
		errorHandler($scope, response);
	});

	$http({
		method : "GET",
		url : "/ajax/common/lookup/PMMTHD"
	}).success(function(response) {
		$scope.paymentMethods = response.data;
	}).error(function(response) {
		errorHandler($scope, response);
	});

	$scope.pagingData = {
		pageIndex : 1,
		pageSize : 10
	};
	$scope.filterData = {
		category : "",
		status : ""
	};

	$http({
		method : "GET",
		url : "/ajax/service"
	}).success(function(response) {
		$scope.services = response.data;
	}).error(function(response) {
		errorHandler($scope, response);
	});

	$scope.searchOrders = function() {
		var ajaxUrl = "/ajax/user/order?searchScope=supplier&rsexp=serviceInfo[serviceCategory],paymentTransaction,user,staff";

		ajaxUrl += "&pageIndex=" + ($scope.pagingData.pageIndex - 1);
		ajaxUrl += "&pageSize=" + $scope.pagingData.pageSize;
		ajaxUrl += "&sortedBy=serviceTime";

		if ($scope.filterData.category != undefined && $scope.filterData.category != "") {
			ajaxUrl += "&category=" + $scope.filterData.category;
		}

		if ($scope.filterData.status != undefined && $scope.filterData.status != "") {
			ajaxUrl += "&status=" + $scope.filterData.status;
		}

		$http({
			method : "GET",
			url : ajaxUrl
		}).success(function(response) {
			$scope.orders = response.data;
			response.meta.paging.pageIndex++;
			$scope.pagingData = response.meta.paging;
		}).error(function(response) {
			errorHandler($scope, response);
		});
	};

	$scope.uploadReceipt = function(order, file) {
		var fd = new FormData();
		fd.append("IMAGE", file);
		$http({
			method : "POST",
			url : "/ajax/user/order/" + order.uid + "/receipt",
			transformRequest : angular.identity,
			headers : {
				'Content-Type' : undefined
			},
			data : fd
		}).success(function(response) {
			order.receipt = response.data[0];
		}).error(function(response) {
			errorHandler($scope, response);
		});
	};

	$scope.assignPrepare = function(order) {
		$http({
			method : "GET",
			url : "/ajax/service/supplier/staff/available?serviceCategoryId=" + order.serviceInfo.serviceCategory.id
		}).success(function(response) {
			order.availableStaffs = response.data;
			$scope.txCodeInputCancel(order);
			order.assigning = true;
		}).error(function(response) {
			errorHandler($scope, response);
		});
	};

	$scope.assignCancel = function(order) {
		order.assigning = false;
	};

	$scope.txCodeInputCancel = function(order) {
		order.txCodeInputing = false;
	};
	$scope.txCodeInput = function(order) {
		$scope.assignCancel(order);

		order.txCodeInputing = true;
	};

	$scope.cancel = function(order) {
		$http({
			method : "POST",
			url : "/ajax/user/order/cancel",
			data : {
				data : [ {
					uid : order.uid
				} ]
			}
		}).success(function(response) {
			order.status = response.data[0].status;
		}).error(function(response) {
			errorHandler($scope, response);
		});
	};

	$scope.rejectCancel = function(order) {
		$http({
			method : "POST",
			url : "/ajax/user/order/rejectCancel",
			data : {
				data : [ {
					uid : order.uid
				} ]
			}
		}).success(function(response) {
			order.status = response.data[0].status;
		}).error(function(response) {
			errorHandler($scope, response);
		});
	};

	$scope.sendTxCode = function(order) {
		$http({
			method : "POST",
			url : "/ajax/user/order/transaction",
			data : {
				data : [ {
					uid : order.uid,
					paymentTransaction : {
						code : order.inputTxCode
					}
				} ]
			}
		}).success(function(response) {
			order.status = response.data[0].status;
			order.price = response.data[0].price;
			order.expectedPaymentAmount = response.data[0].expectedPaymentAmount;
			order.actualPaymentAmount = response.data[0].actualPaymentAmount;
			$scope.txCodeInputCancel(order);
		}).error(function(response) {
			errorHandler($scope, response);
		});
	};

	$scope.assign = function(order) {
		if (order.availableStaffs.length == 0) {
			order.assigning = false;
			return;
		}

		var staff = {};

		if (order.selectedStaff == undefined || order.selectedStaff == "") {
			staff = order.availableStaffs[Math.floor(Math.random() * order.availableStaffs.length)];
		} else {
			for (var i = 0; i < order.availableStaffs.length; i++) {
				if (order.availableStaffs[i].id == order.selectedStaff) {
					staff = order.availableStaffs[i];
					break;
				}
			}
		}

		$http({
			method : "POST",
			url : "/ajax/user/order/assign",
			data : {
				data : [ {
					uid : order.uid,
					staff : staff
				} ]
			}
		}).success(function(response) {
			order.assigning = false;
			order.staff = {};
			order.status.code = 'I';
			for (var i = 0; i < $scope.statuses.length; i++) {
				if ($scope.statuses[i].code == 'I') {
					order.status.message = $scope.statuses[i].message;
					break;
				}
			}
		}).error(function(response) {
			errorHandler($scope, response);
		});
	};

	$scope.release = function(order) {
		$http({
			method : "POST",
			url : "/ajax/user/order/release",
			data : {
				data : [ {
					uid : order.uid
				} ]
			}
		}).success(function(response) {
			order.status.code = 'F';
			for (var i = 0; i < $scope.statuses.length; i++) {
				if ($scope.statuses[i].code == order.status.code) {
					order.status.message = $scope.statuses[i].message;
					break;
				}
			}
		}).error(function(response) {
			errorHandler($scope, response);
		});
	};

	$scope.edit = function(order) {
		$window.location.href = "/servicer/order/edit/" + order.id;
	};

	$scope.prepareEditPrice = function(order) {
		order.priceEditing = true;
		order.newExpectedPaymentAmount = order.expectedPaymentAmount;
	};

	$scope.editPrice = function(order) {
		$http({
			method : "PUT",
			url : "/ajax/user/order/price",
			data : {
				data : [ {
					uid : order.uid,
					expectedPaymentAmount : order.newExpectedPaymentAmount
				} ]
			}
		}).success(function(response) {
			order.expectedPaymentAmount = response.data[0].expectedPaymentAmount;
			$scope.editPriceCancel(order);
		}).error(function(response) {
			errorHandler($scope, response);
		});
	};

	$scope.editPriceCancel = function(order) {
		order.priceEditing = false;
	};

	$scope.editRequest = function(order) {
		$http({
			method : "POST",
			url : "/ajax/user/order/proposeRequirement",
			data : {
				data : [ {
					uid : order.uid,
					expectedPaymentAmount : order.newExpectedPaymentAmount,
					paymentMethod : {
						code : order.newPaymentMethodCode
					},
					serviceInfo : {
						id : order.newServiceInfoId
					}
				} ]
			}
		}).success(function(response) {
			order.expectedPaymentAmount = response.data[0].expectedPaymentAmount;
			for (var index = 0; index < $scope.services; index++) {
				if ($scope.services[index].id == order.newServiceInfoId) {
					order.serviceInfo = $scope.services;
					break;
				}
			}

			for (var index = 0; index < $scope.paymentMethods; index++) {
				if ($scope.paymentMethods[index].code == 'R') {
					order.paymentMethod = $scope.paymentMethods[index];
					break;
				}
			}
			order.requestEditing = false;
		}).error(function(response) {
			errorHandler($scope, response);
		});
	};
	$scope.searchOrders();
});