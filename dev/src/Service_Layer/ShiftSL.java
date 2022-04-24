package src.Service_Layer;

import java.util.HashMap;
import java.util.List;

public class ShiftSL {
    List<WorkerSL> workers;
    HashMap<String,List<Integer>> jobToWorkers;
    public ShiftSL(List<WorkerSL> workers, int managerID, HashMap<String, List<Integer>> jobToWorkers) {
        this.workers = workers;
        this.managerID = managerID;
        this.jobToWorkers = jobToWorkers;
    }
    int managerID;

    public List<WorkerSL> getWorkers() {
        return workers;
    }

    public int getManagerID() {
        return managerID;
    }

    public String toString(){
        String s = "";
        if (managerID==-1) s+="shift manager doesn't exist yet for this shift\n";
        else s+="shift manager is: "+ managerID + "\n";
        if(workers != null) {
            s+="the workers are: ";
            int counter = 0;
            for (WorkerSL wk : workers) {
                s += wk.toString() + "\n";
                counter++;
            }
            s+="and the assigned jobs are: \n";
            if (jobToWorkers!=null){
                for (String job: jobToWorkers.keySet()){
                    s+="job: "+job +" does workers:\n";
                    for (int id: jobToWorkers.get(job)){
                        s+=id+", ";
                    }
                    s=s.substring(0, s.length() -1 );
                }
            }
            if (counter>0)s=s.substring(0, s.length() -1 );
        }
        else s+= " no workers yet";
        return s;
    }
}
