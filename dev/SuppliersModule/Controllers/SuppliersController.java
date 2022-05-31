package SuppliersModule.Controllers;

import SuppliersModule.SupplierDataAccessLayer.DataAccessObjects.SupplierDAO;
import SuppliersModule.SuppliersBusinessLayer.ContactPerson;
import SuppliersModule.SuppliersBusinessLayer.Supplier;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class SuppliersController {
    private final Hashtable<Integer, Supplier> suppliers;
    private final SupplierDAO supplierDAO = new SupplierDAO();


    public SuppliersController(){
        suppliers = new Hashtable<>();
        try {
            List<Supplier> supplierList = supplierDAO.getAllSuppliers();
            for (Supplier supplier : supplierList) {
                suppliers.put(supplier.getCompanyNumber(), supplier);
            }
        }
        catch (Exception e){
            throw new RuntimeException("Supplier Load Error: "+e.getMessage());
        }

    }
    public void containsSupplier(int companyNumber){
        if((!suppliers.containsKey(companyNumber)) && (!supplierDAO.exists(companyNumber)))
            throw new IllegalArgumentException("USER ERROR: supplier with id "+companyNumber+" does not exist");
    }
    public void hasContractPerson(int companyNumber,String name){
        if(!suppliers.get(companyNumber).ContainsContact(name))
            throw new RuntimeException("USER ERROR: Supplier "+suppliers.get(companyNumber).getName()+"has no contact named "+name);

    }
    public Supplier AddSupplier(String name, Integer companyNumber, String bankNumber, String address, List<ContactPerson> contactPeople){
        if(suppliers.containsKey(companyNumber))
            throw new IllegalArgumentException("Company number already exists in the system");
        Supplier supplier = new Supplier(name, companyNumber, bankNumber, address, contactPeople);
        suppliers.put(companyNumber, supplier);
        supplierDAO.create(supplier);
        return supplier;
    }
    public Supplier getSupplier(int companyNum) {
        if(!suppliers.containsKey(companyNum)){
            return supplierDAO.get(companyNum);
            //throw new IllegalArgumentException("USER ERROR: Supplier with company number "+companyNum+" not in system");
        }

        else return suppliers.get(companyNum);
    }
    public void changeAddress(int companyNumber, String address)  {
        suppliers.get(companyNumber).setAddress(address);
            supplierDAO.update(suppliers.get(companyNumber));
    }
    public void changeBankNum(int companyNumber, String bankNum)  {
        suppliers.get(companyNumber).setBankNumber(bankNum);
        supplierDAO.update(suppliers.get(companyNumber));
    }
    public ContactPerson AddContact(Integer companyNumber, String name, String Email, String cellNumber) throws Exception {
        if(companyNumber == null || name == null || Email == null)
            throw new IllegalArgumentException("Parameter can not ne empty");
        if((!suppliers.containsKey(companyNumber))&&(!supplierDAO.exists(companyNumber)))
            throw new IllegalArgumentException("No Supplier with this company number");
        ContactPerson contactPerson = new ContactPerson(name ,Email ,cellNumber);
        suppliers.get(companyNumber).addContact(contactPerson);
        supplierDAO.update(suppliers.get(companyNumber));
        return contactPerson;
    }
    public ContactPerson RemoveContactPerson(int companyNumber, String name) throws Exception {
        if((!suppliers.containsKey(companyNumber))&&(!supplierDAO.exists(companyNumber)))
            throw new IllegalArgumentException("USER ERROR: no supplier with company number" + companyNumber);
        ContactPerson contactPerson = suppliers.get(companyNumber).RemoveContact(name);
        supplierDAO.update(suppliers.get(companyNumber));
        return contactPerson;
    }
    public void ChangeContactPersonMail(int companyNumber, String name, String newMail) throws Exception {
        if((!suppliers.containsKey(companyNumber))&&(!supplierDAO.exists(companyNumber)))
            throw new IllegalArgumentException("USER ERROR: no supplier with company number" + companyNumber);
        suppliers.get(companyNumber).ChangeContactEmail(name, newMail);
        supplierDAO.update(suppliers.get(companyNumber));
    }
    public void ChangeContactPersonPhone(int companyNumber, String name, String newNum) throws Exception {
        if((!suppliers.containsKey(companyNumber))&&(!supplierDAO.exists(companyNumber)))
            throw new IllegalArgumentException("USER ERROR: no supplier with company number" + companyNumber);
        suppliers.get(companyNumber).ChangeContactPhoneNum(name, newNum);
        supplierDAO.update(suppliers.get(companyNumber));
    }
    public List<Supplier> getSupplierList() {
      //  return supplierDAO.getAllSuppliers();
        return new ArrayList<>(suppliers.values());
    }
    public void clearDataBase() throws Exception {
        supplierDAO.deleteAllData();
    }
    public void populateSuppliersDB() throws Exception{
        supplierDAO.populateDB();
        try {
            List<Supplier> supplierList = supplierDAO.getAllSuppliers();
            for (Supplier supplier : supplierList) {
                suppliers.put(supplier.getCompanyNumber(), supplier);
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

}
