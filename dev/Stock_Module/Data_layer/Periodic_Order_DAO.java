package Stock_Module.Data_layer;

import Stock_Module.busniess_layer.Periodic_Order;
import java.sql.*;
import java.util.WeakHashMap;

public class Periodic_Order_DAO {


    private String table_name="Periodic_Order";
    private String connection_string;
    private WeakHashMap<Integer, Periodic_Order> periodicOrdersMap;

    public Periodic_Order_DAO(String connection_string)
    {
        this.connection_string=connection_string;
        create_table(connection_string);
        periodicOrdersMap=new WeakHashMap<>();
    }

    public Periodic_Order getPeriodicOrder(int id){
        Periodic_Order p= periodicOrdersMap.get(id);
        if(p==null)
            p=readPeriodicOrder(id);
        return p;
    }

    private Periodic_Order readPeriodicOrder(int id) {
        String sql = "SELECT * FROM "+table_name+ " WHERE id="+id;

        try{
            Connection conn = DriverManager.getConnection(connection_string);
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(sql);
          Integer id_=Integer.parseInt(res.getObject(1).toString());
          String day_of_week=res.getObject(2).toString();
          Long catalog_number=Long.parseLong(res.getObject(3).toString());
            Integer quanitiy=Integer.parseInt(res.getObject(4).toString());
            String manufactore=res.getObject(5).toString();
            String main_cat=res.getObject(6).toString();
            String sub_cat=res.getObject(7).toString();
            String sub_sub_cat=res.getObject(8).toString();
            String name=res.getObject(9).toString();
            Double cost_price=Double.parseDouble(res.getObject(10).toString());
            Periodic_Order order=new Periodic_Order(id_,day_of_week,catalog_number,quanitiy,cost_price,name,manufactore,main_cat,sub_cat,sub_sub_cat,this);
            stmt.close();
            conn.close();

           return order;

        } catch (SQLException e) {
            return null;
        }
    }

    public Connection connect() {
        Connection conn = null;
        try {


            // create a connection to the database
            conn = DriverManager.getConnection(connection_string);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {

            }
        }
        return conn;
    }


    private void create_table(String connection_string) {


        String sql = "CREATE TABLE IF NOT EXISTS Periodic_Order (\n"
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + " day_of_week text,\n"
                + " catalog_number long,\n"
                + " quantity integer,\n"
                + " manufacturer text,\n"
                + " main_category text,\n"
                + " sub_category text,\n"
                + " sub_sub_category text,\n"
                + " name text,\n"
                + " cost double,\n"
                + "  FOREIGN KEY (catalog_number) REFERENCES Products(catalog_number)\n"
                + ");";


        try{
            Connection conn = DriverManager.getConnection(connection_string);
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public int get_current_id()
    {
        String query = "select seq from sqlite_sequence WHERE name = 'Periodic_Order'";

        try{
            Connection conn = DriverManager.getConnection(connection_string);
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(query);
            int current_id=res.getInt(1);
            conn.close();

            return current_id;

        } catch (SQLException e) {

        }
        return -1;

    }

    public void insert(String day_of_week, long catalog_number,int quantity,String manufactorer,String category,String sub_category,String sub_sub_category,String name,Double cost) {
        String sql = "INSERT INTO Periodic_Order(day_of_week, catalog_number, quantity, manufacturer, main_category, sub_category, sub_sub_category, name, cost) VALUES(?,?,?,?,?,?,?,?,?)";

        try{
            Connection conn = DriverManager.getConnection(connection_string);
            PreparedStatement pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, day_of_week);
            pstmt.setLong(2,catalog_number);
            pstmt.setInt(3,quantity);
            pstmt.setString(4,manufactorer);
            pstmt.setString(5,category);
            pstmt.setString(6,sub_category);
            pstmt.setString(7,sub_sub_category);
            pstmt.setString(8,name);
            pstmt.setDouble(9,cost);


            pstmt.executeUpdate();
pstmt.close();
            conn.close();
        } catch (SQLException e) {

        }
    }

    public void delete_periodic_order(int id)
    {
        String sql = "DELETE FROM "+table_name+" WHERE id = ?";
        try{
            Connection conn = DriverManager.getConnection(connection_string);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,id);
            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void update_periodic_order_quantity(int id,int quantity)
    {
        String sql="UPDATE "+table_name+" SET quantity=? WHERE id=?";
        try{
            Connection conn = DriverManager.getConnection(connection_string);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,quantity);
            pstmt.setInt(2,id);

            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {

        }
    }

    public void update_periodic_order_day(int id, String day) {
        String sql="UPDATE "+table_name+" SET day=? WHERE id=?";
        try{
            Connection conn = DriverManager.getConnection(connection_string);
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,day);
            pstmt.setInt(2,id);

            pstmt.executeUpdate();
            pstmt.close();
            conn.close();
        } catch (SQLException e) {

        }
    }

    public void insert_in_map(int id, Periodic_Order periodic_order) {
        this.periodicOrdersMap.put(id,periodic_order);
    }

    public int check_if_exists(String day_of_week, long catalog_number, int quantity, String manufactorer, String category, String sub_cat, String sub_sub_cat, String name, double cost) {
        String sql = "SELECT id FROM "+table_name+ " WHERE day_of_week='"+day_of_week +"' AND catalog_number= "+catalog_number+" AND quantity= "+quantity+" AND manufacturer= '"+manufactorer+"' AND main_category= '"+category+"' AND sub_category= '"+sub_cat+"' AND sub_sub_category= '"+sub_sub_cat+"' AND name= '"+name+"' AND cost= "+cost;

        try{
            Connection conn = DriverManager.getConnection(connection_string);
            Statement stmt = conn.createStatement();
            ResultSet res = stmt.executeQuery(sql);

            if(res.next()) {
                Integer id_ = Integer.parseInt(res.getObject(1).toString());
                stmt.close();
                conn.close();
                return id_;
            }
        stmt.close();
            conn.close();

            return -1;



        } catch (SQLException e) {
            return -1;
        }
    }
    }

