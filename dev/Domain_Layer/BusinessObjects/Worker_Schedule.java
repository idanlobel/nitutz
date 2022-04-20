package Domain_Layer.BusinessObjects;

public class Worker_Schedule {

    boolean [][] schedule;

    //TODO:: צריך לבדוק פה שזה לא קיים כבר בדטאבייס...
    public Worker_Schedule(boolean[][] schedule) {
        this.schedule = schedule;
    }
    public Worker_Schedule() {
        this.schedule = new boolean[5][2];
    }
    public void editShiftPresence(boolean present, int day, int shift){
        schedule[day][shift] = present;
    }
    public boolean[][] getSchedule() {
        return schedule;
    }
}
