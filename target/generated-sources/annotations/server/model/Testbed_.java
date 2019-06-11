package server.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import server.model.Sensor;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-04-22T00:10:43")
@StaticMetamodel(Testbed.class)
public class Testbed_ { 

    public static volatile SingularAttribute<Testbed, String> address;
    public static volatile ListAttribute<Testbed, Sensor> sensorList;
    public static volatile SingularAttribute<Testbed, String> name;
    public static volatile SingularAttribute<Testbed, Long> tid;

}