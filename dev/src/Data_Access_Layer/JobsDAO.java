package src.Data_Access_Layer;

import src.Domain_Layer.BusinessObjects.Worker;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class JobsDAO {
    private static boolean readAll = false;
    private static List<String> cacheJobs = new LinkedList<>();
    public JobsDAO(){

    }
    public boolean exists(String job) throws Exception {
        if (!cacheJobs.contains(job)){
            //read from db
            //if failed return false, else true;
            return false;
        }
        return true;
    }
    public List<String> getAllJobs() throws Exception{
        if (readAll)return cacheJobs;
        //read from DB and insert into cache workers;
        readAll = true;
        return cacheJobs;
    }
    public void add(String job) throws Exception {
        if (cacheJobs.contains(job)) throw new Exception("worker already exists");
        //insert to db first
        //dont forget to add all the 2*5 presence needed for this worker id as false so its woker schedule will exist as well
        cacheJobs.add(job);
    }
    public void delete(String job)throws Exception{
        //throw exception only if db throws it a second time or somesht.
        //delete from db first
        cacheJobs.remove(job);
    }
}
