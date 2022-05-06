package src.transportationsModule.Dal;

import src.transportationsModule.BusinessLogic.TransportForm;

import java.util.List;

public class TransportsRep {



    List<TransportForm> transportFormsCache;
    //connection sqlite connection
    //Map<,> trucksIdentityMap

    public TransportsRep() {

    }

    public List<TransportForm> getTransportFormsCache() {
        return transportFormsCache;
    }

    public void addTransportForm(TransportForm transportForm){

        System.out.println(transportForm.getId() + " added to transports cache");
    }

    public TransportForm getTransportForm(){
        return null;
    }


}
