    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.net;

import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.spi.JsonProvider;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Emil
 */
public class Net {

    private final static String BASE_URL = "http://localhost:8080/degthesis/webresources";
    private Client client = ClientBuilder.newClient();

    public void servtest() {
        WebTarget webTarget = client.target(BASE_URL);
        webTarget = webTarget.path("senser");
        webTarget = webTarget.path("tester");
        Invocation.Builder invocationBuilder = webTarget.request();

        JsonObject json = JsonProvider.provider().createObjectBuilder()
                .add("fname", "Lucas")
                .add("surname", "Man")
                .add("email", "lucastheman@kth.se")
                .add("password", "123")
                .add("privilege", "1")
                .build();

        Response response = invocationBuilder.post(Entity.entity(json, MediaType.APPLICATION_JSON));
        String s = response.readEntity(String.class);
        json = Json.createReader(new StringReader(s)).readObject();
        System.out.println(json.get("test"));

    }

    /**
     * Creates a register request
     * @param fname
     * @param surname
     * @param email
     * @param password
     * @return Response from the register request
     */
    public Response register(String fname, String surname, String email, String password) {
        WebTarget webTarget = client.target(BASE_URL);
        webTarget = webTarget.path("senser");
        webTarget = webTarget.path("register");
        Invocation.Builder invocationBuilder = webTarget.request();

        JsonObject json = JsonProvider.provider().createObjectBuilder()
                .add("fname", fname)
                .add("surname", surname)
                .add("email", email)
                .add("password", password)
                .add("privilege", "1")
                .build();

        return invocationBuilder.post(Entity.entity(json, MediaType.APPLICATION_JSON));
    }
    
    /**
     * Creates a login request
     * @param email
     * @param password
     * @return Response from the login request
     */
    public Response login(String email, String password) {
        WebTarget webTarget = client.target(BASE_URL);
        webTarget = webTarget.path("senser");
        webTarget = webTarget.path("login");
        Invocation.Builder invocationBuilder = webTarget.request();

        JsonObject json = JsonProvider.provider().createObjectBuilder()
                .add("email", email)
                .add("password", password)
                .build();

        return invocationBuilder.post(Entity.entity(json, MediaType.APPLICATION_JSON));
    }

    /**
     * Creates a request to get all testbeds in the db
     * @return Response from the request
     */
    public Response getTestbeds() {
        WebTarget webTarget = client.target(BASE_URL);
        webTarget = webTarget.path("senser");
        webTarget = webTarget.path("testbeds");
        Invocation.Builder invocationBuilder = webTarget.request();

        JsonObject json = JsonProvider.provider().createObjectBuilder()
                .add("fname", "Lucas")
                .add("surname", "Man")
                .add("email", "lucastheman@kth.se")
                .add("password", "123")
                .add("privilege", "1")
                .build();

        return invocationBuilder.post(Entity.entity(json, MediaType.APPLICATION_JSON));

    }

    /**
     * Requests all sensors in the db
     * @return Response from the request
     */
    public Response getSensors() {
        WebTarget webTarget = client.target(BASE_URL);
        webTarget = webTarget.path("senser");
        webTarget = webTarget.path("sensors");
        Invocation.Builder invocationBuilder = webTarget.request();

        JsonObject json = JsonProvider.provider().createObjectBuilder()
                .add("fname", "Lucas")
                .add("surname", "Man")
                .add("email", "lucastheman@kth.se")
                .add("password", "123")
                .add("privilege", "1")
                .build();

        return invocationBuilder.post(Entity.entity(json, MediaType.APPLICATION_JSON));
    }

