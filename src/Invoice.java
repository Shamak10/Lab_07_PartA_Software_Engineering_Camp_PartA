import java.util.ArrayList;
import java.util.List;

class Invoice {
    private String title;
    private String customerAddress;
    private List<LineItem> lineItems;

    public Invoice(String title, String customerAddress) {
        this.title = title;
        this.customerAddress = customerAddress;
        this.lineItems = new ArrayList<>();
    }

    public void addLineItem(LineItem lineItem) {
        lineItems.add(lineItem);
    }

    public double getTotalAmountDue() {
        double total = 0;
        for (LineItem item : lineItems) {
            total += item.getTotal();
        }
        return total;
    }

    public void addLineItem(InvoiceGUI.LineItem lineItem) {
    }
}
