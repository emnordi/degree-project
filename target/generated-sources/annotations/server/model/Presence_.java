package server.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import server.model.Testbed;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-04-22T00:10:43")
@StaticMetamodel(Presence.class)
public class Presence_ { 

    public static volatile SingularAttribute<Presence, Long> pid;
    public static volatile SingularAttribute<Presence, Date> tfrom;
    public static volatile SingularAttribute<Presence, Testbed> tid;
    public static volatile SingularAttribute<Presence, Date> tto;

}