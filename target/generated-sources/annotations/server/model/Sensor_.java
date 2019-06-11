package server.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import server.model.Datapoint;
import server.model.Metadata;
import server.model.Testbed;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-04-22T00:10:43")
@StaticMetamodel(Sensor.class)
public class Sensor_ { 

    public static volatile SingularAttribute<Sensor, String> unit;
    public static volatile ListAttribute<Sensor, Datapoint> datapointList;
    public static volatile SingularAttribute<Sensor, String> name;
    public static volatile SingularAttribute<Sensor, Testbed> tid;
    public static volatile ListAttribute<Sensor, Metadata> metadataList;
    public static volatile SingularAttribute<Sensor, Long> sid;

}