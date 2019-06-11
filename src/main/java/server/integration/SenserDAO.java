package server.integration;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import server.model.Datapoint;
import server.model.Metadata;
import server.model.Presence;
import server.model.Sensor;
import server.model.Testbed;
import server.model.User;

/**
 * DAO for database access
 *
 * @author Emil
 */
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Stateless
public class SenserDAO {

    @PersistenceContext(unitName = "senserPU")
    private EntityManager em;

    /**
     * Enter new user into database
     *
     * @param testuser new user
     * @return True if user registered successfully otherwise false
     */
    public boolean registerUser(User testuser) {
        List<User> u = findWithEmail(testuser.getEmail());
        if (!u.isEmpty()) {
            return false;
        } else {
            em.persist(testuser);
            return true;
        }
    }

    /**
     * Adds new Testbed to database
     *
     * @param tb new testbed
     * @return true if successfully added false if not
     */
    public boolean addTestbed(Testbed tb) {
        if (!getTestbedsByNameAddress(tb).isEmpty()) {
            System.out.println("testbed FOUND!");
            return false;
        } else {
            System.out.println("NOT FOUND! - Adding testbed");
            em.persist(tb);
            return true;
        }
    }

    /**
     * Adds sensor to database
     *
     * @param s new sensor
     * @param tb testbed to add sensor to
     * @return true if added false if not
     */
    public boolean addSensor(Sensor s, Testbed tb) {
        if (!getSensorByTidAndName(s.getName(), tb).isEmpty()) {
            return false;
        } else {
            em.persist(s);
            return true;
        }
    }
    
    /**
     * Adds new Metadata to database
     *
     * @param m new Metadata
     * @return true if added false if not
     */
    public boolean addMetadata(Metadata m) {
        if (getMetadataByName(m)) {
            return false;
        } else {
            em.persist(m);
            return true;
        }
    }
    /**
     * See if metadata already exists
     * @param m new metadata
     * @return true/false if exist or not
     */
    public boolean getMetadataByName(Metadata m) {
        Map<String, Object> param = new HashMap<>();
        param.put("sid", m.getSid());
        param.put("name", m.getName());
        return !getQuery(Metadata.class, "findBySensorAndName", param).getResultList().isEmpty();
    }
    
    /**
     * See if metadata already exists
     * @param sid sensor id
     * @return list of metadata from sensor
     */
    public List<Metadata> getMetadataBySensor(Sensor sid) {
        Map<String, Object> param = new HashMap<>();
        param.put("sid", sid);
        return getQuery(Metadata.class, "findBySensor", param).getResultList();
    }
    
    public boolean addPresence(Presence p){
        if(!getPresenceByTimes(p).isEmpty()){
            return false;
        }else{
            em.persist(p);
            return true;
        }
    }
    /**
     * Check if someone is present at the time of a datapoint
     * @param p new presence
     * @return any Presence at the given times
     */
    public List<Presence> getPresenceByTimes(Presence p){
        Map<String, Object> param = new HashMap<>();
        param.put("tfrom", p.getTfrom());
        param.put("tto", p.getTto());
        return getQuery(Presence.class, "findByTimes", param).getResultList();
    }
    
    /**
     * Check if someone is present at the time of a datapoint
     * @param d datapoint
     * @return any Presence at that current time
     */
    public List<Presence> getPresenceForDp(Datapoint d){
        Map<String, Object> param = new HashMap<>();
        param.put("tstamp", d.getTstamp());
        param.put("tid", d.getSid().getTid());
        return getQuery(Presence.class, "findByTime", param).getResultList();
    }
    /**
     * Add new datapoint into database if not already there
     *
     * @param da new datapoint
     * @return true if added false if not
     */
    public boolean addDatapoint(Datapoint da) {
        List<Sensor> s = getSensorBySid(da.getSid().getSid());
        if (!s.isEmpty() && findDatapointBySensorAndTime(s.get(0), da.getTstamp())) {
            return true;
        } else if (s.isEmpty()) {
            return true;
        } else {
            em.persist(da);
            return false;
        }

    }

