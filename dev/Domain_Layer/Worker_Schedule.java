package Domain_Layer;

public class Worker_Schedule {
    int schedule_ID;
    int worker_ID;
    boolean [][] schedule;

    //TODO:: צריך לבדוק פה שזה לא קיים כבר בדטאבייס...
    public Worker_Schedule(int schedule_ID, int worker_ID, boolean[][] schedule) {
        this.schedule_ID = schedule_ID;
        this.worker_ID = worker_ID;
        this.schedule = schedule;
    }
}
