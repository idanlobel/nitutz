package Stock_Module.stock_service_layer;


import Stock_Module.busniess_layer.Stock;
import Stock_Module.stock_service_layer.Responses.IsError;
import Stock_Module.stock_service_layer.Responses.IsOk;
import Stock_Module.stock_service_layer.Responses.Response;

import java.util.List;

public class Stock_Manager {

    private Stock mystock;



    public Stock_Manager()
    {
        mystock=new Stock();
    }



public Response Auto_start() throws Exception {


    try {
        this.make_order("500","20","100","2022-12-12","beer","beers_spring","drinks","beverages","500_ml");
        this.make_order("123","200","10","2022-05-30","milk","tnuva","dairy","drinks","null");
        this.make_order("99","5","1000","2022-04-20","pizza","pizza_hut","food","fast_food","null");
        this.make_sale("500","2022-04-23","2022-08-09","easter","10");

        this.set_broken_product("10","500");
        this.set_broken_product("151","123");
        return new IsOk(null,"done successfully");
    }
    catch (Exception e){
        return new IsError(e.getMessage());
    }

}
public Response<Boolean> ValidateCatalogNumber(List<Long> catalogNumbers){
    try {
        return new IsOk(mystock.ValidateCatalogNum(catalogNumbers),"done successfully");
    }
    catch (Exception e){
        return new IsError(e.getMessage());
    }
}
    public Response make_order(String product_catalog_number, String quantity, String cost, String expiry, String name, String manufactorer, String category, String sub_cat, String sub_sub_cat)
    {


        try {
            mystock.Order(Long.parseLong(product_catalog_number),Integer.parseInt(quantity),Double.parseDouble(cost),expiry,name,manufactorer,category,sub_cat,sub_sub_cat);
            return new IsOk(null,"done successfully");
        }
        catch (Exception e){
            return new IsError(e.getMessage());
        }


    }

    public Response create_order(String day,String product_catalog_number, String quantity, String cost,String name, String manufactorer, String category, String sub_cat, String sub_sub_cat)
    {
        try {
            mystock.define_periodic_orders(day,Long.parseLong(product_catalog_number),Integer.parseInt(quantity),Double.parseDouble(cost),name,manufactorer,category,sub_cat,sub_sub_cat);
            return new IsOk(null,"done successfully");
        }
        catch (Exception e){
            return new IsError(e.getMessage());
        }

    }

    public Response make_sale(String products_catalog_number, String startdate,String enddate , String reason, String percentage) throws Exception {

        try {
            mystock.Sale(Long.parseLong(products_catalog_number),startdate,enddate,reason,Double.parseDouble(percentage));
            return new IsOk(null,"done successfully");
        }
        catch (Exception e){
            return new IsError(e.getMessage());
        }

    }

    public Response add_product_to_sale(String products_catalog_number,String sale_id) throws Exception {
        try {
            mystock.Add_to_sale(Long.parseLong(products_catalog_number),Integer.parseInt(sale_id));
            return new IsOk(null,"done successfully");
        }
        catch (Exception e){
            return new IsError(e.getMessage());
        }

    }

    public Response make_sale_by_catgeory(String startdate,String enddate , String reason, String percentage,String category,String sub_cat,String sub_sub_cat) throws Exception
    {
        try {
            mystock.sale_by_category(startdate,enddate,reason,Double.parseDouble(percentage),category,sub_cat,sub_sub_cat);
            return new IsOk(null,"done successfully");
        }
        catch (Exception e){
            return new IsError(e.getMessage());
        }

    }

    public Response<Service_Report> show_by_catgeory(String main_cat,String sub_cat,String sub_sub_cat)
    {
        try {
            Service_Report service_report= new Service_Report(mystock.get_products_by_categories(main_cat,sub_cat,sub_sub_cat).tostring());
            return new IsOk(service_report,"done successfully");
        }
        catch (Exception e){
            return new IsError(e.getMessage());
        }
    }

    public Response<Service_Report>  make_stock_report()
    {
        try {
            Service_Report service_report=new Service_Report(mystock.make_stock_report().tostring());
            return new IsOk(service_report,"done successfully");
        }
        catch (Exception e){
            return new IsError(e.getMessage());
        }



    }
    public Response<Service_Report>  make_prices_report()
    {
        try {
            String str=mystock.make_prices_report().tostring();
            Service_Report service_report=new Service_Report(str);
            return new IsOk(service_report,"done successfully");
        }
        catch (Exception e){
            return new IsError(e.getMessage());
        }

    }


    public Response<Service_Report>  make_sales_report()
    {
        try {
            Service_Report service_report=new Service_Report(mystock.make_sales_report().tostring());

            return new IsOk(service_report,"done successfully");
        }
        catch (Exception e){
            return new IsError(e.getMessage());
        }


    }

    public Response<Service_Report>  make_defective_report()
    {
        try {
            Service_Report service_report=new Service_Report(this.mystock.make_defective_report().tostring());

            return new IsOk(service_report,"done successfully");
        }
        catch (Exception e){
            return new IsError(e.getMessage());
        }


    }

    public Response<Service_Report>  make_expiry_report() {
        try {
            Service_Report service_report = new Service_Report(this.mystock.make_sorted_by_expiration_report().tostring());

            return new IsOk(service_report, "done successfully");
        } catch (Exception e) {
            return new IsError(e.getMessage());

        }
    }

    public Response<Boolean>  set_broken_product(String id,String catalog_number)
    {
        try {
            boolean answer=mystock.set_product_broken(Integer.parseInt(id),Integer.parseInt(catalog_number));
            return new IsOk(answer,"done successfully");
        }
        catch (Exception e){
            return new IsError(e.getMessage());
        }

    }


    public Response<String> check_shortage_order() {

        try {

            String str=mystock.shortage_order();

            return new IsOk(str,"done successfully");
        }
        catch (Exception e){
            return new IsError(e.getMessage());
        }
    }

    public Response<String> check_periodic_order() {

        try {

            return new IsOk(mystock.periodic_order(),"done successfully");
        }
        catch (Exception e){
            return new IsError(e.getMessage());
        }
    }

    public Response update_periodic_order_quantity(String id, String new_quantity) throws Exception {
        try {
            mystock.update_periodic_order_quantity(Integer.parseInt(id),Integer.parseInt(new_quantity));
            return new IsOk(null,"done successfully");
        }
        catch (Exception e){
            return new IsError(e.getMessage());
        }


    }

    public Response update_periodic_order_day(String id, String day) throws Exception {
        try {
            mystock.update_periodic_order_day(Integer.parseInt(id),day);
            return new IsOk(null,"done successfully");
        }
        catch (Exception e){
            return new IsError(e.getMessage());
        }

    }

    public Response remove_periodic_order(String id) throws Exception {
        try {
            mystock.remove_periodic_order(Integer.parseInt(id));
            return new IsOk(null,"done successfully");
        }
        catch (Exception e){
            return new IsError(e.getMessage());
        }

    }

    public Response remove_sale(String id) throws Exception {
        try {
            mystock.remove_sale(Integer.parseInt(id));
            return new IsOk(null,"done successfully");
        }
        catch (Exception e){
            return new IsError(e.getMessage());
        }

    }

    public Response remove_broken_items() {
        try {
            mystock.remove_defective_and_expired_products();
            return new IsOk(null,"done successfully");
        }
        catch (Exception e){
            return new IsError(e.getMessage());
        }

    }
}
