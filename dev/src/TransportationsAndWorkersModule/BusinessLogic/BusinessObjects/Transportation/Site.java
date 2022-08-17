package src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.Transportation;


public class Site {
    public String name;
    public String address;
    public ContactPerson contactPerson;
    public String region;

    public Site(String name, String address, ContactPerson contactPerson, String region){
        this.name = name;
        this.address = address;
        this.contactPerson = contactPerson;
        this.region=region;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "Site{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", contactPersons=" + contactPerson +
                '}';
    }
}

