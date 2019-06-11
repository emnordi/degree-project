package server.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import server.model.Sensor;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-04-22T00:10:43")
@StaticMetamodel(Metadata.class)
public class Metadata_ { 

    public static volatile SingularAttribute<Metadata, String> name;
    public static volatile SingularAttribute<Metadata, Long> mid;
    public static volatile SingularAttribute<Metadata, String> value;
    public static volatile SingularAttribute<Metadata, Sensor> sid;

}