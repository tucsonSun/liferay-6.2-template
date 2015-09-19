(function() {
    'use strict';

    angular
        .module('myFirstApp.services')
        .factory('landingSrv', landingSrv);

    landingSrv.$inject = ['myFirstBaseService'];

    function landingSrv(myFirstBaseService) {
        return {
        	weatherPromsie: function() {
        		return myFirstBaseService.weather.get().$promise;
        	},
        	
        	getMyAccountPromise: function() {
                return myFirstBaseService.getMyAccount.get().$promise;
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
            
            weather: function(vm) {
            	this.weatherPromsie().then(function(response) {
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
