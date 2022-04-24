package src.Service_Layer;

public class WeeklyScheduleSL {
    ShiftSL[][] shiftSLS;
    public WeeklyScheduleSL(ShiftSL[][] shiftSLS) {
        this.shiftSLS = shiftSLS;
    }
    public ShiftSL[][] getShiftSLS() {
        return shiftSLS;
    }

    @Override
    public String toString() {
        String s = "";
        for (int i =0; i<5; i++){
            s+= "day "+i + " shift morning: "+shiftSLS[i][0].toString() + "\n"
            + "shift evening: "+shiftSLS[i][1].toString()+"\n";
        }
        return s;
    }
}
