import busniess_layer.Products;
import busniess_layer.Sale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ProductsTest {


    Products products_test;
    Sale sale_test;

    @BeforeEach
    void setUp() {

        products_test=new Products(1,"something",10,50.0,100.0, LocalDate.of(2000,2,2).toString(),"something","something","something","something",null,null);
        sale_test=new Sale(20,0,LocalDate.of(2000,2,2).toString(),LocalDate.of(2022,2,2).toString(),"something",null);
    }
    @Test
    void record_sale() throws Exception {
        //checks that there was really a sale
        sale_test.Add_Products(products_test);
        assertEquals(80.0,products_test.get_by_date(LocalDate.of(2000,2,2).toString()));
        assertEquals(100.0,products_test.get_by_date(LocalDate.now().toString()));
    }

    @Test
    void record_sale_2() throws Exception {
        //checks that the sale was recorded
        sale_test.Add_Products(products_test);
        assertEquals(0,products_test.getsaleshistory().get(0));

    }
}