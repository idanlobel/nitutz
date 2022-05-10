package src.transportationsModule.Dal;

import src.transportationsModule.BusinessLogic.TransportForm;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TransportsRep {



    List<TransportForm> transportFormsCache;
    //connection sqlite connection
    //Map<,> trucksIdentityMap

    public TransportsRep() {
        transportFormsCache = new LinkedList<>();
    }

    public List<TransportForm> getTransportFormsCache() {
        return transportFormsCache;
    }

    public void addTransportForm(TransportForm transportForm){
    //todo add to DB
        System.out.println(transportForm.getId() + " added to transports cache");
        transportFormsCache.add(transportForm);
    }

    public TransportForm getTransportForm(){
        return null;
    }


    public List<String> getTransportFormsbyDate(String date) {
        //todo change to pull db
        List<String> formsIds=new ArrayList<>();
        for (TransportForm tf: transportFormsCache) {
            if(tf.getDate().equals(date))
                formsIds.add(tf.getId());
        }
        return  formsIds;
    }

    public TransportForm getTransportFormById(String id) {
        //todo change to pull db

        for (TransportForm tf: transportFormsCache) {
            if(tf.getId().equals(id))
                return tf;
        }
        return null;
    }
}
