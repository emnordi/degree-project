/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.controller;

import server.model.Createfile;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import server.integration.SenserDAO;
import server.model.User;
import javax.ejb.EJB;
import server.model.Datapoint;
import server.model.Encrypter;
import server.model.Metadata;
import server.model.Presence;
import server.model.Sensor;
import server.model.Testbed;
import server.model.XMLFileReader;

/**
 * Forwards requests to the correct layers
 *
 * @author Emil
 */
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Stateless
public class Controller {

    @EJB
    private SenserDAO sdao;

    @EJB
    private XMLFileReader fr;

    @EJB
    private Encrypter enc;

    @EJB
    private Createfile crt;

    public boolean test() {
        User testuser = new User("Emil", "Nordin", 1, "emnordi@kth.se", "emil");
        //return sdao.registerUser(testuser);
        //System.out.println("gonna fileread");
        //fr.fileread();
        //System.out.println("done");
        return true;
    }

    /**
     * Forwards request to create file
     *
     * @param l list of datapoints
     * @param met list of metadata
     * @param uid user id
     * @param enc encryption variable
     * @return Path to created file
     */
    public String createfile(List<Datapoint> l, List<Metadata> met, Long uid, int enc) {
        return crt.filemake(l, met, uid, enc);
    }

    /**
     * Forwards request to register user
     *
     * @param user new user
     * @return boolean with true if success and false if not
     */
    public boolean register(User user) {
        return sdao.registerUser(user);
    }

    /**
     * Fetch user from email
     *
     * @param email email address for user
     * @return List containing users with email address (will only be one
     * because email is unique)
     */
    public List<User> login(String email) {
        return sdao.findWithEmail(email);
    }

    /**
     * Forwards request for data from specific sensor in specific interval
     *
     * @param s Sensor to get data from
     * @param from startdate
     * @param to enddate
     * @return List of datapoints from specific sensor in interval
     */
    public List<Datapoint> requestData(Sensor s, Date from, Date to) {
        return sdao.getDataByTimeAndSid(from, to, s);
    }

    /**
     * Forward request for sensor by sensor id
     *
     * @param sid sensor id
     * @return List containing sensor with sensor id
     */
    public List<Sensor> getSensor(Long sid) {
        return sdao.getSensorBySid(sid);
    }

    /**
     * Forwards request to encrypt a value with user id as salt
     *
     * @param val value to encrypt
     * @param uid user id
     * @return encrypted value as string
     */
    public String hash(float val, int uid) {
        return enc.md5(val, uid);
    }

    /**
     * Forwards request to add new testbed
     *
     * @param tb new testbed
     * @return true/false based on success/fail of request
     */
    public boolean newTestbed(Testbed tb) {
        return sdao.addTestbed(tb);
    }

    /**
     * Forwards request to add new sensor
     *
     * @param s new sensor object
     * @param tb testbed object which new sensor will be associated to
     * @return true/false on success/fail
     */
    public boolean newSensor(Sensor s, Testbed tb) {
        return sdao.addSensor(s, tb);
    }

    /**
     * Forwards request to add metadata
     *
     * @param m new metadata object
     * @return true if success false if fail
     */
    public boolean newMetadata(Metadata m) {
        return sdao.addMetadata(m);
    }

    /**
     * Forwards request to retrieve testbed by testbed id
     *
     * @param tid testbed id
     * @return List containing testbed
     */
    public List<Testbed> getTbByTid(Long tid) {
        return sdao.getTestbedByTid(tid);
    }

    /**
     * Test method
     */
    public void addTestbed() {
        List<Sensor> sensorlist = new ArrayList();
        List<Sensor> sensorlist2 = new ArrayList();
        Testbed a = new Testbed("Flat A", "KTH Campus");
        Testbed b = new Testbed("Flat B", "KTH Campus");
        Testbed c = new Testbed("Flat C", "KTH Campus");
        Testbed d = new Testbed("Flat D", "KTH Campus");
        sdao.addTestbed(a);
        sdao.addTestbed(b);
        sdao.addTestbed(c);
        sdao.addTestbed(d);
        List<Testbed> tb = sdao.getTestbedsByNameAddress(a);
        List<Testbed> tbb = sdao.getTestbedsByNameAddress(b);
        List<Testbed> tbc = sdao.getTestbedsByNameAddress(c);
        List<Testbed> tbd = sdao.getTestbedsByNameAddress(d);
        a = tb.get(0);
        b = tbb.get(0);
        c = tbc.get(0);
        d = tbd.get(0);
        sensorlist.add(new Sensor("GX11 Co2", "ppm", a));
        sensorlist.add(new Sensor("GX11 Humidity", "Percent", a));
        sensorlist.add(new Sensor("GX43 Co2", "ppm", a));
        sensorlist.add(new Sensor("GX43 Humidity", "Percent", a));
        sensorlist.add(new Sensor("GX44 Co2", "ppm", a));
        sensorlist.add(new Sensor("GX44 Humidity", "Percent", a));
        sdao.addTbS(a, b, c, d, sensorlist);
        for (Sensor sen : sensorlist) {
            Sensor temp = sdao.getSensorByTidAndName(sen.getName(), sen.getTid()).get(0);
            Metadata m = new Metadata(temp, "Measuring method", "A good one");
            sensorlist2.add(temp);
            sdao.addMetadata(m);
        }
        List<Datapoint> l = fr.fileread(sensorlist2);
        List<Presence> pres = fr.motionSenser(a);
        for (Datapoint dp : l) {
            sdao.addDatapoint(dp);
        }
        for (Presence presence : pres) {
            sdao.addPresence(presence);
        }
        System.out.println("Added all " + l.size());
    }

    public void teste() {
        Testbed a = new Testbed("Flat A", "KTH Campus");
        List<Testbed> tb = sdao.getTestbedsByNameAddress(a);
        List<Sensor> l = tb.get(0).getSensorList();
        for (Sensor s : l) {
            System.out.println(s.getName());
        }
    }

    /**
     * Get presence for a datapoint
     *
     * @param d datapoint
     * @return true/false if exists result
     */
    public boolean presenceOnDp(Datapoint d) {
        return !sdao.getPresenceForDp(d).isEmpty();
    }

    /**
     * Forwards request for all the testbeds
     *
     * @return List of all the testbeds
     */
    public List<Testbed> allTestbeds() {
        return sdao.getAllTestbeds();
    }

    /**
     * Retrieves metadata for sensor
     *
     * @param sid sensor
     * @return list of metadata
     */
    public List<Metadata> metaDataBySensor(Sensor sid) {
        return sdao.getMetadataBySensor(sid);
    }

    /**
     * Forwards request for retrieving all the sensors
     *
     * @return List of all the sensors
     */
    public List<Sensor> allSensors() {
        return sdao.getAllSensors();
    }
}
