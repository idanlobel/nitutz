import java.security.KeyPair;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;


public class Sale {
    private double percentage;
    private int ID;
    private LocalDate start_date;
    private LocalDate end_date;
    private String reason;
    private List<Products> products_in_sale=new ArrayList<>();



    public Sale(double percentage,int Id, LocalDate start_date, LocalDate end_date, String reason) {
        this.percentage = percentage;
        this.start_date = start_date;
        this.end_date = end_date;
        this.reason = reason;


//        this.products_in_sale.add(product);
        this.ID=Id;
    }
    public int getId()
    {
        return this.ID;
    }

    public void Add_Products(Products p) throws Exception {
        if(this.percentage<0 | this.percentage>1)
        {
            throw new Exception("percenrage is illegal, change percentage");
        }
        this.products_in_sale.add(p);
        p.Record_sale(percentage,start_date,this.ID);

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

    public LocalDate getStart_date()
    {
        return this.start_date;
    }
    public LocalDate getEnd_date()
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


}
