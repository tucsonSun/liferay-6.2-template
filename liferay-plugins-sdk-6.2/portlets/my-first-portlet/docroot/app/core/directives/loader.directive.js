(function() {
    'use strict';

    angular
        .module('myFirstApp.directives')
        .directive('egrLoader', egrLoader);

    function egrLoader() {
        var directive = {
            restrict: 'AE',
			template: '<div class="sk-cube-grid" ng-hide="isloaded">'+
				'<div class="sk-cube sk-cube1"></div>'+
				  '<div class="sk-cube sk-cube2"></div>'+
				  '<div class="sk-cube sk-cube3"></div>'+
				  '<div class="sk-cube sk-cube4"></div>'+
				  '<div class="sk-cube sk-cube5"></div>'+
				  '<div class="sk-cube sk-cube6"></div>'+
				  '<div class="sk-cube sk-cube7"></div>'+
				  '<div class="sk-cube sk-cube8"></div>'+
				  '<div class="sk-cube sk-cube9"></div>'+
				'</div>',
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
