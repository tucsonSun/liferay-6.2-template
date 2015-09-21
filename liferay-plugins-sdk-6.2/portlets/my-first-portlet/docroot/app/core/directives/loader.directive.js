(function() {
    'use strict';

    angular.module('myFirstApp.directives')
        .directive('egrLoader', egrLoader);

        function egrLoader() {
            var directive = {
                restrict: 'A',
                template: '<div class="loader" ng-hide="egrContentLoaded"></div>',
                scope: {
                	egrContentLoaded: '='
                }
            };
            return directive;
        }

})();
