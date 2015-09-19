(function() {
	'use strict';

	angular
		.module('myFirstApp.homeModule')
		.controller('HomeCtrlAs', HomeCtrlAs);

	HomeCtrlAs.$inject = ['landingSrv'];

	function HomeCtrlAs(landingSrv) {
		var vm = this;
		vm.resultData = null;
	
		vm.myAccountAction = function() {
			vm.resultData = landingSrv.getMyAccount(vm);
		};
		
		vm.weatherAction = function() {
			vm.resultData = landingSrv.weather(vm);
		};
		
	}//end of Ctrl	
})();
