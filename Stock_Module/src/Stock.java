import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Stock {
    private List<Products> products_list;
    private List<Sale> sales_list;


    public Stock() {

        this.products_list = new ArrayList<>();
        this.sales_list = new ArrayList<>();
    }


    //sale
    //returns a list of products that reached their minimal quanitity
    public List<Products> get_low_products() {
        List<Products> answerlist = new ArrayList<>();
        for (Products products : this.products_list) {
            if (products.getQuantity() <= products.getMin_quantity()) {
                answerlist.add(products);
            }

        }
        return answerlist;
    }

    //orders products with required quantity
    public void Order(int id, int quantity, Double cost, LocalDate expiry, String name, String manufactorer, String category) {
        boolean added = false;
        for (Products products : this.products_list) {
            if (products.getID() == id) {
                products.update_quantity(quantity, cost, expiry);
                added = true;
            }
        }
        if (!added) {
            //maybe we should ask the client if there is a default price for selling , for exmaple : cost * 1.5
            Products products = new Products(id, name, quantity, cost, cost * 1.5, expiry, manufactorer, category);
            products_list.add(products);
        }

    }

    public void Sale(int products_id, int sales_id, LocalDate startdate, LocalDate endate, String reason, double percentage) throws Exception {
        boolean foundsale = false;
        int index_of_sale = -1;
        //this for checks if this sale already exists and we want to add to it more products
        //if there isn't we will make new instance of sale
        for (Sale sale : this.sales_list) {
            if (sale.getId() == sales_id) {
                foundsale = true;
                index_of_sale = this.sales_list.indexOf(sale);
                break;

            }

        }
        //this checks the products list to add sale to products with provided id
        for (Products products : this.products_list) {
            if (products.getID() == products_id) {
                if (!foundsale) {

                    Sale new_sale = new Sale(percentage, LocalDate.now(), endate, reason);
                    new_sale.Add_Products(products);
                    this.sales_list.add(new_sale);


                } else {
                    this.sales_list.get(index_of_sale).Add_Products(products);

                }
            }

        }


    }

    public void remove_sale(int sale_id) {
        for (Sale sale : this.sales_list) {
            if (sale.getId() == sale_id) {
                sale.sale_is_over();

            }

        }
    }

    public Products get_products(int id) {
        for (Products products : this.products_list) {
            if (products.getID() == id) {
                return products;
            }


        }


        return null;


    }
    public List<Sale> getsales()
    {
        return  this.sales_list;
    }
}
