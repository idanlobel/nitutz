package src.Domain_Layer.BusinessObjects;

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
    public Shift[][] getSchedule() throws Exception {
        if(schedule != null) {
            return schedule;
        }
        else {
            throw new Exception("There's no such schedule");
        }
    }

    public boolean isWeeklyScheduleReady(){
        for(int i =0; i <5; i++){
            for(int j= 0; j<2; j++){
                if(!schedule[i][j].isShiftIsReady())
                    return false;
            }
        }
        return true;
    }
}
