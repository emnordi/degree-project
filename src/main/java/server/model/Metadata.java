/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Entity representing metadata for a sensor
 * @author Emil
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Metadata.findAll", query = "SELECT m FROM Metadata m")
    ,@NamedQuery(name = "Metadata.findByMid", query = "SELECT m FROM Metadata m where m.mid = :mid")
    ,@NamedQuery(name = "Metadata.findBySensor", query = "SELECT m FROM Metadata m WHERE m.sid = :sid")
    ,@NamedQuery(name = "Metadata.findBySensorAndName", query = "SELECT m FROM Metadata m WHERE m.sid = :sid AND m.name = :name")
})
public class Metadata implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "METADATA_ID", nullable = false)
    private long mid;
    
    @NotNull
    @JoinColumn(name = "SENSOR_ID", referencedColumnName = "SENSOR_ID", nullable = false)
    @ManyToOne(optional = false)
    private Sensor sid;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "NAME", nullable = false, length = 255)
    private String name;
    
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "VALUE", nullable = false, length = 255)
    private String value;
    /**
     * Constructor
     */
    public Metadata(){
    }
    /**
     * Constructor
     * @param mid metadata id
     */
    public Metadata(Long mid){
        this.mid = mid;
    }
    /**
     * Constructor
     * @param s sensor
     * @param name name
     * @param value value
     */
    public Metadata(Sensor s, String name, String value){
        this.sid = s;
        this.name = name;
        this.value = value;
    }
    
    public long getMid() {
        return mid;
    }

    public void setMid(long mid) {
        this.mid = mid;
    }

    public Sensor getSid() {
        return sid;
    }

    public void setSid(Sensor sid) {
        this.sid = sid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
    
    
}
