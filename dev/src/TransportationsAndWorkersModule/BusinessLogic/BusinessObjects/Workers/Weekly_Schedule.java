package src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.Workers;

public class Weekly_Schedule {
    Shift[][] schedule;
    int id;

    public int getId() {
        return id;
    }

    //TODO:: צריך לבדוק פה שזה לא קיים כבר בדטאבייס...
    public Weekly_Schedule( Shift[][] schedule, int id) {
        this.schedule = schedule;
        this.id = id;
    }
    public Weekly_Schedule(int weekID) {
        this.schedule = new Shift[5][2];
        for (int i =0; i<5; i++){
            for (int j = 0; j<2; j++){
                schedule[i][j] = new Shift();
            }
        }
        this.id=weekID;
    }
    public Shift getShift(int day, int shiftType)throws Exception{
        if ((day > 5 || day < 0) || (shiftType!= 0 && shiftType!= 1))
            throw new Exception("Please make sure you've entered legal values for day and shift type");
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