    /**
     * Requests data from a sensor in a time interval
     * @param dateFrom
     * @param dateTo
     * @param sensorID
     * @return Response from the request
     */
    public Response getDataByTime(String dateFrom, String dateTo, String sensorID, String encrypt, String motion) {
        WebTarget webTarget = client.target(BASE_URL);
        webTarget = webTarget.path("senser");
        webTarget = webTarget.path("datarequest");
        Invocation.Builder invocationBuilder = webTarget.request();
        JsonObject json = JsonProvider.provider().createObjectBuilder()
                .add("dateFrom", dateFrom) //"2019-02-01 00:00:01"
                .add("dateTo", dateTo) //"2019-03-20 00:00:01"
                .add("sensorID", sensorID) //"6"
                .add("userID", "1")
                .add("encrypt", encrypt)
                .add("motion", motion)
                .build();

        return invocationBuilder.post(Entity.entity(json, MediaType.APPLICATION_JSON));
        
    }
    
    public Response addSensor(String name, String unit, String tb){
        WebTarget webTarget = client.target(BASE_URL);
        webTarget = webTarget.path("senser");
        webTarget = webTarget.path("addSensor");
        Invocation.Builder invocationBuilder = webTarget.request();

        JsonObject json = JsonProvider.provider().createObjectBuilder()
                .add("name", name)
                .add("unit", unit)
                .add("testbedID", tb)
                .build();
        //Returnerar object med status "fail" eller status "success"
        return invocationBuilder.post(Entity.entity(json, MediaType.APPLICATION_JSON));
    }
    public Response addTestbed(String name, String address){
        WebTarget webTarget = client.target(BASE_URL);
        webTarget = webTarget.path("senser");
        webTarget = webTarget.path("addTestbed");
        Invocation.Builder invocationBuilder = webTarget.request();

        JsonObject json = JsonProvider.provider().createObjectBuilder()
                .add("name", name)
                .add("address", address)
                .build();
        //Returnerar object med status "fail" eller status "success"
        return invocationBuilder.post(Entity.entity(json, MediaType.APPLICATION_JSON));
    }
    public Response addMetadata(String name, String value, String sensor){
        WebTarget webTarget = client.target(BASE_URL);
        webTarget = webTarget.path("senser");
        webTarget = webTarget.path("addMetadata");
        Invocation.Builder invocationBuilder = webTarget.request();

        JsonObject json = JsonProvider.provider().createObjectBuilder()
                .add("name", name)
                .add("value", value)
                .add("sensorID", sensor)
                .build();
        //Returnerar object med status "fail" eller status "success"
        return invocationBuilder.post(Entity.entity(json, MediaType.APPLICATION_JSON));
    }
    
    public Response getMetadataBySensor(Long sid) {
        WebTarget webTarget = client.target(BASE_URL);
        webTarget = webTarget.path("senser");
        webTarget = webTarget.path("metadata");
        Invocation.Builder invocationBuilder = webTarget.request();

        JsonObject json = JsonProvider.provider().createObjectBuilder()
                .add("sensorID", String.valueOf(sid))
                .build();

        return invocationBuilder.post(Entity.entity(json, MediaType.APPLICATION_JSON));
    }

    /**
     * Requests to download data from a sensor in a time interval
     * @param dateFrom
     * @param dateTo
     * @param sensorID
     * @return Response from the request
     */
    public Response dlDataByTime(String dateFrom, String dateTo, String sensorID, String encrypt, String motion) {
        WebTarget webTarget = client.target(BASE_URL);
        webTarget = webTarget.path("senser");
        webTarget = webTarget.path("datadl");
        Invocation.Builder invocationBuilder = webTarget.request();

        JsonObject json = JsonProvider.provider().createObjectBuilder()
                .add("dateFrom", dateFrom) //"2019-02-01 00:00:01"
                .add("dateTo", dateTo) //"2019-03-20 00:00:01"
                .add("sensorID", sensorID) //"6"
                .add("userID", "1")
                .add("motion", motion)
                .add("encrypt", encrypt)
                .build();

        return invocationBuilder.post(Entity.entity(json, MediaType.APPLICATION_JSON));
        
    }

}
