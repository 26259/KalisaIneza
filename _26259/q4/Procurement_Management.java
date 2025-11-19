package _26259.q4;

import java.util.*;

public class Procurement_Management {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== PROCUREMENT MANAGEMENT SYSTEM (Q4) | 26259 ===");

        try {
            System.out.print("Enter Entity id (>0): ");
            int id = Integer.parseInt(sc.nextLine());

            System.out.print("Enter organization name: ");
            String orgName = sc.nextLine();
            System.out.print("Enter org code (>=3 chars): ");
            String orgCode = sc.nextLine();
            System.out.print("Enter rssbNumber (8 digits): ");
            String rssb = sc.nextLine();
            System.out.print("Enter contact email: ");
            String email = sc.nextLine();

            Organization org = new Organization(id, orgName, orgCode, rssb, email);

            System.out.print("Enter department name: ");
            String deptName = sc.nextLine();
            System.out.print("Enter dept code (>=3 alnum): ");
            String deptCode = sc.nextLine();

            Department dept = new Department(org.getId(), deptName, deptCode);

            System.out.print("Enter supplier name: ");
            String supplierName = sc.nextLine();
            System.out.print("Enter supplier TIN (9 digits): ");
            String supplierTIN = sc.nextLine();
            System.out.print("Enter supplier phone (10 digits): ");
            String supplierPhone = sc.nextLine();

            Supplier supplier = new Supplier(dept.getId(), supplierName, supplierTIN, supplierPhone);

            System.out.print("Enter product name: ");
            String productName = sc.nextLine();
            System.out.print("Enter unit price (>0): ");
            double unitPrice = Double.parseDouble(sc.nextLine());
            System.out.print("Enter quantity (>=0): ");
            int quantity = Integer.parseInt(sc.nextLine());

            Product product = new Product(supplier.getId(), productName, unitPrice, quantity);

            System.out.print("Enter PO number: ");
            String poNo = sc.nextLine();
            System.out.print("Enter order date: ");
            String orderDate = sc.nextLine();

            PurchaseOrder po = new PurchaseOrder(product.getId(), poNo, orderDate, unitPrice * quantity);

            System.out.print("Enter delivery date: ");
            String deliveryDate = sc.nextLine();
            System.out.print("Enter delivered by: ");
            String deliveredBy = sc.nextLine();

            Delivery del = new Delivery(po.getId(), deliveryDate, deliveredBy);

            System.out.print("Enter inspector name: ");
            String insp = sc.nextLine();
            System.out.print("Enter status (Passed/Failed): ");
            String status = sc.nextLine();
            System.out.print("Enter remarks: ");
            String remarks = sc.nextLine();

            Inspection ins = new Inspection(del.getId(), insp, status, remarks);

            System.out.print("Enter invoice no: ");
            String invNo = sc.nextLine();
            System.out.print("Enter invoice amount (>0): ");
            double invAmt = Double.parseDouble(sc.nextLine());

            Invoice invoice = new Invoice(ins.getId(), invNo, invAmt);

            ProcurementReport report = new ProcurementReport(invoice.getId(), new Date().toString(), "Summary", invAmt);

            System.out.println("\n---- ALL INSERTED DATA | 26259 ----");
            System.out.println(org);
            System.out.println(dept);
            System.out.println(supplier);
            System.out.println(product);
            System.out.println(po);
            System.out.println(del);
            System.out.println(ins);
            System.out.println(invoice);
            System.out.println(report.calculateTotal() + " | 26259");

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage() + " | 26259");
        } finally {
            sc.close();
        }
    }
}

class Entity {
    private int id;
    public Entity(int id) { if (id <= 0) throw new IllegalArgumentException("id > 0"); this.id = id; }
    public int getId(){ return id; }
}

class Organization extends Entity {
    private String orgName, orgCode, rssbNumber, contactEmail;
    public Organization(int id, String orgName, String orgCode, String rssbNumber, String contactEmail) {
        super(id);
        if (rssbNumber == null || rssbNumber.length() != 8) throw new IllegalArgumentException("rssbNumber = 8 digits");
        if (!contactEmail.contains("@")) throw new IllegalArgumentException("invalid email");
        this.orgName = orgName; this.orgCode = orgCode; this.rssbNumber = rssbNumber; this.contactEmail = contactEmail;
    }
    @Override public String toString(){ return "Organization{name=" + orgName + ", code=" + orgCode + ", rssb=" + rssbNumber + ", email=" + contactEmail + "}"; }
}

