package BusinessLayer;

import java.util.HashMap;
import java.util.List;

public class Supplier {
    private String name;
    private Integer companyNumber;
    private String bankNumber;
    private final HashMap<String,ContactPerson> contactList;

    public Supplier(String name, Integer companyNumber, String bankNumber, List<ContactPerson> contactList) {
        if(companyNumber==null | name==null | bankNumber==null | contactList==null)
            throw new IllegalArgumentException("System Error: one of the supplier parameter is null");
        if(name.trim().length()==0)
            throw new IllegalArgumentException("User Error: Supplier parameter can not be empty");
        if(contactList.isEmpty())
            throw new IllegalArgumentException("User Error: Each supplier must have at least one contact person");
        this.name=name;
        this.companyNumber=companyNumber;
        this.bankNumber=bankNumber;
        this.contactList=new HashMap<>();
        for(ContactPerson contactPerson:contactList)
            this.contactList.put(contactPerson.getName(),contactPerson);
    }
    public void addContact(ContactPerson contactPerson){
        if(contactList.containsKey(contactPerson.getName()))
            throw new IllegalArgumentException("USER ERROR: SUPPLIER "+this.name+" ALREADY HAS A CONTACT NAMED "+contactPerson.getName());
     contactList.put(contactPerson.getName(),contactPerson);
    }
    public ContactPerson getContact(String name){
        return contactList.get(name);
    }
}
