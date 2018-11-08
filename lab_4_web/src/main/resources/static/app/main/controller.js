angular.module('iot').controller('MainController', function($scope, $rootScope, $http, $timeout, SensorData) {
    $scope.sensors = [];
   // $scope.data = {};





    getData = function() {
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
    }


    $scope.showHistory = function (sensor){
        $scope.machine = {
            device: 'maachine-1',
            name: 'Castor',
            sensor: sensor
        };

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

    if (!$rootScope.config) {
        $http.get('config')
            .then(function(conf){
               $rootScope.config = conf.data;
               getData();
            });
    } else {
        getData();
    }

});
