package com.redhat.iot.uiweb.pojo.history;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ChartData {
    boolean hasData = true;
    double upperLimit;
    double lowerLimit;
    int value = 0;
    List<ChartPoint> dataset0 = new ArrayList<>();
}
