package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.UserXOrganization;

@Generated(value="EclipseLink-2.3.2.v20111125-r10461", date="2012-09-04T17:15:42")
@StaticMetamodel(Organization.class)
public class Organization_ { 

    public static volatile SingularAttribute<Organization, Integer> id;
    public static volatile SingularAttribute<Organization, String> name;
    public static volatile CollectionAttribute<Organization, UserXOrganization> userXOrganizationCollection;

}