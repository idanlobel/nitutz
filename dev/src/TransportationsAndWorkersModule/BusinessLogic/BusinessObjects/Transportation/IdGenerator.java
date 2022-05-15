package src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.Transportation;

import java.time.LocalTime;

public class IdGenerator {

    int IntId = 1;

    public String getId(){
        String nextId = LocalTime.now()+ ""+IntId+"" ;
        IntId++;
        return nextId;
    }

}
