package Stock_Module.stock_service_layer;

public class Service_Report {

    private String report_info;

    public Service_Report(String s)
    {
        this.report_info=s;
    }

    public String tostring()
    {
        return report_info;
    }
}
