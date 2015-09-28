(function() {
	'use strict';

	angular
		.module('myFirstApp.globalWeatherModule')
		.controller('CityWeatherCtrlAs', CityWeatherCtrlAs);

	CityWeatherCtrlAs.$inject = ['$stateParams','globalWeatherService'];

	function CityWeatherCtrlAs($stateParams, globalWeatherService) {
		var vm = this;
		vm.resultData = null;
		vm.contentLoaded = false;
		vm.countryName = $stateParams.countryName;
		vm.cityName = $stateParams.cityName;
	
        /**
         * Method will use the location service to get agencyCodes then do a callback to locationsCallbackAction
         */
        vm.loadDataAction = function() {
			globalWeatherService.getWeatherForCity(vm);
        };
        
        /**
         * Method called post 
         */
		vm.postLoadDataAction = function() {
			var isCityDataEmpty = jQuery.isEmptyObject(vm.cityWeather); // true
			if (isCityDataEmpty) {
				
				var messageObj = new MODEL.MessageObj('warning', 'The city '+vm.cityName+' does not have weather information at this time.');
				
				vm.messageObj = messageObj;
				vm.message = messageObj.toString();
				
//				vm.message = {
//                    type: 'warning',
//                    title: 'Warning',
//                    msg: 'The city '+vm.cityName+' does not have any weather information at this time.'
//                };
			}
		};
		
		
	  
		
	}//end of Ctrl	
})();
