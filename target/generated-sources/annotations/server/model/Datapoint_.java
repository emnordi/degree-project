package server.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import server.model.Sensor;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-04-22T00:10:43")
@StaticMetamodel(Datapoint.class)
public class Datapoint_ { 

    public static volatile SingularAttribute<Datapoint, Float> val;
    public static volatile SingularAttribute<Datapoint, Date> tstamp;
    public static volatile SingularAttribute<Datapoint, Long> did;
    public static volatile SingularAttribute<Datapoint, Sensor> sid;

}