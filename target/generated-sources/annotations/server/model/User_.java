package server.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-04-22T00:10:43")
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile SingularAttribute<User, Long> uid;
    public static volatile SingularAttribute<User, String> password;
    public static volatile SingularAttribute<User, String> surname;
    public static volatile SingularAttribute<User, String> name;
    public static volatile SingularAttribute<User, Integer> privilege;
    public static volatile SingularAttribute<User, String> email;

}