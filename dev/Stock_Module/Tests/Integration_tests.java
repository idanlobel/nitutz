package Stock_Module.Tests;



import Stock_Module.Data_layer.DAL_controller;
import Stock_Module.stock_service_layer.Responses.IsError;
import Stock_Module.stock_service_layer.Responses.IsOk;
import Stock_Module.stock_service_layer.Responses.Response;
import Stock_Module.stock_service_layer.Stock_Manager;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Integration_tests {

    Stock_Manager stock_manager_test;
    Response order_catalog_99;
    Response order_catalog_88;
    Response order_catalog_72;
    Response make_sale_99;
    Response periodic_order_monday;



    @BeforeEach
    void setUp() throws Exception {

       stock_manager_test=new Stock_Manager();
        order_catalog_99=stock_manager_test.make_order("99","20","20.0","2022-05-08","something","something","something","something","something");
        order_catalog_88=stock_manager_test.make_order("88","20","20.0","2022-05-08","something","something","something","something","something");
        order_catalog_72=stock_manager_test.make_order("72","5","5.5","2022-07-18","milka","something","diary","milk products","chocolate");
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
        Response response=stock_manager_test.make_stock_report();

        assertEquals(IsOk.class,response.getClass());
    }

    @Test
    void make_prices_report() {
        Response response=stock_manager_test.make_prices_report();
        assertEquals(IsOk.class,response.getClass());
    }

    @Test
    void make_sales_report() {
        Response response=stock_manager_test.make_sales_report();
        assertEquals(IsOk.class,response.getClass());
    }

    @Test
    void make_defective_report() {
        Response response=stock_manager_test.make_defective_report();
        assertEquals(IsOk.class,response.getClass());
    }

    @Test
    void make_expiry_report() {
        Response response=stock_manager_test.make_expiry_report();
        assertEquals(IsOk.class,response.getClass());
    }

    @Test
    void set_broken_product() {
        Response response=stock_manager_test.set_broken_product("3","72");
        assertEquals(IsOk.class,response.getClass());
    }

    @Test
    void check_shortage_order() {
        Response response=stock_manager_test.check_shortage_order();
        assertEquals(IsOk.class,response.getClass());
    }

    @Test
    void check_periodic_order() {
        Response response=stock_manager_test.check_periodic_order();
        assertEquals(IsOk.class,response.getClass());
    }

    @Test
    void update_periodic_order_quantity() throws Exception {
        Response response=stock_manager_test.update_periodic_order_quantity("1","30");
        assertEquals(IsOk.class,response.getClass());
    }

    @Test
    void update_periodic_order_day() throws Exception {
        Response response=stock_manager_test.update_periodic_order_day("1","thursday");
        assertEquals(IsOk.class,response.getClass());
    }

    @Test
    void remove_periodic_order() throws Exception {
        Response response=stock_manager_test.remove_periodic_order("1");
        assertEquals(IsOk.class,response.getClass());
    }

    @Test
    void remove_sale() throws Exception {
        Response response=stock_manager_test.remove_sale("1");
        assertEquals(IsOk.class,response.getClass());
    }

    @Test
    void remove_broken_items() {
        Response response_broken=stock_manager_test.set_broken_product("3","99");
        Response response=stock_manager_test.remove_broken_items();
        assertEquals(IsOk.class,response.getClass());
    }



}
