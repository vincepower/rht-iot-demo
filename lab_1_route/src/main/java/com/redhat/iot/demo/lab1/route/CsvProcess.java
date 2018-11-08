package com.redhat.iot.demo.lab1.route;

import com.redhat.iot.demo.lab1.route.pojo.Sensor;
import com.redhat.iot.demo.lab1.route.pojo.SensorData;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.List;
import java.util.Map;

public class CsvProcess implements Processor {
    @Override
    public void process(Exchange msg) throws Exception {
        List<Map> metrics = (List<Map>) msg.getIn().getBody();
        Map<String, String> map = metrics.get(0); //Each line of the file produces a map of name/value pairs, but we only get one line at a time due to the splitter above
        SensorData data = new SensorData();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            switch (entry.getKey()) {
                case "offset":
                    data.setOffset(entry.getValue());
                    data.setName("Machine1");
                    break;
                case "sensor0":
                    Sensor sensor0 = new Sensor();
                    sensor0.setName("Temperature");
                    sensor0.setData(entry.getValue());
                    data.getSensors()[0] = sensor0;
                    break;
                case "sensor1":
                    Sensor sensor1 = new Sensor();
                    sensor1.setName("Vibrations");
                    sensor1.setData(entry.getValue());
                    data.getSensors()[1] = sensor1;
                    break;
                case "sensor2":
                    Sensor sensor2 = new Sensor();
                    sensor2.setName("Pressure");
                    sensor2.setData(entry.getValue());
                    data.getSensors()[2] = sensor2;
                    break;
                case "sensor3":
                    Sensor sensor3 = new Sensor();
                    sensor3.setName("Pressure");
                    sensor3.setData(entry.getValue());
                    data.getSensors()[3] = sensor3;
                    break;
                case "sensor4":
                    Sensor sensor4 = new Sensor();
                    sensor4.setName("Light");
                    sensor4.setData(entry.getValue());
                    data.getSensors()[4] = sensor4;
                    break;
                case "sensor5":
                    Sensor sensor5 = new Sensor();
                    sensor5.setName("Voltage");
                    sensor5.setData(entry.getValue());
                    data.getSensors()[5] = sensor5;
                    break;
                case "sensor6":
                    Sensor sensor6 = new Sensor();
                    sensor6.setName("Motion");
                    sensor6.setData(entry.getValue());
                    data.getSensors()[6] = sensor6;
                    break;
                default:
                    System.out.println("---------------------");
                    System.out.println(entry.getKey() + ":" + entry.getValue());
                    break;
            }
        }

        System.out.println(data);
        msg.getIn().setBody(data);
    }
}
