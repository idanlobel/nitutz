package src.TransportationsAndWorkersModule.Dal.Workers;



import src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.Workers.BankAccount;
import src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.Workers.EmploymentConditions;
import src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.Workers.HR;
import src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.Workers.Worker;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

public class WorkerDAO {
    private boolean readAll = false;
    private static HashMap<Integer, Worker> cacheWorkers = new HashMap<>();
    private static HR hr;

    private static boolean retry = false;
    private static SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
    public Worker get(int id) throws Exception{
        Worker worker = cacheWorkers.get(id);
        if (worker!=null) return  worker;
        Connection conn=null;
        try {
            conn = DatabaseManager.getInstance().connect();
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select * from workers where id = '"+id+"'");
            if ( rs.next() ) {
                int worker_id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                String password = rs.getString("password");
                int bank_id = rs.getInt("bank_id");
                int bank_branch = rs.getInt("bank_branch");
                int salary = rs.getInt("salary");
                String rec = rs.getString("rec_date");
                Date date=format.parse(rec);
                //need to change to date
                worker = new Worker(name,worker_id,password,email,new BankAccount(bank_id,bank_branch),new EmploymentConditions(salary,date),new LinkedList<>());
                conn.close();
                conn = DatabaseManager.getInstance().connect();
                ResultSet rs2 = conn.createStatement().executeQuery("select * from workerJobs where worker_id = '"+id+"'");//read from worker jobs
                while (rs2.next()){
                    String job = rs2.getString("job");
                    worker.getWorkerJobs().add(job);
                }
            }
        } catch (Exception e) {
            if (!retry){retry=true; get(id);}
            else{
                retry=false;
                throw new Exception(e.getMessage());
            }
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                throw new Exception(ex.getMessage());
            }
        }
        if (worker==null)throw new Exception("worker doesn't exist");
        cacheWorkers.put(worker.getId(),worker);
        return worker;
        //take from the db and insert to the cache then return
    }
    public List<Worker> getAllWorkers() throws Exception{
        List<Worker> list = new ArrayList();
        if (!readAll){
            //read from DB and insert into cache workers;
            Connection conn=null;
            List<Integer> workerIDS = new LinkedList<>();
            try {
                conn = DatabaseManager.getInstance().connect();
                Statement statement = conn.createStatement();
                ResultSet rs = statement.executeQuery("select id from workers");
                while ( rs.next() ) {
                    int worker_id = rs.getInt("id");
                    workerIDS.add(worker_id);
                }
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            } finally {
                try {
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException ex) {
                    throw new Exception(ex.getMessage());
                }
            }
            for (int id:workerIDS) {
                Worker worker = get(id);
                if (!cacheWorkers.containsKey(worker.getId()))cacheWorkers.put(worker.getId(),worker);
            }
            readAll = true;
        }
        list.addAll(cacheWorkers.values());
        return list;
    }
    public void create(Worker worker) throws Exception {
        if (cacheWorkers.containsKey(worker.getId())) throw new Exception("worker already exists");
        Connection conn=null;
        String sql = "INSERT INTO workers(id,name,password,email,bank_id,bank_branch,salary,rec_date) VALUES(";
        try {
            conn = DatabaseManager.getInstance().connect();
            Statement rs = conn.createStatement();
            String date = format.format(worker.getEmploymentConditions().getRecruitmentDate());
            //set worker data
            sql+="'"+worker.getId()+"','"+worker.getName()+"','"+worker.getPassword()+"','"+worker.getEmail_address()+
                    "','"+worker.getBankAccount().getBankID()+"','"+worker.getBankAccount().getBranch()+
                    "','"+worker.getEmploymentConditions().getSalary()+"','"+date+"');";
            rs.addBatch(sql);
            //set his jobs;
            for (String job:worker.getWorkerJobs()) {
                String sql2="\nINSERT INTO workerJobs(worker_id,job) VALUES('"+worker.getId()+"','"+job+"');";
                rs.addBatch(sql2);
            }
            //PreparedStatement rs = conn.prepareStatement(sql);
            rs.executeBatch();
            conn.commit();
            cacheWorkers.put(worker.getId(),worker);
        } catch(Exception e) {
            if (!retry){retry=true; create(worker);}
            else{
                retry=false;
                throw new Exception(e.getMessage());
            }
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                throw new Exception(ex.getMessage());
            }
        }
        //dont forget to add all the 2*5 presence needed for this worker id as false so its woker schedule will exist as well
        cacheWorkers.put(worker.getId(),worker);
    }
    public void update(Worker worker) throws Exception {
        if (!cacheWorkers.containsKey(worker.getId())) throw new Exception("worker doesn't exist");
        //update to db first
        try {
            delete(worker.getId());
            create(worker);
        }catch (Exception e){
            System.out.println("failed worker update");
            throw new Exception(e.getMessage());
        }
        //maybe its better to actually make a delete sql instead but for now this will be it
        cacheWorkers.put(worker.getId(), worker);
    }
    public void delete(int id) throws Exception{
        Connection conn=null;
        String sql = "DELETE from workers where id = '"+id+"';";
        String sql2 = "DELETE from workerJobs where worker_id = '"+id+"';";
        try {
            conn = DatabaseManager.getInstance().connect();
            Statement rs = conn.createStatement();
            rs.addBatch(sql);
            rs.addBatch(sql2);
            rs.executeBatch();
            conn.commit();
            cacheWorkers.remove(id);
        } catch(Exception e) {
            if (!retry){retry=true; delete(id);}
            else{
                retry=false;
                throw new Exception(e.getMessage());
            }
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                throw new Exception(ex.getMessage());
            }
        }
    }

    public HR readHR() throws Exception {
        if (hr==null) {
            int worker_id = -1;
            Connection conn=null;
            try {
                conn = DatabaseManager.getInstance().connect();
                Statement statement = conn.createStatement();
                String search = "HR";
                ResultSet rs = statement.executeQuery("select * from workerJobs where job = '"+search+"'");
                if (rs.next()) {
                     worker_id= rs.getInt("worker_id");

                }
            } catch (Exception e) {
                    throw new Exception(e.getMessage());

            } finally {
                try {
                    if (conn != null) {
                        conn.close();
                    }
                } catch (SQLException ex) {
                    throw new Exception(ex.getMessage());
                }
            }
            Worker worker;
            try {
                worker= get(worker_id);
                hr = new HR(worker.getName(),worker.getId(),worker.getPassword(),
                        worker.getEmail_address(),worker.getBankAccount(),worker.getEmploymentConditions(),worker.getWorkerJobs());
            }catch (Exception e){
                throw new Exception(e.getMessage());
            }
        }
        return hr;
    }

    public boolean exists(int id) throws Exception {
        Worker worker = cacheWorkers.get(id);
        if (worker!=null) return true;
        Connection conn=null;
        try {
            conn = DatabaseManager.getInstance().connect();
            Statement statement = conn.createStatement();
            ResultSet rs = statement.executeQuery("select * from workers where id = "+id);
            if ( rs.next() ) return true;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                throw new Exception(ex.getMessage());
            }
        }
        return false;
    }
}
