'use strict';

angular.module('dashboardApp').controller('CommonController', CommonController);

CommonController.$inject = ['$scope', '$rootScope', '$state', '$window', 'Utils', 'scimFactory', 'RegistrationRequestService'];

function CommonController($scope, $rootScope, $state, $window, Utils, scimFactory, RegistrationRequestService) {

	var commonCtrl = this;
	commonCtrl.name = "CommonController";

	$rootScope.loggedUser = Utils.getLoggedUser();

	console.log("Logged user", $rootScope.loggedUser);
	
	scimFactory.getMe().then(function(response) {
		console.log(response.data);
		$rootScope.loggedUser.me = response.data;
	}, function(error) {
		$state.go("error", {
			"error": error
		});
	});
	
	RegistrationRequestService.listPending().then(function(response) {
		console.log(response.data);
		$rootScope.loggedUser.pendingRequests = response.data;
	}, function(error) {
		console.log(error);
		$rootScope.loggedUser.pendingRequests = undefined;
	});
	
	scimFactory.getUsers(1, 1).then(function(response) {
		console.log(response.data);
		$rootScope.loggedUser.totUsers = response.data.totalResults;
	}, function(error) {
		console.log(error);
		$rootScope.loggedUser.totUsers = undefined;
	});

	scimFactory.getGroups(1, 1).then(function(response) {
		console.log(response.data);
		$rootScope.loggedUser.totGroups = response.data.totalResults;
	}, function(error) {
		$rootScope.loggedUser.totGroups = undefined;
	});

	$rootScope.reload = function() {
		$window.location.reload();
	}
}
