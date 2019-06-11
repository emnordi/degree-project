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
public class Datapoint {
    String key;
    String value;

    public Datapoint(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey(){
        return key;
    }
    public String getValue(){
        return value;
    }
}
