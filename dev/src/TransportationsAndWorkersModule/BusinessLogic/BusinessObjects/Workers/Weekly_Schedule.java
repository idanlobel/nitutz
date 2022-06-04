package src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.Workers;

public class Weekly_Schedule {
    Shift[][] schedule;

    String date;
    public String getSite() {
        return site;
    }

    String site;


    //TODO:: צריך לבדוק פה שזה לא קיים כבר בדטאבייס...
    public Weekly_Schedule( Shift[][] schedule, String date, String site) {
        this.schedule = schedule;
        this.date=date;
        this.site=site;
    }
    public Weekly_Schedule(String site, String date) {
        this.schedule = new Shift[5][2];
        this.site=site;
        this.date=date;
        for (int i =0; i<5; i++){
            for (int j = 0; j<2; j++){
                schedule[i][j] = new Shift();
            }
        }

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

    public String getId() {
        return date;
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
