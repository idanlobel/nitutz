package src.TransportationsAndWorkersModule.Service.ServiceWorkers;

public class WeeklyScheduleSL {
    ShiftSL[][] shiftSLS;
    String site;
    public WeeklyScheduleSL(ShiftSL[][] shiftSLS, String site) {
        this.shiftSLS = shiftSLS;
        this.site = site;
    }
    public ShiftSL[][] getShiftSLS() {
        return shiftSLS;
    }

    @Override
    public String toString() {
        String s = "name of the site of this weekly schedule is: "+site +"\n";
        for (int i =0; i<5; i++){
            s+= "day "+i + " shift morning: "+shiftSLS[i][0].toString() + "\n"
            + "shift evening: "+shiftSLS[i][1].toString()+"\n";
        }
        return s;
    }
}
