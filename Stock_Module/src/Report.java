public class Report {
    //maybe we make it abstarct
    //every subject will be a subclass of report
    public enum Subject {
        sales_report, stock_report , order_report , defective_items_report , expired_items_report
    }

    private Subject subject;
    private int id;
}
