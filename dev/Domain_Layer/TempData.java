package Domain_Layer;

import java.util.LinkedList;
import java.util.List;

public class TempData {
    private static TempData tmpData = null;

    List<Worker> workers;

    private TempData() {
        //TODO:: צריך לשנות פה שיקרא מקובץ טקסט במקום ויכניס לתוך הרשימה...
        this.workers = new LinkedList<>();
    }

    public static TempData getInstance()
    {
        if (tmpData == null)
            tmpData = new TempData();
        return tmpData;
    }

    public int containsWorker(String userName, String password){
        for(Worker w : workers){
            if(w.name == userName && w.passwordsMatch(password)){
                return w.id;
            }
        }
        return -1; //The login info is incorrect.
    }
}
