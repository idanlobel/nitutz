package UnitTests;

//import BusinessLayer.ContactPerson;
//import BusinessLayer.Contracts.Contract;
//import BusinessLayer.Controller;
//import BusinessLayer.Order;
//import BusinessLayer.Supplier;
//import org.junit.jupiter.api.*;
//import org.junit.jupiter.api.Test;
//
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//public class ControllerTest {
//    Controller controller;
//
//    @BeforeEach
//    void setUp() {
//        controller = new Controller();
//    }
//
//    //1
//    @Test
//    void add_Supplier_Success() throws Exception {
//        List<ContactPerson> cpList = new ArrayList<ContactPerson>();
//        ContactPerson cp1 = new ContactPerson("Yoav", "yoav@mail.com", "0500000000");
//        cpList.add(cp1);
//        Supplier supplier = controller.AddSupplier("Osem", 1110, "5120", cpList);
//        assertEquals("Osem", supplier.getName());
//        assertEquals(1110, supplier.getCompanyNumber());
//        assertEquals("5120", supplier.getBankNumber());
//    }
//    //2
//    @Test
//    void add_Supplier_Fail() throws Exception {
//        List<ContactPerson> cpList = new ArrayList<ContactPerson>();
//        ContactPerson cp1 = new ContactPerson("Yoav", "yoav@mail.com", "0500000000");
//        cpList.add(cp1);
//        Supplier supplier1 = controller.AddSupplier("Tnuva", 1111, "5121", cpList);
//        assertThrows(IllegalArgumentException.class,() -> controller.AddSupplier("Elit", 1111, "5122", cpList));
//    }
//
//    //3
//    @Test
//    void add_Contact_Success() throws Exception {
//        List<ContactPerson> cpList = new ArrayList<ContactPerson>();
//        ContactPerson cp1 = new ContactPerson("Yoav", "yoav@mail.com", "0500000000");
//        cpList.add(cp1);
//        controller.AddSupplier("Tnuva", 1111, "5121", cpList);
//        ContactPerson contactPerson = controller.AddContact(1111, "Denis", "denis@mail.com", "0500000001");
//        assertEquals("Denis", contactPerson.getName());
//        assertEquals("denis@mail.com", contactPerson.getEmail());
//        assertEquals("0500000001", contactPerson.getCellNumber());
//    }
//
//    //4
//    @Test
//    void add_Contact_Failure_No_Company() throws Exception {
//        assertThrows(IllegalArgumentException.class,() -> controller.AddContact(1111, "Denis", "denis@mail.com", "0500000001"));
//    }
//
//    //5
//    @Test
//    void add_Contact_Failure_Null() throws Exception {
//        List<ContactPerson> cpList = new ArrayList<ContactPerson>();
//        ContactPerson cp1 = new ContactPerson("Yoav", "yoav@mail.com", "0500000000");
//        cpList.add(cp1);
//        controller.AddSupplier("Tnuva", 1111, "5121", cpList);
//        assertThrows(IllegalArgumentException.class,() -> controller.AddContact(1111, null, "denis@mail.com", "0500000001"));
//    }
//
//
//    //6
//    @Test
//    void sign_Contract_Success() throws Exception {
//        boolean[] deliveyDays = {false,false,false,false,true,false,false};
//        List<int[]> itemList = new ArrayList<>();
//        List<ContactPerson> cpList = new ArrayList<ContactPerson>();
//        ContactPerson cp1 = new ContactPerson("Yoav", "yoav@mail.com", "0500000000");
//        cpList.add(cp1);
//        controller.AddSupplier("Tnuva", 1111, "5121", cpList);
//        Contract contract = controller.SignContract(1111, itemList, null, deliveyDays);
//        assertEquals("Tnuva", contract.getSupplier().getName());
//    }
//
//    //7
//    @Test
//    void sign_Contract_Fail() throws Exception {
//        boolean[] deliveyDays = {false,false,false,false,true,false};
//        List<int[]> itemList = new ArrayList<>();
//        List<ContactPerson> cpList = new ArrayList<ContactPerson>();
//        ContactPerson cp1 = new ContactPerson("Yoav", "yoav@mail.com", "0500000000");
//        cpList.add(cp1);
//        controller.AddSupplier("Tnuva", 1111, "5121", cpList);
//        assertThrows(IllegalArgumentException.class,() -> controller.SignContract(1111, itemList, null, deliveyDays));
//    }
//
//    //8
//    @Test
//    void order_Product_Success() throws Exception {
//        boolean[] deliveyDays = {false,false,false,false,true,false,false};
//        List<int[]> itemList = new ArrayList<>();
//        List<ContactPerson> cpList = new ArrayList<ContactPerson>();
//        ContactPerson cp1 = new ContactPerson("Yoav", "yoav@mail.com", "0500000000");
//        cpList.add(cp1);
//        controller.AddSupplier("Tnuva", 1111, "5121", cpList);
//        Contract contract = controller.SignContract(1111, itemList, null, deliveyDays);
//        LocalDate localDate = LocalDate.parse("03-04-22", DateTimeFormatter.ofPattern("dd-MM-yy"));
//        List<int[]> productList = new ArrayList<>();
//        Order order = controller.OrderProducts(1111, productList, "Yoav", localDate);
//        assertEquals(0, order.getTotalPrice());
//    }
//
//    //9
//    @Test
//    void order_Product_Fail_Illegal_Item() throws Exception {
//        boolean[] deliveyDays = {false,false,false,false,true,false,false};
//        List<int[]> itemList = new ArrayList<>();
//        List<ContactPerson> cpList = new ArrayList<ContactPerson>();
//        ContactPerson cp1 = new ContactPerson("Yoav", "yoav@mail.com", "0500000000");
//        cpList.add(cp1);
//        controller.AddSupplier("Tnuva", 1111, "5121", cpList);
//        controller.SignContract(1111, itemList, null, deliveyDays);
//        LocalDate localDate = LocalDate.parse("03-04-22", DateTimeFormatter.ofPattern("dd-MM-yy"));
//        List<int[]> productList = new ArrayList<>();
//        int[] a = {1000, 50};
//        productList.add(a);
//        assertThrows(IllegalArgumentException.class,() -> controller.OrderProducts(1111, productList, "Yoav", localDate));
//    }
//
//    //10
//    @Test
//    void order_Product_Fail_No_Contract() throws Exception {
//        List<ContactPerson> cpList = new ArrayList<>();
//        ContactPerson cp1 = new ContactPerson("Yoav", "yoav@mail.com", "0500000000");
//        cpList.add(cp1);
//        controller.AddSupplier("Tnuva", 1111, "5121", cpList);
//        LocalDate localDate = LocalDate.parse("03-04-22", DateTimeFormatter.ofPattern("dd-MM-yy"));
//        List<int[]> productList = new ArrayList<>();
//        assertThrows(IllegalArgumentException.class,() -> controller.OrderProducts(1111, productList, "Yoav", localDate));
//    }
//
//    @AfterEach
//    void tearDown() {
//        controller = null;
//    }
//}
//