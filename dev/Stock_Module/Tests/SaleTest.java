package Stock_Module.Tests;


import Stock_Module.Data_layer.DAL_controller;
import Stock_Module.busniess_layer.Products;
import Stock_Module.busniess_layer.Sale;
import Stock_Module.busniess_layer.Stock;
import org.junit.jupiter.api.AfterEach;
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
        products_test=new Products(1,"something",10,50.0,100.0,LocalDate.now().toString(),"something","something","something","something", DAL_controller.getInstance().getProducts_table(), DAL_controller.getInstance().getProduct_table(),DAL_controller.getInstance().getSales_history_table());
        sale_test = new Sale(0, LocalDate.now().toString(),LocalDate.of(2033,4,6).toString(),"something",DAL_controller.getInstance().getSale_table());
    }

    @AfterEach
    void clear()
    {
        DAL_controller.getInstance().clear_database();
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