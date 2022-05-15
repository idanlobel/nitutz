package src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects;

import src.TransportationsAndWorkersModule.BusinessLogic.BusinessObjects.Workers.Worker;

public class WorkerDTO {
    Integer id;
    String licenseType;
    Worker work;

    public WorkerDTO(){

    }

    public WorkerDTO(Integer id, String licenseType, Worker work) {
        this.id = id;
        this.licenseType = licenseType;
        this.work = work;
    }

    public Integer getId() {
        return id;
    }

    public String getLicenseType() {
        return licenseType;
    }

    public Worker getWork() {
        return work;
    }
}
