/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Entity representing a sensor
 * @author Emil
 */
@Entity
@Table(name = "Sensor")
@NamedQueries({
    @NamedQuery(name = "Sensor.findAll", query = "SELECT s FROM Sensor s")
    ,@NamedQuery(name = "Sensor.findBySid", query = "SELECT s FROM Sensor s WHERE s.sid = :sid")
    ,@NamedQuery(name = "Sensor.findByNameAndTestbed", query = "SELECT s FROM Sensor s WHERE s.name = :name AND s.tid = :tid")})
public class Sensor implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sid")
    private List<Datapoint> datapointList;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sid")
    private List<Metadata> metadataList;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SENSOR_ID", nullable = false)
    private long sid;

    @NotNull
    @JoinColumn(name = "TESTBED_ID", referencedColumnName = "TESTBED_ID", nullable = false)
    @ManyToOne(optional = false)
    private Testbed tid;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "NAME", nullable = false, length = 255)
    private String name;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "UNIT", nullable = false, length = 255)
    private String unit;
    /**
     * Constructor
     */
    public Sensor() {
    }
    /**
     * Constructor
     * @param sid sensor id
     */
    public Sensor(Long sid) {
        this.sid = sid;
    }
    /**
     * Constructor
     * @param name name
     * @param unit unit
     * @param tid testbed
     */
    public Sensor(String name, String unit, Testbed tid) {
        this.name = name;
        this.unit = unit;
        this.tid = tid;
    }

    public List<Datapoint> getDatapointList() {
        return datapointList;
    }

    public void setDatapointList(List<Datapoint> datapointList) {
        this.datapointList = datapointList;
    }

    public long getSid() {
        return sid;
    }

    public void setSid(long sid) {
        this.sid = sid;
    }

    public Testbed getTid() {
        return tid;
    }

    public void setTid(Testbed tid) {
        this.tid = tid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

}
