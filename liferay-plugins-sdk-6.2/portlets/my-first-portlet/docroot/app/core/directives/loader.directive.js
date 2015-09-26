(function() {
    'use strict';

    angular
        .module('myFirstApp.directives')
        .directive('egrLoader', egrLoader);

    function egrLoader() {
        var directive = {
            restrict: 'AE',
			templateURL: '/my-first-portlet/app/core/directives-templates/egrLoader.html',
			scope : {
				isloaded: '='
			},
			//link: link,
			//controller: controller
        };

        return directive;

//        function link(scope, element, attrs, ngModel) {
//        	
//        }
//        
//        function controller($scope) {
//        	
//        }
    }
})();
