package service_layer;

import busniess_layer.Stock;

import java.time.LocalDate;

public class Stock_Manager {

    private Stock mystock;


    public Stock_Manager()
    {
        mystock=new Stock();
    }




    public void make_order(String product_catalog_number, String quantity, String cost, String expiry,String name, String manufactorer, String category, String sub_cat, String sub_sub_cat)
    {

        mystock.Order(Long.parseLong(product_catalog_number),Integer.parseInt(quantity),Double.parseDouble(cost),expiry,name,manufactorer,category,sub_cat,sub_sub_cat);
    }

    public void make_sale(String products_catalog_number, String startdate,String enddate , String reason, String percentage) throws Exception {


        mystock.Sale(Long.parseLong(products_catalog_number),startdate,enddate,reason,Double.parseDouble(percentage));
    }

    public void add_product_to_sale(String products_catalog_number,String sale_id) throws Exception {
        mystock.Add_to_sale(Long.parseLong(products_catalog_number),Integer.parseInt(sale_id));
    }

    public void make_sale_by_catgeory(String startdate,String enddate , String reason, String percentage,String category,String sub_cat,String sub_sub_cat) throws Exception
    {
        mystock.sale_by_category(startdate,enddate,reason,Double.parseDouble(percentage),category,sub_cat,sub_sub_cat);
    }

    public Service_Report show_by_catgeory(String main_cat,String sub_cat,String sub_sub_cat)
    {
        return new Service_Report(mystock.get_products_by_categories(main_cat,sub_cat,sub_sub_cat).tostring());
    }

    public Service_Report make_stock_report()
    {
        return new Service_Report(mystock.make_stock_report().tostring());


    }
    public Service_Report make_prices_report()
    {
        String str=mystock.make_prices_report().tostring();
       return new Service_Report(str);
    }


    public Service_Report make_sales_report()
    {
        return new Service_Report(mystock.make_sales_report().tostring());

    }

    public Service_Report make_defective_report()
    {

      return new Service_Report(this.mystock.make_defective_report().tostring());


    }

    public Service_Report make_expiry_report()
    {
        return new Service_Report(this.mystock.make_sorted_by_expiration_report().tostring());
    }

    public boolean set_broken_product(String id,String catalog_number)
    {
        return mystock.set_product_broken(Integer.parseInt(id),Integer.parseInt(catalog_number));
    }




}
