package BusinessLayer;

import java.util.List;

public class Supplier {
    private String name;
    private Integer companyNumber;
    private String bankNumber;
    private List<ContactPerson> contactList;

    public Supplier(String name, Integer companyNumber, String bankNumber, List<ContactPerson> contactList) {
        if(companyNumber==null | name==null | bankNumber==null | contactList==null)
            throw new IllegalArgumentException("Supplier parameter can not be empty");
        if(contactList.isEmpty())
            throw new IllegalArgumentException("Each supplier must have at least one contact person");
        this.name=name;
        this.companyNumber=companyNumber;
        this.bankNumber=bankNumber;
        this.contactList=contactList;
    }
    public void addContact(ContactPerson contactPerson){
     contactList.add(contactPerson);
    }
}
