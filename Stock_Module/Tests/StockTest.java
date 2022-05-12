import Data_layer.DAL_controller;
import Data_layer.Products_DAO;
import busniess_layer.Product;
import busniess_layer.Products;
import busniess_layer.Sale;
import busniess_layer.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class StockTest {

    Stock stock_test;
    Sale sale_test;
    Products products_1_test;
    Products products_2_test;

    @BeforeEach
    void setUp() {
        stock_test=new Stock();
        products_1_test=new Products(1,"something",100,50.0,100.0, LocalDate.now().toString(),"something","something","something","something", DAL_controller.getInstance().getProducts_table(), DAL_controller.getInstance().getProduct_table());
        products_2_test=new Products(2,"something",10,50.0,100.0, LocalDate.now().toString(),"something","something","something","something",DAL_controller.getInstance().getProducts_table(), DAL_controller.getInstance().getProduct_table());
        sale_test = new Sale(0, LocalDate.now().toString(),LocalDate.of(2033,4,6).toString(),"something",DAL_controller.getInstance().getSale_table());
    }

    @Test
    void get_low_products() {
        stock_test.Order(1,100,50.0, LocalDate.now().toString(),"somethig","something","somthing","something","something");
        stock_test.Order(2,10,50.0, LocalDate.now().toString(),"somethig","something","somthing","something","something");

        assertEquals(1, stock_test.get_low_products().size());
    }

    @Test
    void order() {
        stock_test.Order(2,10,50.0, LocalDate.now().toString(),"somethig","something","somthing","something","something");
        stock_test.Order(2,50,50.0,"2022-08-09","something","something","something","something","something");
        stock_test.Order(1,500,50.0,LocalDate.of(1111,1,20).toString(),"something","something","something","something","something");
        assertEquals(60, stock_test.get_products(2).getQuantity());
        assertEquals(500, stock_test.get_products(1).getQuantity());
    }

    @Test
    void sale() throws Exception {

        stock_test.Order(2,50,50.0,LocalDate.of(1111,1,20).toString(),"something","something","something","something","something");
        stock_test.Sale(2,LocalDate.now().toString(),LocalDate.of(2030,2,2).toString(),"something",50);
        assertEquals(37.5, stock_test.get_products(2).getsellprice());

    }

    @Test
    void remove_sale() throws Exception {
        stock_test.Order(2,50,50.0,LocalDate.of(1111,1,20).toString(),"something","something","something","something","something");
        stock_test.Sale(2,LocalDate.now().toString(),LocalDate.of(2030,2,2).toString(),"something",0.5);
        stock_test.remove_sale(stock_test.getsales().get(0).getId());
        assertEquals(75, stock_test.get_products(2).getsellprice());
    }

    @Test
    void sorted_by_expiration() throws Exception {
        stock_test.Order(2,50,50.0,LocalDate.of(2022,8,20).toString(),"something","something","something","something","something");
        stock_test.Order(87,50,50.0,LocalDate.of(2022,6,20).toString(),"something","something","something","something","something");
        List<Product> testme= stock_test.sorted_by_expiration();
        assertEquals(LocalDate.of(2022,6,20), testme.get(0).getExpire_date());
    }

    @Test
    void sale_by_category() throws Exception {

        stock_test.Order(2,50,100.0,LocalDate.of(1111,1,20).toString(),"something","something","dairy","null","null");
        stock_test.Order(5,50,10.0,LocalDate.of(1111,1,20).toString(),"something","something","drinks","beer","500");
        stock_test.sale_by_category(LocalDate.now().toString(),LocalDate.of(2222,2,2).toString(),"holiday",50,"dairy","null","null");
        assertEquals(75, stock_test.get_products(2).getsellprice());

    }


}