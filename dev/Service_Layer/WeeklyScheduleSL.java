package Service_Layer;

public class WeeklyScheduleSL {
    ShiftSL[][] shiftSLS;
    public WeeklyScheduleSL(ShiftSL[][] shiftSLS) {
        this.shiftSLS = shiftSLS;
    }
    public ShiftSL[][] getShiftSLS() {
        return shiftSLS;
    }
}
