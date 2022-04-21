package Domain_Layer.BusinessObjects;

public class Weekly_Schedule {
    Shift[][] schedule;
    //TODO:: צריך לבדוק פה שזה לא קיים כבר בדטאבייס...
    public Weekly_Schedule( Shift[][] schedule) {
        this.schedule = schedule;
    }
    public Weekly_Schedule() {
        this.schedule = new Shift[5][2];
    }
    public Shift getShift(int day, int shiftType){
        if (schedule[day][shiftType]==null) schedule[day][shiftType] = new Shift();
        return schedule[day][shiftType];
    }
    public Shift[][] getSchedule() {
        if(schedule != null) {
            return schedule;
        }
        return null;
    }
}
