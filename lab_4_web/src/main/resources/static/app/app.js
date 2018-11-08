var app = angular.module('iot', ['ngMaterial','ui.router','chart.js']);
/*
angular.element(document).ready(function () {

    // get config
    var initInjector = angular.injector(["ng"]);
    var $http = initInjector.get("$http");

    $http.get("config").then(function (response) {
        app.constant("APP_CONFIG", response.data);
        console.log("Bootstrapping system");
        console.log(APP_CONFIG);
        angular.bootstrap(document, ["iot"], {
            strictDi: true
        });
    });
});
*/
app.config(function($stateProvider, $urlRouterProvider) {

   $urlRouterProvider.otherwise('/');

    var mainState = {
        name: 'mainState',
        url: '/',
        templateUrl: 'app/main/index.html',
        controller: 'MainController'
    };


    $stateProvider.state(mainState);
});