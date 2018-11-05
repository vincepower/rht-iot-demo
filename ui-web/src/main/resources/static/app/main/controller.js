angular.module('iot').controller('MainController', function($scope, $rootScope, $http, $timeout, SensorData) {
    $scope.sensors = [];
   // $scope.data = {};

     SensorData.connectClient(function (data) {
        $scope.$apply(function () {
            var arr = [];
            for (elementName in data.metrics) {
                var item = {
                    'name': elementName,
                    'data': Number(data.metrics[elementName]).toFixed(2)
                };
                arr.push(item);
            }
            $scope.sensors = arr;
        });
    });

    $scope.showHistory = function (sensor) {
        $http.get('/history/' + sensor)
            .then(function res(data) {
                chartData = data.data;
                var labels = [];
                var series = [sensor];
                var data =[];



                chartData.dataset0.forEach(function (pt) {
                    labels.push(pt.x);
                    data.push(pt.val_0);
                });

                $scope.options = {
                    scales: {
                        yAxes: [
                            {
                                id: 'y',
                                type: 'linear',
                                display: true,
                                position: 'left'
                            }
                        ],
                        xAxes: [
                            {
                                ticks: {display: false}
                            }
                        ]
                    }
                };

                $scope.onClick = function (points, evt) {
                    console.log(points, evt);
                };

                $scope.labels = labels;
                $scope.series= series;
                $scope.data = [data];
                //$scope.data = newData;
                console.log($scope.data);
            })
    }


    /*
    var getData = function () {
        $http.get('http://localhost:8080/iot')
            .then(function (res) {
                $scope.data = res.data;
                $scope.sensors = res.data.sensors;
                errorCount = 0;
                nextLoad();
            })
            .catch(function (res) {
                $scope.data = 'Server error';
                nextLoad(++errorCount * 2 * loadTime);
            });
    };

    var cancelNextLoad = function () {
        $timeout.cancel(loadPromise);
    };

    var nextLoad = function (mill) {
        mill = mill || loadTime;
        cancelNextLoad();
        loadPromise = $timeout(getData, mill);
    };

    $scope.$on('$destroy', function () {
        cancelNextLoad();
    });

    $scope.data = 'Loading...';

    getData();

    //SensorData.getData();

*/
});
/*
    //$scope.selectedMachine = machine;
    SensorData.subscribeMachine(function (data) {
        $scope.$apply(function () {
            //addData(machine, data);
            console.log(data);
        });
    });
});
*/