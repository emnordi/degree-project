/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.view;

import client.controller.Controller;
import client.model.Sensor;
import client.model.Datapoint;
import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.net.URLEncoder;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;
import java.util.regex.*;

/**
 *
 * @author Emil
 */
@Named("dataManager")
@SessionScoped
public class View implements Serializable {

    private static final long serialVersionUID = 5443351151396868724L;
    private int s;

    //Used for login & register
    private String fname;
    private String surname;
    private String email;
    private String pwd;

    private Controller cont = new Controller();

    //Used for downloading data
    private String chosenSensor;
    private Sensor[] sensorsObject;
    private String startDate;
    private String endDate;
    //private String downloadEncrypt;
    //private String downloadMotion;
    private Boolean downloadEncrypt;
    private Boolean downloadMotion;

    //Used for adding a new sensor
    private String[] newTestbedsObject;
    private String newSensorName;
    private String newSensorTestbed;
    private String newSensorUnit;

    //Used for adding new testbed
    private String newTestbedAddress;
    private String newTestbedName;

    //Used for adding new metadata;
    private String newMetadataSensorID;
    private String newMetadataName;
    private String newMetadataValue;

    //Used for displaying data in table
    private String chosenSensorData;
    private Datapoint[] sensorDataList;
    private String displayDataStart;
    private String displayDataEnd;
    private String displayDataSensor;
    //private String displayEncrypt;
    //private String displayMotion;
    private Boolean displayEncrypt;
    private Boolean displayMotion;

    private String metaDataSensor;
    private Datapoint[] metaData;

    //Gets and sets for all variables
    public int getS() {
        return s;
    }

