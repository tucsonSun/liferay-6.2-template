(function() {
	'use strict';

	angular
		.module('myFirstApp.globalWeatherModule')
		.controller('GlobalWeatherCtrlAs', GlobalWeatherCtrlAs);

	GlobalWeatherCtrlAs.$inject = ['globalWeatherService'];

	function GlobalWeatherCtrlAs(globalWeatherService) {
		var vm = this;
		vm.resultData = null;
	
        /**
         * Method will use the location service to get agencyCodes then do a callback to locationsCallbackAction
         */
        vm.loadDataAction = function() {
        	var countryName = "United States"
			vm.countryName = countryName;
			globalWeatherService.getWeatherCitiesForCountry(vm);
        };
        
        /**
         * Method called post 
         */
		vm.postLoadDataAction = function() {
			console.log(vm.citiesForCountry);
		};
		
		
		
	}//end of Ctrl	
})();
