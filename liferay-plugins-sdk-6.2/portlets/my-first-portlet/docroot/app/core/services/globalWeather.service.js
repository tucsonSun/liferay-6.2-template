(function() {
    'use strict';

    angular
        .module('myFirstApp.services')
        .factory('globalWeatherService', globalWeatherService);

    globalWeatherService.$inject = ['myFirstBaseService'];

    function globalWeatherService(myFirstBaseService) {
        return {
        	getWeatherCitiesForCountryPromsie: function(countryName) {
        		return myFirstBaseService.weatherCitiesForCountry.get(countryName).$promise;
        	},
        	
            
            getWeatherCitiesForCountry: function(vm) {
            	if (!vm.countryName)
            		throw new Error("The param 'countryName' is not allowed to be null.");
            	
            	this.getWeatherCitiesForCountryPromsie(vm.countryName).then(function(response) {
                    if (response.succeeded) {
                    	vm.weatherCitiesForCountryResponse = response;
                    	var data = response.data;
                    	vm.citiesForCountry = data.NewDataSet.Table;
                        vm.postLoadDataAction();
                        vm.contentLoaded = true;
                    } else {
                        vm.message = response.message;
                        vm.contentLoaded = true;
                    }
                })
                .catch(function(error){
                    vm.message = {
                        type: 'error',
                        msg: 'There was a problem processing your request.'
                    };
                    vm.contentLoaded = true;
                });
            }
        };
    }
})();
