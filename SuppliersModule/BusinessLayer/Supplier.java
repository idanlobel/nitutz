package BusinessLayer;

import java.util.HashMap;
import java.util.List;

public class Supplier {
    private final String name;
    private final Integer companyNumber;
    private final String bankNumber;
    private final HashMap<String, ContactPerson> contactList;
    private String orderingCP;

    public Supplier(String name, Integer companyNumber, String bankNumber, List<ContactPerson> contactList,String orderingCP) {
        if (companyNumber == null | name == null | bankNumber == null | contactList == null)
            throw new IllegalArgumentException("System Error: one of the supplier parameter is null");
        if (name.trim().length() == 0)
            throw new IllegalArgumentException("User Error: Supplier parameter can not be empty");
        if (contactList.isEmpty())
            throw new IllegalArgumentException("User Error: Each supplier must have at least one contact person");
        this.name = name;
        this.companyNumber = companyNumber;
        this.bankNumber = bankNumber;
        this.contactList = new HashMap<>();
        for (ContactPerson contactPerson : contactList)
            this.contactList.put(contactPerson.getName(), contactPerson);
        if(!this.contactList.containsKey(orderingCP))
            throw new IllegalArgumentException("USER ERROR: ordering CP not in contract list");
        this.orderingCP=orderingCP;
    }

    public void addContact(ContactPerson contactPerson) {
        if (contactList.containsKey(contactPerson.getName()))
            throw new IllegalArgumentException("USER ERROR: SUPPLIER " + this.name + " ALREADY HAS A CONTACT NAMED " + contactPerson.getName());
        contactList.put(contactPerson.getName(), contactPerson);
    }

    public ContactPerson getContact(String name) {
        return contactList.get(name);
    }

    public Integer getCompanyNumber() {
        return companyNumber;
    }

    @Override
    public String toString() {
        return "name='" + name + '\'' +
                ", companyNumber=" + companyNumber +
                ", bankNumber='" + bankNumber + '\'' +
                ", contactList=" + contactList+"\n";
    }
    public ContactPerson getOrderingCP(){
        return contactList.get(orderingCP);
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public String getName() {
        return name;
    }
}
