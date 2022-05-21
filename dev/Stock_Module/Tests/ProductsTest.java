package Stock_Module.Tests;


import Stock_Module.Data_layer.DAL_controller;
import Stock_Module.busniess_layer.Products;
import Stock_Module.busniess_layer.Sale;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductsTest {


    Products products_test;
    Sale sale_test;

   @BeforeEach
    void setUp() {

        products_test=new Products(1,"something",10,50.0,100.0, LocalDate.of(2000,2,2).toString(),"something","something","something","something", DAL_controller.getInstance().getProducts_table(), DAL_controller.getInstance().getProduct_table(),DAL_controller.getInstance().getSales_history_table());
        sale_test=new Sale(20,LocalDate.of(2000,2,2).toString(),LocalDate.of(2022,2,2).toString(),"something",DAL_controller.getInstance().getSale_table());
    }

    @AfterEach
    void clear()
    {
        DAL_controller.getInstance().clear_database();
    }
    @Test
    void record_price() throws Exception {
        //checks that there was really a sale
        sale_test.Add_Products(products_test);
        assertEquals(80.0,products_test.get_by_date(LocalDate.of(2000,2,2).toString()));
       assertEquals(100.0,products_test.get_by_date(LocalDate.now().toString()));
    }

    @Test
    void record_sale() throws Exception {
        //checks that the sale was recorded

        sale_test.Add_Products(products_test);
        assertEquals(sale_test.getId(),products_test.getsaleshistory().get(0).getId());

    }
}