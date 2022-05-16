import Data_layer.DAL_controller;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service_layer.Responses.IsError;
import service_layer.Responses.IsOk;
import service_layer.Responses.Response;
import service_layer.Stock_Manager;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Integration_tests {

    Stock_Manager stock_manager_test;
    Response order_catalog_99;
    Response order_catalog_88;

    Response make_sale_99;
    Response periodic_order_monday;



    @BeforeEach
    void setUp() throws Exception {

       stock_manager_test=new Stock_Manager();
        order_catalog_99=stock_manager_test.make_order("99","20","20.0","2022-05-08","something","something","something","something","something");
        order_catalog_88=stock_manager_test.make_order("88","20","20.0","2022-05-08","something","something","something","something","something");

        make_sale_99=stock_manager_test.make_sale("99","2022-05-08","2022-05-10","something","2");
        periodic_order_monday=stock_manager_test.create_order("monday","99","20","20.0","2022-05-08","something","something","something","something");

    }

    @AfterEach
    void clear()
    {
        DAL_controller.getInstance().clear_database();
    }
    @Test
    void make_order_test() throws Exception {



        assertEquals(IsOk.class,order_catalog_99.getClass());

    }

    @Test
    void auto_start_test() throws Exception {

        Response response=stock_manager_test.Auto_start();

        assertEquals(IsOk.class,response.getClass());

    }

    @Test
    void create_order_test() throws Exception {


        assertEquals(IsOk.class,periodic_order_monday.getClass());
    }

    @Test
    void make_sale_test() throws Exception {

        Response response=stock_manager_test.make_sale("99","2022-05-08","2022-05-10","something","2");
        assertEquals(IsOk.class,response.getClass());
    }


    @Test
    void add_product_to_sale() throws Exception {

        Response response4=stock_manager_test.add_product_to_sale("88","1");
        Response response5=stock_manager_test.add_product_to_sale("69","1");
        assertEquals(IsOk.class,response4.getClass());
        assertEquals(IsError.class,response5.getClass());
    }

    @Test
    void make_sale_by_catgeory() throws Exception {

//        Response response=stock_manager_test.make_order("99","20","20.0","2022-05-08","something","something","dairy","drinks","null");


        Response response3=stock_manager_test.make_sale_by_catgeory("2022-05-08","2022-05-10","something","2","dairy","drinks","null");

        assertEquals(IsOk.class,response3.getClass());


    }

    @Test
    void show_by_catgeory() {

        Response response=stock_manager_test.show_by_catgeory("something","dairy","drinks");

        assertEquals(IsOk.class,response.getClass());
    }

    @Test
    void make_stock_report() {

    }

    @Test
    void make_prices_report() {
    }

    @Test
    void make_sales_report() {
    }

    @Test
    void make_defective_report() {
    }

    @Test
    void make_expiry_report() {
    }

    @Test
    void set_broken_product() {
    }

    @Test
    void check_shortage_order() {
    }

    @Test
    void check_periodic_order() {
    }

    @Test
    void update_periodic_order_quantity() {
    }

    @Test
    void update_periodic_order_day() {
    }

    @Test
    void remove_periodic_order() {
    }

    @Test
    void remove_sale() {
    }

    @Test
    void remove_broken_items() {
    }



}
