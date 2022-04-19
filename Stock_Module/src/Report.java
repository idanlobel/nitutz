import java.util.List;

public class Report {
    //maybe we make it abstarct
    //every subject will be a subclass of report
    public enum Subject {
        sales_report,prices_report, stock_report , order_report , defective_items_report , expired_items_report
    }

    private Subject subject;
    private int id;


    public Report (Subject s,int id)
    {
        this.subject=s;
        this.id=id;
    }

    public void Fill_Me(List<Products> products ,List<Sale> sales,List<Product> every_product)
    {
        switch (subject) {
            case stock_report: {
                for (Products p:products) {
                    if(p.getQuantity()>0) {
                        for(Product product:p.getProduct_list())
                        {
                            System.out.println("name : " + p.getName() + "  location: " + product.getLocation() +  "  manufacteror: "+p.getManufactorer() + "   storage quantity:  " +p.getStorage_quantity()+ "   shelf quantity: " +p.getShelf_quantity()+ "  store quantity: " +p.get_store_quantity());
                        }
                    }

                }
            }
            case prices_report:{

                    for(Product product:every_product)
                    {
                        if(product.isSold())
                        {
                            System.out.println("name:   "+product.getName()+"  cost price: "+product.getcostprice()+"  sell price:  "+product.getsoldprice());
                        }
                    }

            }
            case sales_report:{
                for(Products p :products)
                {
                    System.out.println("name:   "+p.getName());
                    for(Integer sale_id:p.getSales_history())
                    {
                        for(Sale s:sales)
                        {
                            if(s.getId()==sale_id)
                            {
                                System.out.println("start date:  "+s.getStart_date() + "end date:   "+s.getEnd_date()+"  reason:   "+s.getReason());
                            }
                        }
                    }
                }
            }
            case defective_items_report:{
                for(Product p:every_product)
                {
                    if(p.isBroken())
                    {
                        System.out.println("name:  "+p.getName()+"   is broken or defective!");
                    }
                }
            }
        }
    }


}
