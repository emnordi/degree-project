/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.ejb.Stateless;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.spi.JsonProvider;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import server.controller.Controller;
import server.model.Datapoint;
import server.model.Metadata;
import server.model.Sensor;
import server.model.Testbed;
import server.model.User;

/**
 * REST Web Service
 *
 * @author Emil
 */
@Stateless
@Path("senser")
public class Net {

    @Inject
    private Controller cont;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of Net
     */
    public Net() {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("ztester")
    public Response lol() {
        //INTIAL request for sensors/testbeds to show
        System.out.println("recieved");
        //System.out.println(cont.test());
        JsonObject json = JsonProvider.provider().createObjectBuilder()
                .add("test", "hello").build();
        return Response.ok(json).build();
    }

    /**
     *
     * @param entity
     * @return
     */
    @POST
    @Path("/tester")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getter(JsonObject entity) {
        System.out.println("recieved request");
        JsonObjectBuilder ob = JsonProvider.provider().createObjectBuilder();
        if (cont.test()) {
            ob.add("test", "Register successful");
        } else {
            ob.add("test", "Register unsuccessful");
        }
        cont.addTestbed();
        //cont.teste();

        //ob.add("test", "d");
        JsonObject json = ob.build();
        return Response.ok(json).build();

    }

    /**
     * Retrieves all testbeds and returns to user
     *
     * @param entity
     * @return all the testbeds from the database
     */
    @POST
    @Path("/testbeds")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response testbeds(JsonObject entity) {
        JsonObjectBuilder ob = JsonProvider.provider().createObjectBuilder();
        List<Testbed> l = cont.allTestbeds();
        ob.add("testbeds", String.valueOf(l.size()));
        if (!l.isEmpty()) {
            int i = 0;
            for (Testbed t : l) {
                ob.add(String.valueOf(i) + "tid", t.getTid());
                ob.add(String.valueOf(i) + "name", t.getName());
                ob.add(String.valueOf(i) + "address", t.getAddress());
                i++;
            }
        }
        JsonObject json = ob.build();
        return Response.ok(json).build();
    }

    /**
     * Reqeusts all the sensors from the database
     *
     * @param entity
     * @return all the sensors from the database
     */
    @POST
    @Path("/sensors")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sensors(JsonObject entity) {
        System.out.println("Requesting all sensors");
        JsonObjectBuilder ob = JsonProvider.provider().createObjectBuilder();
        List<Sensor> l = cont.allSensors();
        ob.add("sensors", String.valueOf(l.size()));
        if (!l.isEmpty()) {
            int i = 0;
            for (Sensor s : l) {
                ob.add(String.valueOf(i) + "name", s.getName());
                ob.add(String.valueOf(i) + "sid", s.getSid());
                ob.add(String.valueOf(i) + "unit", s.getUnit());
                ob.add(String.valueOf(i) + "testbed", s.getTid().getTid());
                i++;
            }
        }
        JsonObject json = ob.build();
        return Response.ok(json).build();
    }

    /**
     * Retrieves metadata for a sensor
     *
     * @param entity containing sensor id
     * @return metadata for a sensor
     */
    @POST
    @Path("/metadata")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMetadata(JsonObject entity) {
        System.out.println("Requesting all metadata for sensor");
        JsonObjectBuilder ob = JsonProvider.provider().createObjectBuilder();
        try {
            Long sid = Long.valueOf(entity.getString("sensorID"));
            List<Sensor> sensors = cont.getSensor(sid);
            if (sensors.isEmpty()) {
                throw new Exception("No sensor found");
            }
            List<Metadata> meta = cont.metaDataBySensor(sensors.get(0));
            int count = 0;
            ob.add("metas", meta.size());
            for (Metadata m : meta) {
                ob.add(count + "name", m.getName());
                ob.add(count + "value", m.getValue());
                count++;
            }
            JsonObject json = ob.build();
            return Response.ok(json).build();
        } catch (Exception e) {
            ob.add("metas", 0);
            JsonObject json = ob.build();
            return Response.ok(json).build();
        }
    }

    /**
     * Requests data from a sensor for a specific timespan
     *
     * @param entity sensor id, a start date, an end date, encrypt variable
     * (yes/no) and user id
     * @return timestamp and associated value either encrypted or plain
     */
    @POST
    @Path("/datarequest")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response datarequest(JsonObject entity) {
        JsonObjectBuilder ob = JsonProvider.provider().createObjectBuilder();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Long sid = Long.valueOf(entity.getString("sensorID"));
            Date from = sdf.parse(entity.getString("dateFrom"));
            Date to = sdf.parse(entity.getString("dateTo"));
            int encrypt = Integer.parseInt(entity.getString("encrypt"));
            int uid = Integer.parseInt(entity.getString("userID"));
            int motion = Integer.parseInt(entity.getString("motion"));
            List<Sensor> sensors = cont.getSensor(sid);
            if (sensors.isEmpty()) {
                throw new Exception("No sensor found");
            }
            List<Datapoint> data = getSdata(sensors.get(0), from, to, motion);
            if (!data.isEmpty()) {
                int count = 0;
                ob.add("datapoints", data.size());
                if (encrypt == 0) {
                    for (Datapoint d : data) {
                        ob.add(count + "value", d.getVal());
                        ob.add(count + "timestamp", String.valueOf(d.getTstamp()));
                        count++;
                    }
                } else {
                    for (Datapoint d : data) {
                        ob.add(count + "value", cont.hash(d.getVal(), uid));
                        ob.add(count + "timestamp", String.valueOf(d.getTstamp()));
                        count++;
                    }
                }
            } else {
                ob.add("datapoints", 0);
                System.out.println("Data null");
            }
            JsonObject json = ob.build();
            return Response.ok(json).build();
        } catch (Exception e) {
            ob.add("datapoints", 0);
            JsonObject json = ob.build();
            return Response.ok(json).build();
        }
    }

