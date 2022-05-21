package SuppliersModule.tests.UnitTests;


import SuppliersModule.SuppliersBusinessLayer.ContactPerson;
import SuppliersModule.SuppliersBusinessLayer.Contracts.Contract;
import SuppliersModule.SuppliersBusinessLayer.Controller;
import SuppliersModule.SuppliersBusinessLayer.Supplier;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ControllerTest {
    Controller controller;

    @BeforeEach
    void setUp() throws Exception {
        controller = new Controller();
    }

    //1
    @Test
    void add_Supplier_Success() throws Exception {
        List<ContactPerson> cpList = new ArrayList<ContactPerson>();
        ContactPerson cp1 = new ContactPerson("Yoav", "yoav@mail.com", "0500000000");
        cpList.add(cp1);
        Supplier supplier = controller.AddSupplier("Osem", 1110, "5120", "Shoham", cpList, "Yoav");
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
        Supplier supplier1 = controller.AddSupplier("Osem", 1111, "5120", "Shoham", cpList, "Yoav2");
        assertThrows(IllegalArgumentException.class,() -> controller.AddSupplier("Elit", 1111, "5120", "Shoham", cpList, "Yoav2"));
    }

    //3
    @Test
    void add_Contact_Success() throws Exception {
        List<ContactPerson> cpList = new ArrayList<ContactPerson>();
        ContactPerson cp1 = new ContactPerson("Yoav3", "yoav@mail.com", "0500000000");
        cpList.add(cp1);
        controller.AddSupplier("Tnuva", 1112, "5120", "Shoham", cpList, "Yoav3");
        ContactPerson contactPerson = controller.AddContact(1112, "Denis", "denis@mail.com", "0500000001");
        assertEquals("Denis", contactPerson.getName());
        assertEquals("denis@mail.com", contactPerson.getEmail());
        assertEquals("0500000001", contactPerson.getCellNumber());
    }

    //4
    @Test
    void add_Contact_Failure_No_Company() throws Exception {
        assertThrows(IllegalArgumentException.class,() -> controller.AddContact(404, "Denis2", "denis@mail.com", "0500000001"));
    }

    //5
    @Test
    void add_Contact_Failure_Null() throws Exception {
        List<ContactPerson> cpList = new ArrayList<ContactPerson>();
        ContactPerson cp1 = new ContactPerson("Yoav4", "yoav@mail.com", "0500000000");
        cpList.add(cp1);
        controller.AddSupplier("Kesem", 1113, "5120", "Shoham", cpList, "Yoav4");
        assertThrows(IllegalArgumentException.class,() -> controller.AddContact(1113, null, "denis@mail.com", "0500000001"));
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
        controller.AddSupplier("Bosem", 1114, "5120", "Shoham", cpList, "Yoav5");
        Contract contract = controller.SignPeriodicContract(1114, itemList, discountsList,generalDiscount, deliveyDays);
        assertEquals("Bosem", contract.getSupplier().getName());
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
        controller.AddSupplier("Tosem", 1115, "5120", "Shoham", cpList, "Yoav6");
        assertThrows(IllegalArgumentException.class,() -> controller.SignPeriodicContract(1115, itemList, discountsList,generalDiscount, deliveyDays));
    }

    //8
    @Test
    void changeAddress_Success() throws Exception {
        List<ContactPerson> cpList = new ArrayList<ContactPerson>();
        ContactPerson cp1 = new ContactPerson("Yoav7", "yoav@mail.com", "0500000000");
        cpList.add(cp1);
        Supplier supplier = controller.AddSupplier("Vosem", 1116, "5120", "Shoham", cpList, "Yoav7");
        controller.changeAddress(1116, "Beer Sheva");
        assertEquals("Beer Sheva", supplier.getAddress());
    }

    //9
    @Test
    void changeAddress_Fail() throws Exception {
        List<ContactPerson> cpList = new ArrayList<ContactPerson>();
        ContactPerson cp1 = new ContactPerson("Yoav8", "yoav@mail.com", "0500000000");
        cpList.add(cp1);
        Supplier supplier = controller.AddSupplier("Hosem", 1117, "5120", "Shoham", cpList, "Yoav8");
        assertThrows(Exception.class,() -> controller.changeAddress(808, "Beer Sheva"));
    }

    //10
    @Test
    void changeBankNum_Success() throws Exception {
        List<ContactPerson> cpList = new ArrayList<ContactPerson>();
        ContactPerson cp1 = new ContactPerson("Yoav9", "yoav@mail.com", "0500000000");
        cpList.add(cp1);
        Supplier supplier = controller.AddSupplier("Vosem", 1118, "5120", "Shoham", cpList, "Yoav9");
        controller.changeBankNum(1118, "1234");
        assertEquals("1234", supplier.getBankNumber());
    }

    @AfterEach
    void tearDown() throws Exception {
        controller.clearDataBase();
        controller = null;
    }
}

