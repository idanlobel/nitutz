package src.Domain_Layer.BusinessObjects;

public class Worker_Schedule {

    boolean [][] schedule;
    int id;

    public int getId() {
        return id;
    }

    //TODO:: צריך לבדוק פה שזה לא קיים כבר בדטאבייס...
    public Worker_Schedule(boolean[][] schedule, int worker_id) {
        this.schedule = schedule; this.id = worker_id;
    }
    public Worker_Schedule(int id) {
        this.id = id;
        this.schedule = new boolean[5][2];
        for(int i =0; i<5; i++){
            for(int j =0; j< 2; j++)
            {
                schedule[i][j] = false;
            }
        }
    }
    public void editShiftPresence(boolean present, int day, int shift) throws Exception{
        if ((day > 5 || day < 0) || (shift!= 0 && shift!= 1))
            throw new Exception("Please make sure you've entered legal values for day and shift type");
        schedule[day][shift] = present;
    }
    public boolean[][] getSchedule() {
        return schedule;
    }
}
