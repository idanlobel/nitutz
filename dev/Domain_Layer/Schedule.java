package Domain_Layer;

import java.util.Dictionary;
import java.util.HashMap;

public class Schedule {

    private static Schedule schedule = null;

    HashMap<Integer, Dictionary<Integer, Worker_Schedule>> workers_Schedules;
    HashMap<Integer, Weekly_Schedule> weekly_Schedules;

    private Schedule() {
        this.weekly_Schedules = new HashMap<>();
        this.workers_Schedules = new HashMap<>();
    }

    public static Schedule getInstance()
    {
        if (schedule == null)
            schedule = new Schedule();
        return schedule;
    }


    //TODO:: For now we can't implement this method because we don't know what the user would like to edit -
    // Later we can implement it by using a GUI interface.
   /* public boolean editWorkerSchedule(int workerID, int workerScheduleID){

    } */

    //TODO:: For now we can't implement this method because we don't know which shifts the Business_Layer.HR would like to add -
    // Later we can implement it by using a GUI interface.
   /* public boolean editWorkerSchedule(int workerID, int workerScheduleID){

    } */
}
