package businessLogic.test;

import businessLogic.ContactPerson;

import static org.junit.jupiter.api.Assertions.*;

class ContactPersonTest {
    ContactPerson contactPersonToTest;
    String name;
    String phoneNumber;
    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        name="Itay";
        phoneNumber="0522521366";
        contactPersonToTest=new ContactPerson(name,phoneNumber);
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {

    }

    @org.junit.jupiter.api.Test
    void getName() {
        assertEquals(contactPersonToTest.getName(),name);
    }

    @org.junit.jupiter.api.Test
    void setName() {
        name="difName";
        contactPersonToTest.setName(name);
        assertEquals(contactPersonToTest.getName(),name);
    }

    @org.junit.jupiter.api.Test
    void getPhoneNumber() {
        assertEquals(contactPersonToTest.getPhoneNumber(),phoneNumber);
    }

    @org.junit.jupiter.api.Test
    void setPhoneNumber() {
        phoneNumber="difPhone";
        contactPersonToTest.setPhoneNumber(phoneNumber);
        assertEquals(contactPersonToTest.getPhoneNumber(),phoneNumber);
    }
}