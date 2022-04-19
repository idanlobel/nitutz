package Domain_Layer;

import java.util.List;

public class Shift {
    enum Type {Morning, Evening};
    Shift_Manager shift_manager;
    List<Worker> workers;

    public Shift(Shift_Manager shift_manager, List<Worker> workers) {
        this.shift_manager = shift_manager;
        this.workers = workers;
    }
}
