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
 * Entity representing time of subject presence
 * @author Emil
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Presence.findAll", query = "SELECT p FROM Presence p")
    ,@NamedQuery(name = "Presence.findByPid", query = "SELECT p FROM Presence p where p.pid = :pid")
    ,@NamedQuery(name = "Presence.findByTime", query = "SELECT p FROM Presence p WHERE (:tstamp BETWEEN p.tfrom AND p.tto) AND p.tid = :tid")
    ,@NamedQuery(name = "Presence.findByTimes", query = "SELECT p FROM Presence p WHERE p.tfrom = :tfrom AND p.tto = :tto")
    })
public class Presence implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PRESENCE_ID", nullable = false)
    private long pid;
    
    @NotNull
    @JoinColumn(name = "TESTBED_ID", referencedColumnName = "TESTBED_ID", nullable = false)
    @ManyToOne(optional = false)
    private Testbed tid;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "TFROM", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date tfrom;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "TTO", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date tto;
    /**
     * Constructor
     */
    public Presence(){    }
    /**
     * Constructor
     * @param pid presence id
     */
    public Presence(Long pid){
        this.pid = pid;
    }
    /**
     * Constructor
     * @param tid testbed
     * @param tfrom timestamp
     * @param tto timestamp
     */
    public Presence(Testbed tid, Date tfrom, Date tto){
        this.tid = tid;
        this.tfrom = tfrom;
        this.tto = tto;
    }
    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public Testbed getTid() {
        return tid;
    }

    public void setTid(Testbed tid) {
        this.tid = tid;
    }

    public Date getTfrom() {
        return tfrom;
    }

    public void setTfrom(Date tfrom) {
        this.tfrom = tfrom;
    }

    public Date getTto() {
        return tto;
    }

    public void setTto(Date tto) {
        this.tto = tto;
    }
    
}
