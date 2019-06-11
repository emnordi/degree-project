/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model;

import java.io.PrintWriter;
import java.util.List;
import java.util.Random;
import javax.ejb.Stateless;

/**
 * Creates files
 * @author Emil
 */
@Stateless
public class Createfile {

    private Encrypter en = new Encrypter();
    /**
     * Creates a file containing datapoints
     * @param l list of datapoints
     * @param uid user id
     * @param encrypt tell if encrypt or not
     * @return path to file
     */
    public String filemake(List<Datapoint> l, List<Metadata> meta, Long uid, int encrypt) {
        String basepath = "C:/Users/Emil/Desktop/";
        Random r = new Random(uid);
        String path = String.valueOf(r.nextInt());
        String filetype = ".txt";
        String all = basepath + path + filetype;
        try {
            PrintWriter writer = new PrintWriter(all, "UTF-8");
            writer.println("Sensor ID: " + l.get(0).getSid().getSid());
            writer.println("METADATA:");
            writer.println("NAME, VALUE");
            for(Metadata m : meta){
                writer.println(m.getName() + " , " + m.getValue());
            }
            writer.println("VALUE, TIMESTAMP");

            if (encrypt == 0) {
                for (Datapoint d : l) {
                    writer.println(d.getVal() + " , " + d.getTstamp());
                }
            } else {
                for (Datapoint d : l) {
                    writer.println(en.md5(d.getVal(), uid.intValue()) + " , " + d.getTstamp());
                }
            }
            writer.close();
            return all;
        } catch (Exception e) {
            return "";
        }
    }
}
