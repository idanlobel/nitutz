package Service;

public class IdGenerator {

    int IntId = 1;

    public String getId(){
        String nextId = ""+IntId+"" ;
        IntId++;
        return nextId;
    }

}
