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
		
		vm.getWeatherAction = function() {
			vm.resultData = landingSrv.getWeather(vm);
		};
		
		vm.saveWeatherAction = function() {
			vm.weatherModel = {status:"This is PUT angular", some:"angular something here"}; 
			vm.resultData = landingSrv.saveWeather(vm);
		};
		
	}//end of Ctrl	
})();