    /**
     * Retrieve users(unique so only one) with certain email address
     *
     * @param email user email address
     * @return Returns list of users found
     */
    public List<User> findWithEmail(String email) {
        Map<String, Object> param = new HashMap<>();
        param.put("email", email);
        return getQuery(User.class, "findByEmail", param).getResultList();
    }

    /**
     * Checks if testbed with name and address is already in database
     *
     * @param t new testbed
     * @return List of testbeds with that name and address
     */
    public List<Testbed> getTestbedsByNameAddress(Testbed t) {
        Map<String, Object> param = new HashMap<>();
        param.put("name", t.getName());
        param.put("address", t.getAddress());
        return getQuery(Testbed.class, "findByNameAndAddress", param).getResultList();
    }

    /**
     * Retrieves a testbed from id
     *
     * @param tid testbed id
     * @return List containing one or no testbeds
     */
    public List<Testbed> getTestbedByTid(Long tid) {
        Map<String, Object> param = new HashMap<>();
        param.put("tid", tid);
        return getQuery(Testbed.class, "findByTid", param).getResultList();
    }

    /**
     * Retrieve all testbeds
     *
     * @return List of all testbeds
     */
    public List<Testbed> getAllTestbeds() {
        Map<String, Object> param = new HashMap<>();
        return getQuery(Testbed.class, "findAll", param).getResultList();
    }

    /**
     * Retrieve all sensors
     *
     * @return list of all sensors
     */
    public List<Sensor> getAllSensors() {
        Map<String, Object> param = new HashMap<>();
        return getQuery(Sensor.class, "findAll", param).getResultList();
    }

    /**
     * Checks if there are any sensors with given testbed and name
     *
     * @param name name of sensor
     * @param tid testbed
     * @return true if none found false if found
     */
    public List<Sensor> getSensorByTidAndName(String name, Testbed tid) {
        Map<String, Object> param = new HashMap<>();
        param.put("tid", tid);
        param.put("name", name);
        return getQuery(Sensor.class, "findByNameAndTestbed", param).getResultList();
    }

    /**
     * Retrieve sensor by sensor id
     *
     * @param sid sensor id
     * @return List containing the one sensor with that id or empty list
     */
    public List<Sensor> getSensorBySid(Long sid) {
        Map<String, Object> param = new HashMap<>();
        param.put("sid", sid);
        return getQuery(Sensor.class, "findBySid", param).getResultList();
    }

    /**
     * Find datapoint fiven a sensor and time
     *
     * @param sid sensor
     * @param timestamp time
     * @return false if found true if not found
     */
    private boolean findDatapointBySensorAndTime(Sensor sid, Date timestamp) {
        Map<String, Object> param = new HashMap<>();
        param.put("sid", sid);
        param.put("timestamp", timestamp);
        return !getQuery(Datapoint.class, "findBySensor", param).getResultList().isEmpty();
    }


    /**
     * Retrieve data points within two dates from specific sensor
     *
     * @param from date from
     * @param to date to
     * @param sid sensor
     * @return List of datapoints within certain timespan
     */
    public List<Datapoint> getDataByTimeAndSid(Date from, Date to, Sensor sid) {
        Map<String, Object> param = new HashMap<>();
        param.put("sid", sid);
        param.put("from", from);
        param.put("to", to);
        return getQuery(Datapoint.class, "findByTimeAndSensor", param).getResultList();
    }

    /**
     * Retrieve metadata with given id
     *
     * @param mid metadata id
     * @return List of the metadata with given id
     */
    public List<Metadata> getDataByMid(Long mid) {
        Map<String, Object> param = new HashMap<>();
        param.put("mid", mid);
        return getQuery(Metadata.class, "findByMid", param).getResultList();
    }

