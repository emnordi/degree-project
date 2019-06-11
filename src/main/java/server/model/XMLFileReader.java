/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Emil
 */
@Stateless
public class XMLFileReader {

    /**
     * Reads XML file and creates list of values and timestamps
     *
     * @param sensorlist list of sensors which the values correspond to
     * @return List of datapoints from xml file
     */
    public List<Datapoint> fileread(List<Sensor> sensorlist) {
        try {
            List<Datapoint> dps = new ArrayList();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            Sensor gx11h = sensorlist.get(0);
            Sensor gx43h = sensorlist.get(1);
            Sensor gx44h = sensorlist.get(2);
            Sensor gx11c = sensorlist.get(3);
            Sensor gx43c = sensorlist.get(4);
            Sensor gx44c = sensorlist.get(5);

            File fXmlFile = new File("C:/Users/Emil/Desktop/lco.xml");
            if (fXmlFile.isFile()) {
                System.out.println("Found xml");
            }
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("MultiTrendLogValue");

            for (int temp = 0; temp < 100; temp++) {

                Datapoint d1, d2, d3, d4, d5, d6;
                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;
                    Timestamp t = Timestamp.valueOf(eElement.getAttribute("Timestamp"));

                    //humid gx44 - bathroom
                    d1 = new Datapoint(gx44h,
                            Float.parseFloat(eElement.getAttribute("Serie1").replace("\u00A0", "").replace(',', '.') + "f"),
                            sdf.parse(eElement.getAttribute("Timestamp")));

                    //humid gx43 - kitchen
                    d2 = new Datapoint(gx43h,
                            Float.parseFloat(eElement.getAttribute("Serie2").replace("\u00A0", "").replace(',', '.') + "f"),
                            sdf.parse(eElement.getAttribute("Timestamp")));
                    //humid gx11 - livingroom
                    d3 = new Datapoint(gx11h,
                            Float.parseFloat(eElement.getAttribute("Serie3").replace("\u00A0", "").replace(',', '.') + "f"),
                            sdf.parse(eElement.getAttribute("Timestamp")));
                    //co2 gx11 livingroom
                    d4 = new Datapoint(gx11c,
                            Float.parseFloat(eElement.getAttribute("Serie4").replace("\u00A0", "").replace(',', '.') + "f"),
                            sdf.parse(eElement.getAttribute("Timestamp")));
                    //co2 gx43 kitchen
                    d5 = new Datapoint(gx43c,
                            Float.parseFloat(eElement.getAttribute("Serie5").replace("\u00A0", "").replace(',', '.') + "f"),
                            sdf.parse(eElement.getAttribute("Timestamp")));
                    //co2 gx44 bathroom
                    d6 = new Datapoint(gx44c,
                            Float.parseFloat(eElement.getAttribute("Serie6").replace("\u00A0", "").replace(',', '.') + "f"),
                            sdf.parse(eElement.getAttribute("Timestamp")));

                    dps.add(d1);
                    dps.add(d2);
                    dps.add(d3);
                    dps.add(d4);
                    dps.add(d5);
                    dps.add(d6);
                }
            }
            return dps;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves data on whenever someone is in testbed
     *
     * @param t Testbed where motion is detected from
     * @return List of motion sensor data
     */
    public List<Presence> motionSenser(Testbed t) {
        try {
            List<Presence> pre = new ArrayList();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            File fXmlFile = new File("C:/Users/Emil/Desktop/presence.xml");
            if (fXmlFile.isFile()) {
                System.out.println("Found xml");
            }
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("MultiTrendLogValue");
            String from = "";
            String to = "";
            for (int temp = 0; temp < 1000; temp++) {
                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    
                    if (eElement.getAttribute("Serie3").equals("1,00")
                            || eElement.getAttribute("Serie4").equals("1,00")
                            || eElement.getAttribute("Serie5").equals("1,00")) {
                        
                        if (to.equals("")) {
                            to = eElement.getAttribute("Timestamp");
                        }else{
                            from = eElement.getAttribute("Timestamp");
                        }
                    }else{
                        if(!to.equals("") && !from.equals("")){
                            Presence p = new Presence(t, sdf.parse(from), sdf.parse(to));
                            pre.add(p);
                            from = "";
                            to = "";
                        }
                    }
                }
            }
            return pre;
        } catch (Exception e) {
            return null;
        }
    }

}
