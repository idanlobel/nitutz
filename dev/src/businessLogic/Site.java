package businessLogic;

import java.util.List;

public class Site {
    public String name;
    public String address;
    public ContactPerson contactPerson;
    public Region region;
    public SiteType siteType;

    public Site(String name, String address, ContactPerson contactPerson, Region region,SiteType siteType ){
        this.name = name;
        this.address = address;
        this.contactPerson = contactPerson;
        this.region = region;
        this.siteType =siteType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    @Override
    public String toString() {
        return "Site{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", contactPersons=" + contactPerson +
                ", region=" + region +
                '}';
    }
}

