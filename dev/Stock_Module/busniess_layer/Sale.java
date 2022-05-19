package Stock_Module.busniess_layer;

import Stock_Module.Data_layer.DAL_controller;
import Stock_Module.Data_layer.Sale_DAO;
import Stock_Module.Data_layer.SalesHistoryDAO;

import java.util.ArrayList;
import java.util.List;


public class Sale {
    private double percentage;
    private int ID;
    private String start_date;
    private String end_date;
    private String reason;
    private List<Products> products_in_sale=new ArrayList<>();
    private Sale_DAO sale_dao;
    private SalesHistoryDAO salesHistoryDAO;


    public Sale(double percentage,String start_date, String end_date, String reason,Sale_DAO sale_dao) {
        this.percentage = percentage/100;
        this.start_date = start_date;
        this.end_date = end_date;
        this.reason = reason;
        this.sale_dao=sale_dao;
        int id_=sale_dao.check_if_exists(percentage,start_date,end_date,reason);
        if(id_==-1)
        {
            this.sale_dao.insert(percentage,start_date,end_date,reason);
            this.ID=this.sale_dao.get_current_id();
        }
        else
        {
            this.ID=id_;
        }
        this.salesHistoryDAO= DAL_controller.getInstance().getSales_history_table();



//        this.products_in_sale.add(product);

    }

    public Sale(int id,double percentage,String start_date, String end_date, String reason,Sale_DAO sale_dao) {
        this.percentage = percentage;
        this.start_date = start_date;
        this.end_date = end_date;
        this.reason = reason;
        this.sale_dao=sale_dao;
        this.ID=id;
        this.salesHistoryDAO= DAL_controller.getInstance().getSales_history_table();



    }
    public int getId()
    {
        return this.ID;
    }

    public void Add_Products(Products p)  {

        this.products_in_sale.add(p);
        p.Record_sale(percentage,start_date,this.ID);
        salesHistoryDAO.insert(ID,p.getCatalog_number());
    }

    public void sale_is_over()
    {
        for (Products products:this.products_in_sale) {
            products.sale_is_over(this.percentage);

        }
    }

    public void SetPercentage(double percentage) throws Exception {
        if(percentage<0 || percentage >100)
        {
            throw new Exception("illegal percentage , it must be 0<x<=100");
        }
        this.percentage=percentage;
    }

    public String getStart_date()
    {
        return this.start_date;
    }
    public String getEnd_date()
    {
        return this.end_date;
    }

    public Double getpercentage()
    {
        return this.percentage;
    }

    public String getReason()
    {
        return this.reason;
    }
    public List<Products> getProducts_in_sale()
    {
        return products_in_sale;
    }


}
