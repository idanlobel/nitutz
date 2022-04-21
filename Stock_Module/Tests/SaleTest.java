import busniess_layer.Products;
import busniess_layer.Sale;
import busniess_layer.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class SaleTest {

    Stock stock_test;
    Sale sale_test;
    Products products_test;

    @BeforeEach
    void setUp() {
        stock_test=new Stock();
        products_test=new Products(1,"something",10,50.0,100.0,LocalDate.now(),"something","something","something","something");
        sale_test = new Sale(0,0, LocalDate.now(),LocalDate.of(2033,4,6),"something");
    }

    @Test
    void Add_Products_Test() throws Exception {

        // maybe add test if throws expection when added illegal percentage
        sale_test.SetPercentage(0.1);
        sale_test.Add_Products(products_test);
        assertEquals(90.0,products_test.getsellprice());

    }

    @Test
    void sale_is_over_Test() throws Exception {

        sale_test.SetPercentage(0.1);
        sale_test.Add_Products(products_test);
        sale_test.sale_is_over();
        assertEquals(100.0,products_test.getsellprice());

    }

}