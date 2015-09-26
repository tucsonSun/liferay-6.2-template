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
			//console.log(vm.citiesForCountry);
			vm.rowCollection = vm.citiesForCountry; 
		};
		
		
	    //copy the references (you could clone ie angular.copy but then have to go through a dirty checking for the matches)
	    vm.displayedCollection = [].concat(vm.rowCollection);
	    
	    vm.getWeatherForCity = function(row) {
	    	var city = row.City;
			console.log("vm.getWeatherForCity function called for city ....  "+city);
		};
		
	}//end of Ctrl	
})();
