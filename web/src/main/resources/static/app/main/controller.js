angular.module('iot').controller("MainController", function($scope, $rootScope, $http, $timeout) {
    $scope.sensors = [];
    var loadTime = 1000, errorCount = 0, loadPromise;

    var getData = function () {
        $http.get('http://localhost:8080/iot')
        .then(function(res) {
            $scope.data = res.data;
            $scope.sensors = res.data.sensors;
            errorCount =0;
            nextLoad();
        })
        .catch(function(res) {
            $scope.data = 'Server error';
            nextLoad(++errorCount * 2 * loadTime);
        });
    };

    var cancelNextLoad = function() {
        $timeout.cancel(loadPromise);
    };

    var nextLoad = function(mill) {
        mill = mill || loadTime;
        cancelNextLoad();
        loadPromise = $timeout(getData, mill);
    };

    $scope.$on('$destroy', function() {
        cancelNextLoad();
    });

    $scope.data = 'Loading...';

    getData();


    /*
    var sensor1 = {display:'1', data:'0.0', name:'0', state: true};
    var sensor2 = {display:'2', data:'0.0', name:'1', state: true};
    var sensor3 = {display:'3', data:'0.0', name:'2', state: true};
    $scope.sensors.push(sensor1);
    $scope.sensors.push(sensor2);
    $scope.sensors.push(sensor3);
    $scope.raiseError = function(name) {
        if ($scope.sensors[name].state) {
            $scope.sensors[name].data = "-1.0";
            $scope.sensors[name].state = false;
        } else {
            $scope.sensors[name].data = "0.0";
            $scope.sensors[name].state = true;
        }
    }
    */
});