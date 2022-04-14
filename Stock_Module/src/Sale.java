import java.time.LocalDate;
import java.util.List;

public class Sale {
    private double percentage;
    private LocalDate start_date;
    private LocalDate end_date;
    private String reason;
    private List<Product> products_in_sale;
}
