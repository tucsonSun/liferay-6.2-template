(function() {
    'use strict';

    angular
        .module('myFirstApp.services')
        .factory('landingSrv', landingSrv);

    landingSrv.$inject = ['myFirstBaseService'];

    function landingSrv(myFirstBaseService) {
        return {
        	getWeatherPromsie: function() {
        		return myFirstBaseService.weather.get().$promise;
        	},
        	
        	saveWeatherPromsie: function(vm) {
        		return myFirstBaseService.weather.save(vm.weatherModel).$promise;
        	},
        	
        	getMyAccountPromise: function() {
                return myFirstBaseService.myAccount.get().$promise;
            },

            getMyAccount: function(vm) {
            	this.getMyAccountPromise().then(function(response) {
                    if (response.succeeded) {
                        vm.resultData = response.data;
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
            },
            
            getWeather: function(vm) {
            	this.getWeatherPromsie().then(function(response) {
                    if (response.succeeded) {
                        vm.resultData = response.data;
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
            },
            
            saveWeather: function(vm) {
            	this.saveWeatherPromsie(vm).then(function(response) {
                    if (response.succeeded) {
                        vm.resultData = response.data;
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