    public void setS(int s) {
        this.s = s;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getChosenSensor() {
        return chosenSensor;
    }

    public void setChosenSensor(String chosenSensor) {
        this.chosenSensor = chosenSensor;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getNewSensorName() {
        return newSensorName;
    }

    public void setNewSensorName(String newSensorName) {
        this.newSensorName = newSensorName;
    }

    public String getNewSensorTestbed() {
        return newSensorTestbed;
    }

    public void setNewSensorTestbed(String newSensorTestbed) {
        this.newSensorTestbed = newSensorTestbed;
    }

    public String getNewSensorUnit() {
        return newSensorUnit;
    }

    public void setNewSensorUnit(String newSensorUnit) {
        this.newSensorUnit = newSensorUnit;
    }

    public String getChosenSensorData() {
        return chosenSensorData;
    }

    public void setChosenSensorData(String chosenSensorData) {
        this.chosenSensorData = chosenSensorData;
    }

    public String getDisplayDataStart() {
        return displayDataStart;
    }

    public void setDisplayDataStart(String displayDataStart) {
        this.displayDataStart = displayDataStart;
    }

    public String getDisplayDataEnd() {
        return displayDataEnd;
    }

    public void setDisplayDataEnd(String displayDataEnd) {
        this.displayDataEnd = displayDataEnd;
    }

    public String getDisplayDataSensor() {
        return displayDataSensor;
    }

    public void setDisplayDataSensor(String displayDataSensor) {
        this.displayDataSensor = displayDataSensor;
    }

    public String getMetaDataSensor() {
        return metaDataSensor;
    }

    public void setMetaDataSensor(String metaDataSensor) {
        this.metaDataSensor = metaDataSensor;
    }

    public Datapoint[] getMetaData() {
        return metaData;
    }

    public void setMetadata(Datapoint[] metaData) {
        this.metaData = metaData;
    }

    public Datapoint[] getSensorDataList() {
        return sensorDataList;
    }

    public void setSensorDataList(Datapoint[] sensorDataList) {
        this.sensorDataList = sensorDataList;
    }

    public Boolean getDownloadEncrypt() {
        return downloadEncrypt;
    }

    public void setDownloadEncrypt(Boolean downloadEncrypt) {
        this.downloadEncrypt = downloadEncrypt;
    }

    public Boolean getDownloadMotion() {
        return downloadMotion;
    }

    public void setDownloadMotion(Boolean downloadMotion) {
        this.downloadMotion = downloadMotion;
    }

    public Boolean getDisplayEncrypt() {
        return displayEncrypt;
    }

    public void setDisplayEncrypt(Boolean displayEncrypt) {
        this.displayEncrypt = displayEncrypt;
    }

    public Boolean getDisplayMotion() {
        return displayMotion;
    }

    public void setDisplayMotion(Boolean displayMotion) {
        this.displayMotion = displayMotion;
    }

    public String getNewTestbedAddress() {
        return newTestbedAddress;
    }

    public void setNewTestbedAddress(String newTestbedAddress) {
        this.newTestbedAddress = newTestbedAddress;
    }

    public String getNewTestbedName() {
        return newTestbedName;
    }

    public void setNewTestbedName(String newTestbedName) {
        this.newTestbedName = newTestbedName;
    }

    public String getNewMetadataSensorID() {
        return newMetadataSensorID;
    }

    public void setNewMetadataSensorID(String newMetadataSensorID) {
        this.newMetadataSensorID = newMetadataSensorID;
    }

    public String getNewMetadataName() {
        return newMetadataName;
    }

    public void setnewMetadataName(String newMetadataName) {
        this.newMetadataName = newMetadataName;
    }

    public String getNewMetadataValue() {
        return newMetadataValue;
    }

    public void setNewMetadataValue(String newMetadataValue) {
        this.newMetadataValue = newMetadataValue;
    }

    public void test() {
        try {
            cont.addData();
            //downloadDataByTime();
            //getDataByTime();
            //getSensors();
            /*Response response = cont.metaDataBySensor(Long.valueOf("1"));
            String s = response.readEntity(String.class);
            JsonObject json = Json.createReader(new StringReader(s)).readObject();
            int i = json.getInt("metas");
            for (int j = 0; j < i; j++) {
                System.out.println(json.get(j + "name"));
                System.out.println(json.get(j + "value"));
            }*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * gets all testbeds in the database
     */
    public void getTestbeds() {
        Response response = cont.getTestbeds();
        String s = response.readEntity(String.class);
        JsonObject json = Json.createReader(new StringReader(s)).readObject();
        int i = Integer.parseInt(json.getString("testbeds"));
        for (int j = 0; j < i; j++) {
            System.out.println(json.get(j + "tid"));
            System.out.println(json.get(j + "name"));
            System.out.println(json.get(j + "address"));
        }
    }

    /**
     * Requests a json object of all sensors in the db
     */
    public void getStandardSensors() {
        Response response = cont.getSensors();
        String s = response.readEntity(String.class);
        JsonObject json = Json.createReader(new StringReader(s)).readObject();
        System.out.println(json.get("sensors"));
        int i = Integer.parseInt(json.getString("sensors"));
        for (int j = 0; j < i; j++) {
            System.out.println(json.get(j + "name"));
            System.out.println(json.get(j + "sid"));
            System.out.println(json.get(j + "unit"));
            System.out.println(json.get(j + "testbed"));
        }
    }

    /**
     * Requests data within a timeframe to be displayed on the overview page
     */
    public void getDataByTime() {
        System.out.println(displayDataStart + displayDataEnd + displayDataSensor);
        String encrypt = "0";
        String motion = "0";
        if (displayEncrypt) {
            encrypt = "1";
        }
        if (displayMotion) {
            motion = "1";
        }
        Response response = cont.getDataByTime(displayDataStart, displayDataEnd, displayDataSensor, encrypt, motion);
        String s = response.readEntity(String.class);
        JsonObject json = Json.createReader(new StringReader(s)).readObject();
        int i = json.getInt("datapoints");
        for (int j = 0; j < i; j++) {
            System.out.println(json.get(j + "value"));
            System.out.println(json.get(j + "timestamp"));
        }
    }

    /**
     * Downloads data from a chosen sensor between two timestamps
     *
     * @throws IOException
     */
    public void downloadDataByTime() throws IOException {
        String encrypt = "0";
        String motion = "0";
        String regex = "^\\d\\d\\d\\d-\\d\\d-\\d\\d\\s\\d\\d:\\d\\d:\\d\\d$";

        if (downloadEncrypt) {
            encrypt = "1";
        }
        if (downloadMotion) {
            motion = "1";
        }
        if (Pattern.matches(regex, startDate) && Pattern.matches(regex, endDate)){
            Response response = cont.dlDataByTime(startDate, endDate, chosenSensor, encrypt, motion);
            String s = response.readEntity(String.class);
            JsonObject json = Json.createReader(new StringReader(s)).readObject();
            if (json.getInt("datapoints") != 0) {
                String i = json.getString("path");
                cont.setCookie("filepath", i);
                //System.out.println("kakan är: " + cont.getCookie("filepath").getValue());
                ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                externalContext.redirect("Download?path=" + URLEncoder.encode(i, "UTF-8"));
            } else {
                showMessage("No data to download");
            }
        } else {
            showMessage("Dates must be of the format yyyy-mm-dd hh:mm:ss");
        }
    }

    /**
     * Is used when the user tries to register
     *
     * @return the overview page if successful register, otherwise register page
     */
    public String register() {
        Response response = cont.register(fname, surname, email, pwd);
        String s = response.readEntity(String.class);
        JsonObject json = Json.createReader(new StringReader(s)).readObject();
        System.out.println(json.get("status"));
        if (json.getString("status").equals("success")) {
            System.out.println(json.get("uid"));
            cont.setCookie("loginCookie", json.get("uid").toString());
            cont.setCookie("role", json.get("role").toString());
            return "overview";
        } else {
            showMessage("Register failed, try again");
            return "register";
        }

    }

    /**
     * Is used when the user tries to login
     *
     * @return the overview page if successful login, otherwise login page
     */
    public String login() {
        Response response = cont.login(email, pwd);
        String s = response.readEntity(String.class);
        JsonObject json = Json.createReader(new StringReader(s)).readObject();
        System.out.println(json.get("status"));
        if (json.getString("status").equals("success")) {
            System.out.println(json.get("uid"));
            cont.setCookie("loginCookie", json.get("uid").toString());
            cont.setCookie("role", json.get("role").toString());
            return "overview";
        } else {
            showMessage("Incorrect credentials, try again!");
            return "index";
        }
    }

    /**
     * Shows a message if form request is invalid
     *
     * @param message
     */
    private void showMessage(String message) {
        FacesContext context = FacesContext.getCurrentInstance();
        context.addMessage(null, new FacesMessage(message));
    }

    /**
     * Updates list of sensor data to be displayed
     *
     * @return sensorDataList with data from request
     */
    public void updateOverviewData() {
        if (displayDataStart != null) {
            String encrypt = "0";
            String motion = "0";
            String regex = "^\\d\\d\\d\\d-\\d\\d-\\d\\d\\s\\d\\d:\\d\\d:\\d\\d$";
            String reg2 = "\\d";
            if (displayEncrypt) {
                encrypt = "1";
            }
            if (displayMotion) {
                motion = "1";
            }
            if (Pattern.matches(reg2, displayDataSensor)) {
                if (Pattern.matches(regex, displayDataStart) && Pattern.matches(regex, displayDataEnd)) {
                    Response response = cont.getDataByTime(displayDataStart, displayDataEnd, displayDataSensor, encrypt, motion);
                    String s = response.readEntity(String.class);
                    JsonObject json = Json.createReader(new StringReader(s)).readObject();
                    int numberOfDatapoints = json.getInt("datapoints");
                    if (numberOfDatapoints != 0) {
                        sensorDataList = new Datapoint[numberOfDatapoints];

                        for (int i = 0; i < numberOfDatapoints; i++) {
                            sensorDataList[i] = new Datapoint(json.get(i + "value").toString(), json.get(i + "timestamp").toString());
                        }
                    } else {
                        showMessage("No datapoints found for given sensor, try again!");
                    }
                } else {
                    showMessage("Dates must be of the format yyyy-mm-dd hh:mm:ss");
                }
            } else {
                showMessage("Entered sensor must be a number");
            }

        }
    }

    /**
     * Used for the selectOneMenu downloading data
     *
     * @return
     */
    public Sensor[] getChosenSensorValue() {
        if (sensorsObject == null) {
            Response response = cont.getSensors();
            String s = response.readEntity(String.class);
            JsonObject json = Json.createReader(new StringReader(s)).readObject();
            int numberOfSensors = Integer.parseInt(json.getString("sensors"));
            sensorsObject = new Sensor[numberOfSensors];

            for (int i = 0; i < numberOfSensors; i++) {
                sensorsObject[i] = new Sensor(json.get(i + "name").toString(), json.get(i + "sid").toString(), json.get(i + "unit").toString(), json.get(i + "testbed").toString());
            }
        }
        return sensorsObject;
    }

    /**
     * Used for the selectOneMenu adding a new sensor
     *
     * @return
     */
    public String[] getMenuTestbedValue() {
        if (newTestbedsObject == null) {
            Response response = cont.getTestbeds();
            String s = response.readEntity(String.class);
            JsonObject json = Json.createReader(new StringReader(s)).readObject();
            int numberOfTestbeds = Integer.parseInt(json.getString("testbeds"));
            System.out.println(numberOfTestbeds);
            //sensorsObject = new Sensor[numberOfSensors];
            newTestbedsObject = new String[numberOfTestbeds];
            for (int i = 0; i < numberOfTestbeds; i++) {
                newTestbedsObject[i] = json.get(i + "tid").toString();
            }
        }
        return newTestbedsObject;
    }

    /**
     * Adds a new sensor to the database
     */
    public void addNewSensor() {
        System.out.println("New Sensor Name: " + newSensorName);
        System.out.println("New Sensor Testbed: " + newSensorTestbed);
        System.out.println("New Sensor Unit: " + newSensorUnit);
        //System.out.println(cont.getCookie("role").getValue());

        if (cont.getCookie("role").getValue().equals("1")) {
            System.out.println("Admin");
            Response response = cont.addSensor(newSensorName, newSensorUnit, newSensorTestbed);
            String s = response.readEntity(String.class);
            JsonObject json = Json.createReader(new StringReader(s)).readObject();
            String status = json.getString("status");
            System.out.println("Status: " + status);
        } else {
            System.out.println("User is not admin, cant add new sensor");
        }
    }

    /**
     * Adds a new testbed to the database
     */
    public void addNewTestbed() {
        System.out.println("New Testbed Name: " + newTestbedName);
        System.out.println("New Testbed Address: " + newTestbedAddress);

        if (cont.getCookie("role").getValue().equals("1")) {
            System.out.println("Admin");
            Response response = cont.addTestbed(newTestbedName, newTestbedAddress);
            String s = response.readEntity(String.class);
            JsonObject json = Json.createReader(new StringReader(s)).readObject();
            String status = json.getString("status");
            System.out.println("Status: " + status);
        } else {
            System.out.println("User is not admin, cant add new sensor");
        }
    }

    /**
     * Adds new metadata for a sensor
     */
    public void addNewMetadata() {
        System.out.println("New Metadata Sensor ID: " + newMetadataSensorID);
        System.out.println("New Metadata Name: " + newMetadataName);
        System.out.println("New Metadata Value: " + newMetadataValue);
        if (cont.getCookie("role").getValue().equals("1")) {
            System.out.println("Admin");
            Response response = cont.addMetadata(newMetadataName, newMetadataValue, newMetadataSensorID);
            String s = response.readEntity(String.class);
            JsonObject json = Json.createReader(new StringReader(s)).readObject();
            String status = json.getString("status");
            System.out.println("Status: " + status);
        } else {
            System.out.println("User is not admin, cant add new sensor");
            showMessage("User is not admin, cant add new sensor");
        }
    }

    /**
     * Updates the list of metadata displayed at the overview page
     */
    public void updateMetaData() {
        String regnum = "\\d";
        if (metaDataSensor != null && Pattern.matches(regnum, metaDataSensor)) {
            Response response = cont.metaDataBySensor(Long.parseLong(metaDataSensor));
            String s = response.readEntity(String.class);
            JsonObject json = Json.createReader(new StringReader(s)).readObject();
            int numberOfDatapoints = json.getInt("metas");
            if (numberOfDatapoints != 0) {
                metaData = new Datapoint[numberOfDatapoints];

                for (int i = 0; i < numberOfDatapoints; i++) {
                    metaData[i] = new Datapoint(json.get(i + "name").toString(), json.get(i + "value").toString());
                }
            } else {
                showMessage("No metadata for chosen sensor, try again!");
            }
        }else {
                showMessage("Sensor ID must be a number");
            }

    }
}
