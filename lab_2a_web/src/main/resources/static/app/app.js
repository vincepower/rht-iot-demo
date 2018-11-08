var app = angular.module('iot', ['ngMaterial','ui.router']);

app.config(function($stateProvider, $urlRouterProvider, $locationProvider) {

    $urlRouterProvider.otherwise('/');

    var mainState = {
        name: 'mainState',
        url: '/',
        templateUrl: 'app/main/index.html',
        controller: 'MainController'
    };


    $stateProvider.state(mainState);
});


