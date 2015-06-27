(function() {
    'use strict';

    angular.module('myFirstApp.directives')
        .directive('cpmiLoader', cpmiLoader);

        function cpmiLoader() {
            var directive = {
                restrict: 'A',
                template: '<div class="loader" ng-hide="cpmiContentLoaded"></div>',
                scope: {
                    cpmiContentLoaded: '='
                }
            };
            return directive;
        }

})();
