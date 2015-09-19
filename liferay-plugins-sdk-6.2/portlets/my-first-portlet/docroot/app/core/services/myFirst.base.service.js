(function() {
	'use strict';

	angular
		.module('myFirstApp.services')
		.factory('myFirstBaseService', myFirstBaseService);

	myFirstBaseService.$inject = ['$resource', 'serviceConfigObject'];

	function myFirstBaseService($resource, serviceConfigObject) {

		return {			
			getMyAccount: $resource('/delegate/services/api/myaccount', {}, serviceConfigObject),
			weather: $resource('/delegate/services/api/weather', {}, serviceConfigObject)
		};
	}

})();