    private List<Datapoint> getSdata(Sensor s, Date from, Date to, int motion) throws Exception {
        List<Datapoint> data = cont.requestData(s, from, to);
        System.out.println("data " + data.size());
        List<Datapoint> motiondata = new ArrayList();
        if (motion == 1) {
            for (Datapoint d : data) {
                if (cont.presenceOnDp(d)) {
                    motiondata.add(d);
                }
            }
            data.clear();
            data = motiondata;
        }
        return data;
    }

    /**
     * Creates a downloadable file of the sensor data from the sensor and
     * timespan requested
     *
     * @param entity sensor id, a start date, an end date, encrypt variable
     * (yes/no) and user id
     * @return The path to the downloadable file containing the data either
     * encrypted or plain
     */
    @POST
    @Path("/datadl")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response downloadData(JsonObject entity) {
        JsonObjectBuilder ob = JsonProvider.provider().createObjectBuilder();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Long sid = Long.valueOf(entity.getString("sensorID"));
            Date from = sdf.parse(entity.getString("dateFrom"));
            Date to = sdf.parse(entity.getString("dateTo"));
            int encrypt = Integer.parseInt(entity.getString("encrypt"));
            int motion = Integer.parseInt(entity.getString("motion"));
            int uid = Integer.parseInt(entity.getString("userID"));
            List<Sensor> s = cont.getSensor(sid);
            if (s.isEmpty()) {
                throw new Exception("No sensor found");
            }
            List<Metadata> meta = cont.metaDataBySensor(s.get(0));
            List<Datapoint> data = getSdata(s.get(0), from, to, motion);
            if (!data.isEmpty()) {
                ob.add("datapoints", data.size());
                String path = cont.createfile(data, meta, Long.valueOf(uid), encrypt);
                ob.add("path", path);

            } else {
                ob.add("datapoints", 0);
                System.out.println("Data null");
            }
            JsonObject json = ob.build();
            return Response.ok(json).build();
        } catch (Exception e) {
            ob.add("datapoints", 0);
            JsonObject json = ob.build();
            e.printStackTrace();
            return Response.ok(json).build();
        }
    }

    /**
     * Recieves user information to register the user
     *
     * @param entity JsonObject containing user information
     * @return status on whether request was success and if successfull also
     * user id
     */
    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(JsonObject entity) {
        String name = entity.getString("fname");
        String password = entity.getString("password");
        String email = entity.getString("email");
        String lname = entity.getString("surname");
        JsonObjectBuilder ob = JsonProvider.provider().createObjectBuilder();

        User newuser = new User(name, lname, 1, email, password);
        if (cont.register(newuser)) {
            List<User> u = cont.login(email);
            ob.add("status", "success");
            ob.add("uid", u.get(0).getUid());
            ob.add("role", u.get(0).getPrivilege());
        } else {
            ob.add("status", "fail");
        }

        JsonObject json = ob.build();
        return Response.ok(json).build();
    }

    /**
     * Recieves an email and password to be authenticated
     *
     * @param entity email and password for a user
     * @return success/fail status and user id if login was successful
     */
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(JsonObject entity) {
        String password = entity.getString("password");
        String email = entity.getString("email");
        JsonObjectBuilder ob = JsonProvider.provider().createObjectBuilder();

        List<User> u = cont.login(email);
        if (!u.isEmpty() && u.get(0).authenticate(password)) {
            System.out.println("Successful");
            ob.add("status", "success");
            ob.add("uid", u.get(0).getUid());
            ob.add("role", u.get(0).getPrivilege());
        } else {
            System.out.println("Fail");
            ob.add("status", "fail");
        }
        JsonObject json = ob.build();
        return Response.ok(json).build();
    }

    /**
     * Recieves name and address of new testbed to add it to the database
     *
     * @param entity name and address for sensor
     * @return either success or fail depending on if adding was a success
     */
    @POST
    @Path("/addTestbed")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTestbed(JsonObject entity) {
        String name = entity.getString("name");
        String address = entity.getString("address");
        JsonObjectBuilder ob = JsonProvider.provider().createObjectBuilder();
        Testbed tb = new Testbed(name, address);
        if (cont.newTestbed(tb)) {
            ob.add("status", "success");
        } else {
            System.out.println("Fail");
            ob.add("status", "fail");
        }
        JsonObject json = ob.build();
        return Response.ok(json).build();
    }

    /**
     * Recieves sensor information and tries to enter new sensor into database
     *
     * @param entity Sensor name and unit of measure
     * @return Success/fail depending on whether adding was successful
     */
    @POST
    @Path("/addSensor")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addSensor(JsonObject entity) {
        JsonObjectBuilder ob = JsonProvider.provider().createObjectBuilder();
        String name = entity.getString("name");
        String unit = entity.getString("unit");
        Long tid = Long.valueOf(entity.getString("testbedID"));
        List<Testbed> l = cont.getTbByTid(tid);
        if (l.isEmpty()) {
            ob.add("status", "fail");
            JsonObject json = ob.build();
            return Response.ok(json).build();
        } else {
            Sensor s = new Sensor(name, unit, l.get(0));
            if (cont.newSensor(s, l.get(0))) {
                ob.add("status", "success");
            } else {
                System.out.println("Fail");
                ob.add("status", "fail");
            }

        }

        JsonObject json = ob.build();
        return Response.ok(json).build();
    }

    /**
     * Retrieves metadata for a sensor and requests to add it to database
     *
     * @param entity name of metadata, value and sensor id for which the
     * metadata represents
     * @return
     */
    @POST
    @Path("/addMetadata")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addMetadata(JsonObject entity) {
        String name = entity.getString("name");
        String value = entity.getString("value");
        Long sid = Long.valueOf(entity.getString("sensorID"));
        JsonObjectBuilder ob = JsonProvider.provider().createObjectBuilder();
        List<Sensor> s = cont.getSensor(sid);

        if (!s.isEmpty()) {
            Metadata m = new Metadata(s.get(0), name, value);
            if (cont.newMetadata(m)) {
                ob.add("status", "success");
            } else {
                ob.add("status", "fail");
            }
        } else {
            ob.add("status", "fail");
        }

        JsonObject json = ob.build();
        return Response.ok(json).build();
    }

    /**
     * PUT method for updating or creating an instance of Net
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.TEXT_HTML)
    public void putHtml(String content) {
    }
}
