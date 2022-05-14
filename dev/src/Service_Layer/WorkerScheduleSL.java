package src.Service_Layer;

public class WorkerScheduleSL {
    boolean [][] schedule;

    public boolean[][] getSchedule() {
        return schedule;
    }

    public WorkerScheduleSL(boolean[][] schedule) {
        this.schedule = schedule;
    }

    @Override
    public String toString() {
        String s = "";
        for (int i =0; i<5; i++){
            String presentDay = "not present";
            String presendEvening = "not present";
            if (schedule[i][0])presentDay = "present";
            if (schedule[i][1])presendEvening = "presend";
            s+= "day "+i + " shift morning: "+presentDay + "\n"
                    + "shift evening: "+presendEvening+"\n";
        }
        return s;
    }
}
