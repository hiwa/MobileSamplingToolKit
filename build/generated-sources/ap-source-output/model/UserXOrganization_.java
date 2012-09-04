package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Organization;
import model.User;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2012-09-04T17:15:42")
@StaticMetamodel(UserXOrganization.class)
public class UserXOrganization_ { 

    public static volatile SingularAttribute<UserXOrganization, Integer> id;
    public static volatile SingularAttribute<UserXOrganization, Organization> organizationid;
    public static volatile SingularAttribute<UserXOrganization, User> userid;

}