class Department extends Organization {
    private String deptName, deptCode;
    public Department(int id, String deptName, String deptCode) {
        super(id, "org", "ORG", "00000000", "a@b.com");
        if (deptCode == null || deptCode.length() < 3) throw new IllegalArgumentException("code >= 3 chars");
        this.deptName = deptName; this.deptCode = deptCode;
    }
    @Override public String toString(){ return "Department{name=" + deptName + ", code=" + deptCode + "}"; }
}

class Supplier extends Department {
    private String supplierName, supplierTIN, contact;
    public Supplier(int id, String supplierName, String supplierTIN, String contact) {
        super(id, "dept", "DPT");
        if (supplierTIN == null || !supplierTIN.matches("\\d{9}")) throw new IllegalArgumentException("TIN = 9 digits");
        if (contact == null || contact.length() != 10) throw new IllegalArgumentException("phone = 10 digits");
        this.supplierName = supplierName; this.supplierTIN = supplierTIN; this.contact = contact;
    }
    @Override public String toString(){ return "Supplier{name=" + supplierName + ", TIN=" + supplierTIN + ", contact=" + contact + "}"; }
}

class Product extends Supplier {
    private String productName;
    private double unitPrice;
    private int quantity;
    public Product(int id, String productName, double unitPrice, int quantity) {
        super(id, "supp", "000000000", "0123456789");
        if (unitPrice <= 0) throw new IllegalArgumentException("unitPrice > 0");
        if (quantity < 0) throw new IllegalArgumentException("qty >= 0");
        this.productName = productName; this.unitPrice = unitPrice; this.quantity = quantity;
    }
    @Override public String toString(){ return "Product{name=" + productName + ", unitPrice=" + unitPrice + ", qty=" + quantity + "}"; }
}

class PurchaseOrder extends Product {
    private String poNumber, orderDate;
    private double totalAmount;
    public PurchaseOrder(int id, String poNumber, String orderDate, double totalAmount) {
        super(id, "prod", 1.0, 1);
        if (totalAmount <= 0) throw new IllegalArgumentException("total > 0");
        this.poNumber = poNumber; this.orderDate = orderDate; this.totalAmount = totalAmount;
    }
    public double getTotalAmount(){ return totalAmount; }
    @Override public String toString(){ return "PurchaseOrder{po=" + poNumber + ", date=" + orderDate + ", total=" + totalAmount + "}"; }
}

class Delivery extends PurchaseOrder {
    private String deliveryDate, deliveredBy;
    public Delivery(int id, String deliveryDate, String deliveredBy) {
        super(id, "po", "d", 1.0);
        if (deliveryDate == null || deliveryDate.isEmpty()) throw new IllegalArgumentException("deliveryDate not null");
        this.deliveryDate = deliveryDate; this.deliveredBy = deliveredBy;
    }
    @Override public String toString(){ return "Delivery{date=" + deliveryDate + ", by=" + deliveredBy + "}"; }
}

class Inspection extends Delivery {
    private String inspectorName, status, remarks;
    public Inspection(int id, String inspectorName, String status, String remarks) {
        super(id, "d", "by");
        if (!status.equalsIgnoreCase("Passed") && !status.equalsIgnoreCase("Failed")) throw new IllegalArgumentException("status must be Passed/Failed");
        this.inspectorName = inspectorName; this.status = status; this.remarks = remarks;
    }
    @Override public String toString(){ return "Inspection{inspector=" + inspectorName + ", status=" + status + ", remarks=" + remarks + "}"; }
}

class Invoice extends Inspection {
    private String invoiceNo;
    private double invoiceAmount;
    public Invoice(int id, String invoiceNo, double invoiceAmount) {
        super(id, "inspector", "Passed", "ok");
        if (invoiceAmount <= 0) throw new IllegalArgumentException("amount > 0");
        this.invoiceNo = invoiceNo; this.invoiceAmount = invoiceAmount;
    }
    public double getInvoiceAmount(){ return invoiceAmount; }
    @Override public String toString(){ return "Invoice{no=" + invoiceNo + ", amount=" + invoiceAmount + "}"; }
}

final class ProcurementReport extends Invoice {
    private String reportDate, summary;
    private double total;
    public ProcurementReport(int id, String reportDate, String summary, double total) {
        super(id, "inv", total);
        this.reportDate = reportDate; this.summary = summary; this.total = total;
    }
    public String calculateTotal() {
        return "ProcurementReport{date=" + reportDate + ", summary=" + summary + ", total=" + total + "}";
    }
}
