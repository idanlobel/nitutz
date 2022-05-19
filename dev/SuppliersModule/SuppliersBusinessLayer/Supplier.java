package SuppliersModule.SuppliersBusinessLayer;

import java.util.HashMap;
import java.util.List;

public class Supplier {
    private final String name;
    private final Integer companyNumber;
    private String bankNumber;
    private String address;
    private final HashMap<String, ContactPerson> contactList;
    private String orderingCP;

    public Supplier(String name, Integer companyNumber, String bankNumber,String address, List<ContactPerson> contactList,String orderingCP) {
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
        this.address=address;
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

    public void setOrderingCP(String orderingCP) {
        this.orderingCP = orderingCP;
    }

    public boolean ContainsContact(String name){
        return contactList.containsKey(name);
    }

    private void CheckNameValidity(String name){
        if(!contactList.containsKey(name)){
            throw new IllegalArgumentException("USER ERROR: "+getName()+" has no contract person named "+name);
        }
    }
    public ContactPerson RemoveContact(String name){
        CheckNameValidity(name);
        ContactPerson contactPerson=contactList.remove(name);
        return contactPerson;
    }
    public void ChangeContactEmail(String name,String newMail){
        CheckNameValidity(name);
        contactList.get(name).setEmail(newMail);
    }
    public void ChangeContactPhoneNum(String name,String newNum){
        CheckNameValidity(name);
        contactList.get(name).setCellNumber(newNum);
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
                ", Address='" + address + '\'' +
                ", contactList=" + contactList+"\n";
    }
    public ContactPerson getOrderingCP(){
        return contactList.get(orderingCP);
    }

    public String getBankNumber() {
        return bankNumber;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setBankNumber(String bankNumber) {
        this.bankNumber = bankNumber;
    }

    public String getName() {
        return name;
    }

    public HashMap<String, ContactPerson> getContactList(){
        return contactList;
    }
}
