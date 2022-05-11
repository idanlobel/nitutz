package busniess_layer;

import Data_layer.DAL_controller;
import Data_layer.PricesHistoryDAO;
import Data_layer.Product_DAO;

public class ProductPrice {


    private int id;
    private long catalog_number;
    private double price;
    private String start_date;
    private String end_date;
    private PricesHistoryDAO pricesHistoryDAO;

    public ProductPrice(long catalog_number, double price, String start_date, String end_date) {
        this.catalog_number = catalog_number;
        this.price = price;
        this.start_date = start_date;
        this.end_date = end_date;
        this.id=id;
        pricesHistoryDAO= DAL_controller.getInstance().getPricesHistoryDAO();
        pricesHistoryDAO.insert(catalog_number,start_date,end_date,price);
    }

    public long getCatalog_number() {
        return catalog_number;
    }


    public double getPrice() {
        return price;
    }

    public String getStart_date() {
        return start_date;
    }


    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
        pricesHistoryDAO.update_end_date(id,end_date);
    }
    public int getId() {
        return id;
    }

}
