package busniess_layer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Report {
    //maybe we make it abstarct
    //every subject will be a subclass of report
    public enum Subject {
        sales_report,prices_report, stock_report , order_report , defective_items_report , sortedby_expiry_report
    }

    private Subject subject;
    private int id;
    private List<Products> products;
    private  List<Sale> sales;
    private List<Product> every_product;


    public Report (Subject s,int id,List<Products> products ,List<Sale> sales,List<Product> every_product)
    {
        this.subject=s;
        this.id=id;
        this.products=products;
        this.sales=sales;
        this.every_product=every_product;

    }

    public void Fill_Me()
    {
        switch (subject) {
            case stock_report: {
                for (Products p:products) {
                    if(p.getQuantity()==0) {
                       this.products.remove(p);
                    }

                }
                break;
            }
            case prices_report:{

                List<Product> newlist=new ArrayList<>();
                    for(Product product:every_product)
                    {
                        if(!product.isSold())
                        {
                           newlist.add(product);
                        }
                    }
                    this.every_product=newlist;
                    break;

            }
            case sales_report:{
                boolean found_sale=false;
                for(Products p :products)
                {

                    for(Integer sale_id:p.getSales_history())
                    {
                        for(Sale s:sales)
                        {
                            if(s.getId()==sale_id)
                            {
                                found_sale=true;

                            }
                        }
                    }
                    if(!found_sale)
                    {
                        this.products.remove(p);
                    }
                }
                break;
            }
            case defective_items_report:{
                for(Product p:every_product)
                {
                    if(!p.isBroken())
                    {
                       this.every_product.remove(p);
                    }
                }
                break;
            }
            case sortedby_expiry_report:{
                //do nothing, maybe add later if sold then remove
                break;
            }
        }
    }

    public String tostring()
    {
        String answer="";

        switch (subject) {
            case stock_report: {
                for (Products p : products) {
                    for (Product product : p.getProduct_list()) {
                        answer += "name : " + p.getName() + "  location: " + product.getLocation() + "  manufacteror: " + p.getManufactorer() + "   storage quantity:  " + p.getStorage_quantity() + "   shelf quantity: " + p.getShelf_quantity() + "  store quantity: " + p.get_store_quantity() + "\n";
                    }


                }
                break;
            }

            case prices_report:{
                int counter=0;
                for(Product product:every_product)
                {
                    if(product.isSold())
                    {
                        answer+="name:   "+product.getName()+"  cost price: "+product.getcostprice()+"  sell price:  "+product.getsoldprice()+"\n";
                        counter++;
                    }
                }
                if(counter==0)
                    answer="nothing sold!";
                break;

            }

            case sales_report:{
                for(Products p :products)
                {
                    answer+="name:   "+p.getName()+"\n";
                    for(Integer sale_id:p.getSales_history())
                    {
                        for(Sale s:sales)
                        {
                            if(s.getId()==sale_id)
                            {

                                answer +="start date:  "+s.getStart_date() + "    end date:   "+s.getEnd_date()+"  reason:   "+s.getReason()+"\n";
                            }
                        }
                    }

                }
                break;
            }

            case defective_items_report:{
                for(Product p:every_product)
                {

                       answer+="name:  "+p.getName()+"   is broken or defective! \n";

                }
                break;
            }
            case sortedby_expiry_report:{
                for(Product p:every_product)
                {
                    if(p.getExpire_date().isBefore(LocalDate.now()))
                    {
                        answer+="name:  "+p.getName()+"location:  "+p.getLocation()+"expiry date:  "+p.getExpire_date()+"\n";
                        answer+="has expired!!!!!!\n";
                    }
                    else
                    {
                        answer+="name:  "+p.getName()+"location:  "+p.getLocation()+"expiry date:  "+p.getExpire_date()+"\n";
                    }
                }
            }
            break;




        }

        return answer;

    }


}
