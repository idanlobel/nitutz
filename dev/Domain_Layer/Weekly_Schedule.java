package Domain_Layer;

public class Weekly_Schedule {
    int schedule_ID;
    Shift[][] schedule;

    //TODO:: צריך לבדוק פה שזה לא קיים כבר בדטאבייס...
    public Weekly_Schedule(int schedule_ID, Shift[][] schedule) {
        this.schedule_ID = schedule_ID;
        this.schedule = schedule;
    }
}
