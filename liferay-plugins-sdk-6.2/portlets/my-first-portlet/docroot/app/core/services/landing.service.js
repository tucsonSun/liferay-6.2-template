(function() {
    'use strict';

    angular
        .module('myFirstApp.services')
        .factory('landingSrv', landingSrv);

    landingSrv.$inject = ['myFirstBaseService'];

    function landingSrv(myFirstBaseService) {
        return {
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
                        msg: 'There was a problem processing your request. Please contact us at 1.800.231.1363 so that we can assist.'
                    };
                    vm.contentLoaded = true;
                });
            }
        };
    }
})();
