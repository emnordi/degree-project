/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.model;

/**
 *
 * @author lucas
 */
public class Sensor {
    String name;
    String sid;
    String unit;
    String testbed;

    public Sensor(String name, String sid, String unit, String testbed) {
        this.name = name;
        this.sid = sid;
        this.unit = unit;
        this.testbed = testbed;
    }

    public String getName(){
        return name;
    }
    public String getSid(){
        return sid;
    }
    public String getUnit(){
        return unit;
    }
    public String getTestbed(){
        return testbed;
    }
}
