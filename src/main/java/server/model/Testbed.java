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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Represents a testbed
 * @author Emil
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Testbed.findAll", query = "SELECT t FROM Testbed t")
    ,@NamedQuery(name = "Testbed.findByTid", query = "SELECT t FROM Testbed t WHERE t.tid = :tid")
    ,@NamedQuery(name = "Testbed.findByNameAndAddress", query = "SELECT t FROM Testbed t WHERE t.name = :name AND t.address = :address")})
public class Testbed implements Serializable {
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tid")
    private List<Sensor> sensorList;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TESTBED_ID", nullable = false)
    private long tid;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "NAME", nullable = false, length = 255)
    private String name;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "ADDRESS", nullable = false, length = 255)
    private String address;
    /**
     * Constructor
     */
    public Testbed() {
    }
    /**
     * Constructor
     * @param tid testbed id
     */
    public Testbed(Long tid) {
        this.tid = tid;
    }
    /**
     * Constructor
     * @param name name of testbed
     * @param address address of testbed
     */
    public Testbed(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public List<Sensor> getSensorList() {
        return sensorList;
    }

    public void setSensorList(List<Sensor> sensorList) {
        this.sensorList = sensorList;
    }

    public long getTid() {
        return tid;
    }

    public void setTid(long tid) {
        this.tid = tid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
