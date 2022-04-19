import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ProductsTest {


    Products products_test;
    Sale sale_test;

    @BeforeEach
    void setUp() {

        products_test=new Products(1,"something",10,50.0,100.0, LocalDate.of(2000,2,2),"something","something","something","something");
        sale_test=new Sale(0.2,0,LocalDate.of(2000,2,2),LocalDate.of(2022,2,2),"something");
    }
    @Test
    void record_sale() throws Exception {
        sale_test.Add_Products(products_test);
        assertEquals(80.0,products_test.get_by_date(LocalDate.of(2000,2,2)));
        assertEquals(100.0,products_test.get_by_date(LocalDate.now()));
    }
}