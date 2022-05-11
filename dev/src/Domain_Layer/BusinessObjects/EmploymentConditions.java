package src.Domain_Layer.BusinessObjects;

import java.util.Date;

public class EmploymentConditions {
    int salary;

    public Date getRecruitmentDate() {
        return recruitmentDate;
    }

    Date recruitmentDate;

    public EmploymentConditions(int salary, Date recruitmentDate) {
        this.salary = salary;
        this.recruitmentDate = recruitmentDate;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }
}
