package Tests.UnitTests.TransportUnitTests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.Transportation.TransportProductsDocument;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class TransportProductsDocumentTest {

    TransportProductsDocument transportProductsDocument;
    HashMap<String,Integer> prod;
    @BeforeEach
    void setUp() {
        transportProductsDocument= new TransportProductsDocument();
        prod=new HashMap<>();
        prod.put("apple",1);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getId() {
        transportProductsDocument.setId("111");
        assertEquals("111",transportProductsDocument.getId());

    }

    @Test
    void getDestinationId() {
        transportProductsDocument.setDestinationId("there");
        assertEquals("there",transportProductsDocument.getDestinationId());
    }

    @Test
    void getProducts() {
        transportProductsDocument.setProducts(prod);
        assertEquals(prod,transportProductsDocument.getProducts());

    }

    @Test
    void setId() {
        assertNotEquals("randid",transportProductsDocument.getId());
        transportProductsDocument.setId("111");
        assertEquals("111",transportProductsDocument.getId());
    }

    @Test
    void setDestinationId() {
        assertNotEquals("randid",transportProductsDocument.getDestinationId());
        transportProductsDocument.setDestinationId("111");
        assertEquals("111",transportProductsDocument.getDestinationId());
    }

    @Test
    void setProducts() {
        assertNotEquals(prod,transportProductsDocument.getProducts());
        transportProductsDocument.setProducts(prod);
        assertEquals(prod,transportProductsDocument.getProducts());
    }
}