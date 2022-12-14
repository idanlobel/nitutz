package src.TransportationsAndWorkersModule.BusinessLogic.controllers.ControllersTransportation;

import src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.Transportation.TransportForm;
import src.TransportationsAndWorkersModule.Dal.Transportation.TransportsRep;
import src.TransportationsAndWorkersModule.Service.ServiceTransportation.ProductsDocumentSDTO;
import src.TransportationsAndWorkersModule.Service.ServiceTransportation.TransportSDTO;

import java.util.List;

/**
 * responsible creating/updating/getting a transport/transports
 */
public class TransportsController {

    TransportsRep transportsRep;

    public TransportsController() {
        transportsRep = new TransportsRep();
    }

    public void updateForm(String id, String toChangeField, String newVal) {

        getFormsById(id).update(id,toChangeField,newVal);
        transportsRep.update(getFormsById(id));
    }

    public void createTransport(TransportSDTO transportSDTO){
        // TODO: 06/05/2022
        //
        TransportForm transportForm1= new TransportForm(transportSDTO.getId(), transportSDTO.getDate(), transportSDTO.getDepartureTime(), transportSDTO.getDriverid(),
                transportSDTO.getSource(), transportSDTO.getDestinations(),transportSDTO.getTruckLicensePlateId(),transportSDTO.getStatus(),transportSDTO.getTransportWeight());
        for (ProductsDocumentSDTO traDoc: transportSDTO.getTransportProductsDocuments()) {
            transportForm1.addDocument(traDoc.getId(),traDoc.getDestinationId(),traDoc.getProducts());
        }
        transportsRep.addTransportForm(transportForm1);
    }

//    public List<String> getForms(){
//        List<String> formsIds=new ArrayList<>();
//        for (TransportForm tf: transportsRep.getTransportFormsCache()) {
//            formsIds.add(tf.getId());
//        }
//        return formsIds;
//    }

    public List<String> getFormsByDate(String date){

        return transportsRep.getTransportFormsbyDate(date);
    }


    public TransportForm getFormsById(String id) {
        return transportsRep.getTransportFormById(id);

    }
}
