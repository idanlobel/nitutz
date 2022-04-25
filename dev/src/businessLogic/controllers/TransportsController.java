package businessLogic.controllers;

import businessLogic.TransportForm;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TransportsController {

    List<TransportForm> transportForms;
    DriversController driversController;
    SitesController sitesController;
    TrucksController trucksController;

    public TransportsController(DriversController driversController,SitesController sitesController , TrucksController trucksController) {
        transportForms = new LinkedList<>();
        this.driversController=driversController;
        this.sitesController=sitesController;
        this.trucksController=trucksController;
    }
//    getForms()
//    createForm()
//    updateForm()
//    addDocumentToForm()

    public List<String> getForms(){
        List<String> formsIds=new ArrayList<>();
        for (TransportForm tf: transportForms) {
            formsIds.add(tf.getId());
        }
        return formsIds;
    }


    public void createForm(Service.TransportForm transportForm){
        TransportForm transportForm1= new TransportForm(transportForm.getId(),transportForm.getDate(),transportForm.getDepartureTime(),driversController.getDriverById(transportForm.getDriverid()),
                sitesController.getSite(transportForm.getSource()),sitesController.getSiteFromList(transportForm.getDestinations()),trucksController.getTruckByLicensePlate(transportForm.getTruckLicensePlateId()));
        for (Service.TransportProductsDocument traDoc: transportForm.getTransportProductsDocuments()) {
            transportForm1.addDocument(traDoc.getId(),sitesController.getSite(traDoc.getDestination()),traDoc.getProducts());

        }

        transportForms.add(transportForm1);

    }
    public void updateForm(String transportForm){

    }


}
