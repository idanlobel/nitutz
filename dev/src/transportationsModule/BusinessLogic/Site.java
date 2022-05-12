package src.transportationsModule.BusinessLogic;

import src.transportationsModule.BusinessLogic.ContactPerson;
import src.transportationsModule.BusinessLogic.Garbage.Region;
import src.transportationsModule.BusinessLogic.Garbage.SiteType;

public class Site {
    public String name;
    public String address;
    public ContactPerson contactPerson;
    public Region region;

    public Site(String name, String address, ContactPerson contactPerson, Region region ){
        this.name = name;
        this.address = address;
        this.contactPerson = contactPerson;
        this.region = region;
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

