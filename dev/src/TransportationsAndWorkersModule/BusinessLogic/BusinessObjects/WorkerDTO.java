package src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects;

import src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.Workers.Worker;

public class WorkerDTO {
    Integer id;
    String licenseType;
    String name;

    public WorkerDTO(){

    }

    public WorkerDTO(Integer id, String licenseType, String name) {
        this.id = id;
        this.licenseType = licenseType;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public String getName() {
        return name;
    }
}
