/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 * Entity representing a datapoint
 * @author Emil
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Datapoint.findAll", query = "SELECT d FROM Datapoint d")
    ,@NamedQuery(name = "Datapoint.findById", query = "SELECT d FROM Datapoint d WHERE d.did = :did")
    ,@NamedQuery(name = "Datapoint.findBySensor", query = "SELECT d FROM Datapoint d WHERE d.tstamp = :timestamp"
            + " AND d.sid = :sid")
    ,@NamedQuery(name = "Datapoint.findByTimeAndSensor", query = "SELECT d FROM Datapoint d "
            + "WHERE (d.tstamp >= :from AND d.tstamp <= :to) AND d.sid = :sid")})
public class Datapoint implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DATAPOINT_ID", nullable = false)
    private long did;
    
    @NotNull
    @JoinColumn(name = "SENSOR_ID", referencedColumnName = "SENSOR_ID", nullable = false)
    @ManyToOne(optional = false)
    private Sensor sid;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "VAL", nullable = false, length = 255)
    private float val;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "TSTAMP", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date tstamp;
    
    /**
     * Constructor
     */
    public Datapoint(){}
    /**
     * Constructor
     * @param did datapoint id
     */
    public Datapoint(Long did){
        this.did = did;
    }
    /**
     * Constructor
     * @param sid sensor
     * @param val value
     * @param tstamp timestamp
     */
    public Datapoint(Sensor sid, float val, Date tstamp){
        this.sid = sid;
        this.val = val;
        this.tstamp = tstamp;
    }
    public long getDid() {
        return did;
    }

    public void setDid(long did) {
        this.did = did;
    }

    public Sensor getSid() {
        return sid;
    }

    public void setSid(Sensor sid) {
        this.sid = sid;
    }

    public float getVal() {
        return val;
    }

    public void setVal(float val) {
        this.val = val;
    }

    public Date getTstamp() {
        return tstamp;
    }

    public void setTstamp(Date tstamp) {
        this.tstamp = tstamp;
    }
    
    
    
    
}