    //Add testbeds and sensors
    public void addTbS(Testbed a, Testbed b, Testbed c, Testbed d, List<Sensor> ls) {

        //Lägenhet A
        //GX11 livingroom, GX43 kitchen, GX44 bathroom
        Sensor sa1 = new Sensor("GX11 Temp", "C", a);
        Sensor sa4 = new Sensor("GX43 Temp", "C", a);
        Sensor sa7 = new Sensor("GX44 Temp", "C", a);

        addSensor(sa1, a);

        addSensor(sa4, a);
        addSensor(sa7, a);
        for (Sensor s : ls) {
            addSensor(s, a);
        }

        //Lägenhet B
        Sensor sb1 = new Sensor("GX11 Temp", "C", b);
        Sensor sb2 = new Sensor("GX11 Co2", "ppm", b);
        Sensor sb3 = new Sensor("GX11 Humidity", "Percent", b);
        Sensor sb4 = new Sensor("GX43 Temp", "C", b);
        Sensor sb5 = new Sensor("GX43 Co2", "ppm", b);
        Sensor sb6 = new Sensor("GX43 Humidity", "Percent", b);
        Sensor sb7 = new Sensor("GX44 Temp", "C", b);
        Sensor sb8 = new Sensor("GX44 Co2", "ppm", b);
        Sensor sb9 = new Sensor("GX44 Humidity", "Percent", b);
        addSensor(sb1, b);
        addSensor(sb2, b);
        addSensor(sb3, b);
        addSensor(sb4, b);
        addSensor(sb5, b);
        addSensor(sb6, b);
        addSensor(sb7, b);
        addSensor(sb8, b);
        addSensor(sb9, b);

        //Lägenhet C
        Sensor sc1 = new Sensor("GX11 Temp", "C", c);
        Sensor sc2 = new Sensor("GX11 Co2", "ppm", c);
        Sensor sc3 = new Sensor("GX11 Humidity", "Percent", c);
        Sensor sc4 = new Sensor("GX43 Temp", "C", c);
        Sensor sc5 = new Sensor("GX43 Co2", "ppm", c);
        Sensor sc6 = new Sensor("GX43 Humidity", "Percent", c);
        Sensor sc7 = new Sensor("GX44 Temp", "C", c);
        Sensor sc8 = new Sensor("GX44 Co2", "ppm", c);
        Sensor sc9 = new Sensor("GX44 Humidity", "Percent", c);
        addSensor(sc1, c);
        addSensor(sc2, c);
        addSensor(sc3, c);
        addSensor(sc4, c);
        addSensor(sc5, c);
        addSensor(sc6, c);
        addSensor(sc7, c);
        addSensor(sc8, c);
        addSensor(sc9, c);

        //Lägenhet D
        Sensor sd1 = new Sensor("GX11 Temp", "C", d);
        Sensor sd2 = new Sensor("GX11 Co2", "ppm", d);
        Sensor sd3 = new Sensor("GX11 Humidity", "Percent", d);
        Sensor sd4 = new Sensor("GX43 Temp", "C", d);
        Sensor sd5 = new Sensor("GX43 Co2", "ppm", d);
        Sensor sd6 = new Sensor("GX43 Humidity", "Percent", d);
        Sensor sd7 = new Sensor("GX44 Temp", "C", d);
        Sensor sd8 = new Sensor("GX44 Co2", "ppm", d);
        Sensor sd9 = new Sensor("GX44 Humidity", "Percent", d);
        addSensor(sd1, d);
        addSensor(sd2, d);
        addSensor(sd3, d);
        addSensor(sd4, d);
        addSensor(sd5, d);
        addSensor(sd6, d);
        addSensor(sd7, d);
        addSensor(sd8, d);
        addSensor(sd9, d);
    }

    /**
     * Gets query by name from a class
     *
     * @param <T> class
     * @param entity entity class
     * @param query name of query
     * @param parameters parameters to add to query
     * @return Query
     */
    private <T> TypedQuery<T> getQuery(Class<T> entity, String query, Map<String, Object> parameters) {
        try {
            String entityType = entity.getSimpleName();
            String fquery = entityType + "." + query;
            TypedQuery<T> namedQuery = em.createNamedQuery(fquery, entity);

            for (Map.Entry<String, Object> entry : parameters.entrySet()) {
                namedQuery.setParameter(entry.getKey(), entry.getValue());
            }
            return namedQuery;
        } catch (Exception ex) {
            return null;
        }
    }
}
