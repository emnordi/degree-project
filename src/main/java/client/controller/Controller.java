/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.controller;

import client.model.CookieHandler;
import client.net.Net;
import javax.servlet.http.Cookie;
import javax.ws.rs.core.Response;

/**
 *
 * @author Emil
 */
public class Controller {
    private Net net = new Net();
    private CookieHandler cookie = new CookieHandler();
    /**
     * Call on register in Net class
     * @param fname user first name
     * @param sname user surname
     * @param email user email
     * @param pwd user password
     * @return 
     */
    public Response register(String fname, String sname, String email, String pwd){
        return net.register(fname, sname, email, pwd);
    }
    /**
     * Call to login in Net class
     * @param email
     * @param password
     * @return 
     */
    public Response login(String email, String password){
        return net.login(email, password);
    }
    /**
     * Call to setCookie in Model class
     * @param name
     * @param value 
     */
    public void setCookie(String name, String value){
        cookie.setCookie(name, value);
    }
    /**
     * Call to getCookie in Model class
     * @param name
     * @return 
     */
    public Cookie getCookie(String name){
        return cookie.getCookie(name);
    }
    /**
     * Call to getTestbeds in Net class
     * @return 
     */
    public Response getTestbeds(){
        return net.getTestbeds();
    }
    /**
     * Call to getSensors in Net class
     * @return 
     */
    public Response getSensors(){
        return net.getSensors();
    }
    /**
     * Call to getDataByTime in Net class
     * @return 
     */
    public Response getDataByTime(String dateFrom, String dateTo, String sensorID, String encrypt, String motion){
        return net.getDataByTime(dateFrom, dateTo, sensorID, encrypt, motion);
    }
    
    /**
     * Retrieves metadata for sensor
     * @param sid sensor 
     * @return list of metadata
     */
    public Response metaDataBySensor(Long sid){
        return net.getMetadataBySensor(sid);
    }
    
    /**
     * Call to dlDataByTime in Net class
     * @param startDate
     * @param endDate
     * @param sensorID
     * @return 
     */

    public Response dlDataByTime(String startDate, String endDate, String sensorID, String encrypt, String motion){
        return net.dlDataByTime(startDate, endDate, sensorID, encrypt, motion);
    }
    /**
     * Call to servtest in Net class
     */
    public void addData(){
        net.servtest();
    }
    
    public Response addSensor(String name, String unit, String tb) {
        return net.addSensor(name, unit, tb);
    }
    
    public Response addTestbed(String name, String address){
        return net.addTestbed(name, address);
    }
    
    public Response addMetadata(String name, String value, String sensor){
        return net.addMetadata(name, value, sensor);
    }
}
