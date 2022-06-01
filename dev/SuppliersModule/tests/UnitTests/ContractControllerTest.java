package SuppliersModule.tests.UnitTests;


import SuppliersModule.SuppliersBusinessLayer.Controllers.OrderController;
import SuppliersModule.SuppliersBusinessLayer.Controllers.SuppliersController;
import SuppliersModule.SuppliersBusinessLayer.ContactPerson;
import SuppliersModule.SuppliersBusinessLayer.Contracts.Contract;
import SuppliersModule.SuppliersBusinessLayer.Controllers.ContractController;
import SuppliersModule.SuppliersBusinessLayer.Supplier;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ContractControllerTest {
    ContractController contractController;
    SuppliersController suppliersController;
    OrderController orderController;

    @BeforeEach
    void setUp()  {
        contractController = new ContractController();
        suppliersController=new SuppliersController();
        orderController=new OrderController();
    }

    //1
    @Test
    void add_Supplier_Success() throws Exception {
        List<ContactPerson> cpList = new ArrayList<ContactPerson>();
        ContactPerson cp1 = new ContactPerson("Yoav", "yoav@mail.com", "0500000000");
        cpList.add(cp1);
        Supplier supplier = suppliersController.AddSupplier("Osem", 1110, "5120", "Shoham", cpList);
        assertEquals("Osem", supplier.getName());
        assertEquals(1110, supplier.getCompanyNumber());
        assertEquals("5120", supplier.getBankNumber());
    }
    //2
    @Test
    void add_Supplier_Fail() throws Exception {
        List<ContactPerson> cpList = new ArrayList<ContactPerson>();
        ContactPerson cp1 = new ContactPerson("Yoav2", "yoav@mail.com", "0500000000");
        cpList.add(cp1);
        Supplier supplier1 = suppliersController.AddSupplier("Osem", 1111, "5120", "Shoham", cpList);
        assertThrows(IllegalArgumentException.class,() -> suppliersController.AddSupplier("Elit", 1111, "5120", "Shoham", cpList));
    }

    //3
    @Test
    void add_Contact_Success() throws Exception {
        List<ContactPerson> cpList = new ArrayList<ContactPerson>();
        ContactPerson cp1 = new ContactPerson("Yoav3", "yoav@mail.com", "0500000000");
        cpList.add(cp1);
        suppliersController.AddSupplier("Tnuva", 1112, "5120", "Shoham", cpList);
        ContactPerson contactPerson = suppliersController.AddContact(1112, "Denis", "denis@mail.com", "0500000001");
        assertEquals("Denis", contactPerson.getName());
        assertEquals("denis@mail.com", contactPerson.getEmail());
        assertEquals("0500000001", contactPerson.getCellNumber());
    }

    //4
    @Test
    void add_Contact_Failure_No_Company() throws Exception {
        assertThrows(IllegalArgumentException.class,() -> suppliersController.AddContact(404, "Denis2", "denis@mail.com", "0500000001"));
    }

    //5
    @Test
    void add_Contact_Failure_Null() throws Exception {
        List<ContactPerson> cpList = new ArrayList<ContactPerson>();
        ContactPerson cp1 = new ContactPerson("Yoav4", "yoav@mail.com", "0500000000");
        cpList.add(cp1);
        suppliersController.AddSupplier("Kesem", 1113, "5120", "Shoham", cpList);
        assertThrows(IllegalArgumentException.class,() -> suppliersController.AddContact(1113, null, "denis@mail.com", "0500000001"));
    }

    //6
    @Test
    void sign_Contract_Success() throws Exception {
        boolean[] deliveyDays = {false,false,false,false,true,false,false};
        List<int[]> itemList = new ArrayList<>();
        HashMap<Integer, List<int[]>> discountsList = new HashMap<>();
        List<int[]> generalDiscount = new ArrayList<>();
        List<int[]> itemInfo = new ArrayList<>();
        List<ContactPerson> cpList = new ArrayList<ContactPerson>();
        ContactPerson cp1 = new ContactPerson("Yoav5", "yoav@mail.com", "0500000000");
        cpList.add(cp1);
        suppliersController.AddSupplier("Bosem", 1114, "5120", "Shoham", cpList);
        Contract contract = contractController.SignPeriodicContract(1114, "Yoav5", itemList, discountsList,generalDiscount, deliveyDays);
        assertEquals("Bosem", suppliersController.getSupplier(1114).getName());
    }

    //7
    @Test
    void sign_Contract_Fail() throws Exception {
        boolean[] deliveyDays = {false,false,false,false,true,false};
        List<int[]> itemList = new ArrayList<>();
        HashMap<Integer, List<int[]>> discountsList = new HashMap<>();
        List<int[]> generalDiscount = new ArrayList<>();
        List<ContactPerson> cpList = new ArrayList<ContactPerson>();
        ContactPerson cp1 = new ContactPerson("Yoav6", "yoav@mail.com", "0500000000");
        cpList.add(cp1);
        suppliersController.AddSupplier("Tosem", 1115, "5120", "Shoham", cpList);
        assertThrows(IllegalArgumentException.class,() -> contractController.SignPeriodicContract(1115, "Yoav6", itemList, discountsList,generalDiscount, deliveyDays));
    }

    //8
    @Test
    void changeAddress_Success() throws Exception {
        List<ContactPerson> cpList = new ArrayList<ContactPerson>();
        ContactPerson cp1 = new ContactPerson("Yoav7", "yoav@mail.com", "0500000000");
        cpList.add(cp1);
        Supplier supplier = suppliersController.AddSupplier("Vosem", 1116, "5120", "Shoham", cpList);
        suppliersController.changeAddress(1116, "Beer Sheva");
        assertEquals("Beer Sheva", supplier.getAddress());
    }

    //9
    @Test
    void changeAddress_Fail() throws Exception {
        List<ContactPerson> cpList = new ArrayList<ContactPerson>();
        ContactPerson cp1 = new ContactPerson("Yoav8", "yoav@mail.com", "0500000000");
        cpList.add(cp1);
        Supplier supplier = suppliersController.AddSupplier("Hosem", 1117, "5120", "Shoham", cpList);
        assertThrows(Exception.class,() -> suppliersController.changeAddress(808, "Beer Sheva"));
    }

    //10
    @Test
    void changeBankNum_Success() throws Exception {
        List<ContactPerson> cpList = new ArrayList<ContactPerson>();
        ContactPerson cp1 = new ContactPerson("Yoav9", "yoav@mail.com", "0500000000");
        cpList.add(cp1);
        Supplier supplier = suppliersController.AddSupplier("Vosem", 1118, "5120", "Shoham", cpList);
        suppliersController.changeBankNum(1118, "1234");
        assertEquals("1234", supplier.getBankNumber());
    }

    @AfterEach
    void tearDown() throws Exception {
        suppliersController.clearDataBase();
        contractController = null;
    }
}

