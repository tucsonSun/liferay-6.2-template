(function() {
    'use strict';

    angular
        .module('myFirstApp')
        .config(routeConfig);

    routeConfig.$inject = ['$stateProvider', '$urlRouterProvider'];

    function routeConfig($stateProvider, $urlRouterProvider) {
        $urlRouterProvider.otherwise("/home");

        $stateProvider
            .state('home', {
                url: '/home',
                templateUrl: '/my-first-portlet/app/featureSets/home/home.html',
                controller: 'HomeCtrlAs',
                controllerAs: 'vm'
            })
            .state('sell', {
                url: '/sell',
                templateUrl: '/my-first-portlet/app/featureSets/sell.html',
                controller: 'SellController'
            })
            .state('sell.lemonade', {
                url: '/lemonade',
                templateUrl: '/my-first-portlet/app/featureSets/sell-lemonade.html',
                controller: function($scope) {
                    $scope.info = {
                        description: 'I love drinking this stuff',
                        health: 'Little lemon ... lots of sugar!'
                    };
                }
            });
    }
})();
