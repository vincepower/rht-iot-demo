package com.rehat.iot.web.pojo;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SensorData {
    String offset;
    String name;
    List<Sensor> sensors = new ArrayList<>(7);
    
    
    public SensorData() {
        String offset = "0";
        String name = "machine1";

        Sensor sensor0 = new Sensor();
        sensor0.setName("Temperature");
        sensor0.setData("0");
        this.getSensors().add(0, sensor0);
       
      
        Sensor sensor1 = new Sensor();
        sensor1.setName("Vibrations");
        sensor1.setData("0");
        this.getSensors().add(1, sensor1);
       
  
        Sensor sensor2 = new Sensor();
        sensor2.setName("Pressure");
        sensor2.setData("0");
        this.getSensors().add(2, sensor2);
       
        
        Sensor sensor3 = new Sensor();
        sensor3.setName("Pressure");
        sensor3.setData("0");
        this.getSensors().add(3, sensor3);
       
        
        Sensor sensor4 = new Sensor();
        sensor4.setName("Light");
        sensor4.setData("0");
        this.getSensors().add(4, sensor4);
        
        Sensor sensor5 = new Sensor();
        sensor5.setName("Voltage");
        sensor5.setData("0");
        this.getSensors().add(5, sensor5);

        
        Sensor sensor6 = new Sensor();
        sensor6.setName("Motion");
        sensor6.setData("0");
        this.getSensors().add(6, sensor6);
    }
}
