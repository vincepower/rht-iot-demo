angular.module('iot').factory('SensorData', function ($http, $rootScope) {

    var factory = {},
        client = null,
        connected = false,
        listeners = [],
        ignoreAlerts = false;

    function handleTelemetry(message) {
        //console.log(message.payloadString);
        var payload = message.payloadString;
        listeners.forEach( function(listener){
            var obj = JSON.parse(payload);
            listener.listener(obj);
        }) ;
    }

    function onMessageArrived(message) {
        if (connected) {
            handleTelemetry(message);
        }
    }

    function onConnectionLost(responseObject) {
        connected = false;
        if (responseObject.errorCode !== 0) {
            console.log("onConnectionLost:" + responseObject.errorMessage);
            //$rootScope.$broadcast("activity:gateway", {type: "warning", duration: 5000});
            // start sim for listening machines
            listeners.forEach(function (listener) {
                if (listener.objType === 'machines') {
                    //startSim(listener.machine.currentFid, listener.machine.currentLid, listener.machine.mid);
                }
            });
            connectClient(1);

        }
    }


     factory.connectClient = function (listener) {

        attempt = 1;
        var MAX_ATTEMPTS = 100;

        if (attempt > MAX_ATTEMPTS) {
            console.log("Cannot connect to broker after " + MAX_ATTEMPTS + " attempts, reload to retry");
            //$rootScope.$broadcast("activity:gateway", {type: "warning", duration: 2000});
            return;
        }

        if (attempt > 1) {
            console.log("Trouble connecting to broker, will keep trying (reload to re-start the count)");
            //$rootScope.$broadcast("activity:gateway", {type: "warning", duration: 2000});
        }
        var brokerHostname = $rootScope.config.mqtt_host;
        var brokerPort = $rootScope.config.mqtt_port;

        client = new Paho.MQTT.Client(brokerHostname, Number(brokerPort), "dashboard-ui-client-" + guid());

        client.onConnectionLost = onConnectionLost;
        client.onMessageArrived = onMessageArrived;

        *** Student Connect ****

    }

    function guid() {
        function s4() {
            return Math.floor((1 + Math.random()) * 0x10000)
                .toString(16)
                .substring(1);
        }

        return s4() + s4() + '-' + s4() + '-' + s4() + '-' +
            s4() + '-' + s4() + s4() + s4();
    }

    factory.subscribeMachine = function (listener) {
        connectClient(1);

        var count = 0;

        var topicName = 'iot-demo/data';

        try {
            client.subscribe(topicName);
            console.log("subscribed to " + topicName);
        } catch (err) {
            console.log("cannot subscribe to " + topicName + ": " + JSON.stringify(err));
        }
        listeners.push({
            topic: topicName,
            objType: 'machines',
            objId: 'machine-1',
            listener: listener
        });
        if (!connected) {
            console.log("broker not connected. starting simulation for " + topicName);
            //startSim(machine.currentFid, machine.currentLid, machine.mid);
        }
    };

    factory.connectBroker = function (attempts) {
        connectClient(attempts);
        return connected;
    };

    return factory;
});
