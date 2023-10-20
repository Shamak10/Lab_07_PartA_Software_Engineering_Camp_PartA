import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.LineBorder;

public class InvoiceGUI extends JFrame {
    private JTextField titleField;
    private JTextField customerAddressField;
    private JTextField productNameField;
    private JTextField unitPriceField;
    private JTextField quantityField;
    private JTextArea invoiceTextArea;
    private JCheckBox moreItemsCheckBox;
    private Invoice invoice;

    private boolean newInvoice = true;

    public InvoiceGUI() {
        setTitle("Invoice Application");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create input panel
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(6, 2));

        inputPanel.add(new JLabel("Title:"));
        titleField = new JTextField();
        inputPanel.add(titleField);

        inputPanel.add(new JLabel("Customer Address:"));
        customerAddressField = new JTextField();
        inputPanel.add(customerAddressField);

        inputPanel.add(new JLabel("Product Name:"));
        productNameField = new JTextField();
        inputPanel.add(productNameField);

        inputPanel.add(new JLabel("Unit Price:"));
        unitPriceField = new JTextField();
        inputPanel.add(unitPriceField);

        inputPanel.add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        inputPanel.add(quantityField);

        moreItemsCheckBox = new JCheckBox("More Items");
        inputPanel.add(moreItemsCheckBox);

        JButton addItemButton = new JButton("Add Item");
        addItemButton.addActionListener(new AddItemButtonListener());
        inputPanel.add(addItemButton);

        add(inputPanel, BorderLayout.NORTH);

        JPanel invoicePanel = new JPanel(new BorderLayout());
        invoicePanel.setBorder(new LineBorder(Color.BLACK));

        invoiceTextArea = new JTextArea();
        invoiceTextArea.setEditable(false);
        invoicePanel.add(new JScrollPane(invoiceTextArea), BorderLayout.CENTER);

        add(invoicePanel, BorderLayout.CENTER);

        invoice = new Invoice();
    }

    private class AddItemButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String title = titleField.getText();
            String customerAddress = customerAddressField.getText();
            String productName = productNameField.getText();
            double unitPrice = Double.parseDouble(unitPriceField.getText());
            int quantity = Integer.parseInt(quantityField.getText());

            Product product = new Product(productName, unitPrice);
            LineItem lineItem = new LineItem(product, quantity);

            if (newInvoice) {
                // Create a new invoice
                if (!title.isEmpty()) {
                    invoiceTextArea.append(title + "\n");
                }
                if (!customerAddress.isEmpty()) {
                    invoiceTextArea.append(customerAddress + "\n");
                }
                invoiceTextArea.append("\n");
                invoiceTextArea.append(String.format("%-15s %-5s %-10s %-10s\n", "Item", "Qty", "Price", "Total"));
                invoiceTextArea.append("------------------------------\n");
                newInvoice = false;
            }

            invoice.addLineItem(lineItem);

            // Add line item
            invoiceTextArea.append(String.format("%-15s %-5d $%-9.2f $%-9.2f\n", productName, quantity, unitPrice, lineItem.getTotal()));

            productNameField.setText("");
            unitPriceField.setText("");
            quantityField.setText("");

            if (!moreItemsCheckBox.isSelected()) {
                titleField.setText("");
                customerAddressField.setText("");
                newInvoice = true; // Start a new invoice
                double totalAmount = invoice.getTotalAmount();
                invoiceTextArea.append("------------------------------\n");
                invoiceTextArea.append(String.format("\nAMOUNT DUE: $%.2f", totalAmount));
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InvoiceGUI gui = new InvoiceGUI();
            gui.setVisible(true);
        });
    }

    class LineItem {
        private Product product;
        private int quantity;

        public LineItem(Product product, int quantity) {
            this.product = product;
            this.quantity = quantity;
        }

        public double getTotal() {
            return product.getUnitPrice() * quantity;
        }
    }

    class Product {
        private String name;
        private double unitPrice;

        public Product(String name, double unitPrice) {
            this.name = name;
            this.unitPrice = unitPrice;
        }

        public double getUnitPrice() {
            return unitPrice;
        }
    }

    class Invoice {
        private double totalAmount;

        public Invoice() {
            totalAmount = 0.0;
        }

        public void addLineItem(LineItem lineItem) {
            totalAmount += lineItem.getTotal();
        }

        public double getTotalAmount() {
            return totalAmount;
        }
    }
}
