package src.TransportationsAndWorkersModule.Service.ServiceWorkers;

public class Resp {
    public String ErrorMessage;
    public boolean ErrorOccured() {
        return ErrorMessage!=null;
    }
    Resp() { }
    Resp(String msg)
    {
        this.ErrorMessage = msg;
    }
}
