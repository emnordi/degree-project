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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Entity representing a user
 * @author Emil
 */
@Entity
@Table(name = "Researcher", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"EMAIL"})})
@NamedQueries({
    @NamedQuery(name = "User.findAll", query = "SELECT u FROM User u")
    ,@NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email")})
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RESEARCHER_ID", nullable = false)
    private long uid;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "EMAIL", nullable = false, length = 255)
    private String email;

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "NAME", nullable = false, length = 255)
    private String name;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "SURNAME", nullable = false, length = 255)
    private String surname;
    @Basic(optional = false)
    @NotNull
    @Column(name = "PRIVILEGE", nullable = false)
    private int privilege;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "PASSWORD", nullable = false, length = 255)
    private String password;

    /**
     * Class Constructor
     */
    public User() {
    }

    /**
     * Constructor taking user id
     *
     * @param uid to set the USER_ID
     */
    public User(Long uid) {
        this.uid = uid;
    }

    /**
     * Class constructor
     *
     * @param name user first name
     * @param surname user surname
     * @param privilege level of privilege
     * @param email user email address
     * @param password user password
     */
    public User(String name, String surname, int privilege, String email, String password) {
        this.name = name;
        this.surname = surname;
        this.privilege = privilege;
        this.email = email;
        this.password = password;
    }

    /**
     * Get user id
     *
     * @return user id
     */
    public long getUid() {
        return uid;
    }

    /**
     * Set user id
     *
     * @param uid user id
     */
    public void setUid(long uid) {
        this.uid = uid;
    }

    /**
     * Get user first name
     *
     * @return user first name
     */
    public String getName() {
        return name;
    }

    /**
     * Set user first name
     *
     * @param name user first name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get user surname
     *
     * @return user surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Set user surname
     *
     * @param surname user surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Get user privilege level
     *
     * @return privilege level
     */
    public int getPrivilege() {
        return privilege;
    }

    /**
     * Set user privilege level
     *
     * @param privilege level of privilege
     */
    public void setPrivilege(int privilege) {
        this.privilege = privilege;
    }

    /**
     * Get user email address
     *
     * @return user email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set user email address
     *
     * @param email user email address
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Get user password
     *
     * @return user password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Set user password
     *
     * @param password user password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Compared password to user's password
     *
     * @param pass password for comparison
     * @return true/false if password correct/incorrect
     */
    public boolean authenticate(String pass) {
        return this.password.equals(pass);
    }

}
