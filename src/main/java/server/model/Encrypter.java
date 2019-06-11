/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.ejb.Stateless;
import javax.xml.bind.DatatypeConverter;

/**
 * Encrypts values
 * @author Emil
 */
@Stateless
public class Encrypter {
    /**
     * Hash value using salt
     * @param val value to hash
     * @param salt salt for hash
     * @return hashed value
     */
    public String md5(float val, int salt) {
        float value = val + salt;
        String toEncrypt = String.valueOf(value);
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
            md.update(toEncrypt.getBytes());
            byte[] digest = md.digest();
            String hash = DatatypeConverter
                    .printHexBinary(digest).toUpperCase();
            return hash;
        } catch (NoSuchAlgorithmException ex) {
            return "";
        }
    }

}
