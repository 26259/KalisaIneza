package _26259.q1;

import java.util.*;

public class Stock_Management {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== STOCK MANAGEMENT SYSTEM (Q1) | 26259 ===");

        try {
            System.out.print("Enter Entity id (>0): ");
            int id = Integer.parseInt(sc.nextLine());
            System.out.print("Enter warehouse name: ");
            String wName = sc.nextLine();
            System.out.print("Enter location: ");
            String loc = sc.nextLine();
            System.out.print("Enter 10-digit contact number: ");
            String phone = sc.nextLine();

            Warehouse wh = new Warehouse(id, wName, loc, phone);

            System.out.print("Enter category name: ");
            String cName = sc.nextLine();
            System.out.print("Enter category code (>=3 alnum): ");
            String cCode = sc.nextLine();

            Category cat = new Category(wh.getId(), cName, cCode);

            System.out.print("Enter supplier name: ");
            String sName = sc.nextLine();
            System.out.print("Enter supplier email: ");
            String sEmail = sc.nextLine();
            System.out.print("Enter supplier 10-digit phone: ");
            String sPhone = sc.nextLine();

            Supplier supp = new Supplier(cat.getId(), sName, sEmail, sPhone);

            System.out.print("Enter product name: ");
            String pName = sc.nextLine();
            System.out.print("Enter unit price (>0): ");
            double price = Double.parseDouble(sc.nextLine());
            System.out.print("Enter stock limit (>=0): ");
            int limit = Integer.parseInt(sc.nextLine());

            Product prod = new Product(supp.getId(), pName, price, limit);

            System.out.print("Enter quantity available (>=0): ");
            int qty = Integer.parseInt(sc.nextLine());
            System.out.print("Enter reorder level (>=0): ");
            int reorder = Integer.parseInt(sc.nextLine());

            StockItem item = new StockItem(prod.getId(), qty, reorder);

            System.out.print("Enter purchase date (YYYY-MM-DD): ");
            String pDate = sc.nextLine();
            System.out.print("Enter purchased quantity (>0): ");
            int pQty = Integer.parseInt(sc.nextLine());

            Purchase pur = new Purchase(item.getId(), pDate, pQty, supp.getSupplierName());

            System.out.print("Enter sale date (YYYY-MM-DD): ");
            String sDate = sc.nextLine();
            System.out.print("Enter sold quantity (>0): ");
            int sQty = Integer.parseInt(sc.nextLine());
            System.out.print("Enter customer name: ");
            String cNameCust = sc.nextLine();

            Sale sale = new Sale(pur.getId(), sDate, sQty, cNameCust);

            Inventory inv = new Inventory(1, item.getQuantityAvailable(), item.getQuantityAvailable() * prod.getUnitPrice());

            StockReport report = new StockReport(inv.getTotalItems(), inv.getStockValue(), "Generated report");

            // Print all data
            System.out.println("\n---- ALL INSERTED DATA | 26259 ----");
            System.out.println(wh);
            System.out.println(cat);
            System.out.println(supp);
            System.out.println(prod);
            System.out.println(item);
            System.out.println(pur);
            System.out.println(sale);
            System.out.println(inv);
            System.out.println(report.generateReport() + " | 26259");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage() + " | 26259");
        } finally {
            sc.close();
        }
    }
}

class Entity {
    private int id;
    private Date createdDate;
    private Date updatedDate;

    public Entity(int id) {
        if (id <= 0) throw new IllegalArgumentException("id must be > 0");
        this.id = id;
        this.createdDate = new Date();
        this.updatedDate = new Date();
    }

    public int getId() { return id; }
    public Date getCreatedDate() { return createdDate; }
    public Date getUpdatedDate() { return updatedDate; }
    public void touch() { this.updatedDate = new Date(); }
}

class Warehouse extends Entity {
    private String warehouseName;
    private String location;
    private String contactNumber;

    public Warehouse(int id, String warehouseName, String location, String contactNumber) {
        super(id);
        if (contactNumber == null || contactNumber.length() != 10) throw new IllegalArgumentException("phone = 10 digits");
        this.warehouseName = warehouseName;
        this.location = location;
        this.contactNumber = contactNumber;
    }

    public String getWarehouseName() { return warehouseName; }
    public String getLocation() { return location; }
    public String getContactNumber() { return contactNumber; }

    @Override
    public String toString() {
        return "Warehouse{id=" + getId() + ", name=" + warehouseName + ", location=" + location + ", phone=" + contactNumber + "}";
    }
}

class Category extends Warehouse {
    private String categoryName;
    private String categoryCode;

    public Category(int id, String categoryName, String categoryCode) {
        super(id, "default", "default", "0123456789");
        if (categoryCode == null || categoryCode.length() < 3 || !categoryCode.matches("[a-zA-Z0-9]+"))
            throw new IllegalArgumentException("category code must be alphanumeric and >=3 chars");
        this.categoryName = categoryName;
        this.categoryCode = categoryCode;
    }

    public String getCategoryName() { return categoryName; }
    public String getCategoryCode() { return categoryCode; }

    @Override
    public String toString() {
        return "Category{id=" + getId() + ", name=" + categoryName + ", code=" + categoryCode + "}";
    }
}

class Supplier extends Category {
    private String supplierName;
    private String supplierEmail;
    private String supplierPhone;

    public Supplier(int id, String supplierName, String supplierEmail, String supplierPhone) {
        super(id, "cat", "CAT1");
        if (!supplierEmail.contains("@")) throw new IllegalArgumentException("invalid email");
        if (supplierPhone == null || supplierPhone.length() != 10) throw new IllegalArgumentException("phone = 10 digits");
        this.supplierName = supplierName;
        this.supplierEmail = supplierEmail;
        this.supplierPhone = supplierPhone;
    }

    public String getSupplierName() { return supplierName; }
    public String getSupplierEmail() { return supplierEmail; }
    public String getSupplierPhone() { return supplierPhone; }

    @Override
    public String toString() {
        return "Supplier{name=" + supplierName + ", email=" + supplierEmail + ", phone=" + supplierPhone + "}";
    }
}

class Product extends Supplier {
    private String productName;
    private double unitPrice;
    private int stockLimit;

    public Product(int id, String productName, double unitPrice, int stockLimit) {
        super(id, "supplier", "s@x.com", "0123456789");
        if (unitPrice <= 0) throw new IllegalArgumentException("unitPrice > 0");
        if (stockLimit < 0) throw new IllegalArgumentException("stockLimit >= 0");
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.stockLimit = stockLimit;
    }

    public String getProductName() { return productName; }
    public double getUnitPrice() { return unitPrice; }
    public int getStockLimit() { return stockLimit; }

    @Override
    public String toString() {
        return "Product{name=" + productName + ", price=" + unitPrice + ", limit=" + stockLimit + "}";
    }
}

class StockItem extends Product {
    private int quantityAvailable;
    private int reorderLevel;

    public StockItem(int id, int quantityAvailable, int reorderLevel) {
        super(id, "product", 1.0, 0);
        if (quantityAvailable < 0 || reorderLevel < 0) throw new IllegalArgumentException("values >= 0");
        this.quantityAvailable = quantityAvailable;
        this.reorderLevel = reorderLevel;
    }

    public int getQuantityAvailable() { return quantityAvailable; }
    public int getReorderLevel() { return reorderLevel; }

    @Override
    public String toString() {
        return "StockItem{qty=" + quantityAvailable + ", reorder=" + reorderLevel + "}";
    }
}

class Purchase extends StockItem {
    private String purchaseDate;
    private int purchasedQuantity;
    private String supplierName;

    public Purchase(int id, String purchaseDate, int purchasedQuantity, String supplierName) {
        super(id, 0, 0);
        if (purchaseDate == null || purchaseDate.isEmpty()) throw new IllegalArgumentException("date not null");
        if (purchasedQuantity <= 0) throw new IllegalArgumentException("quantity > 0");
        this.purchaseDate = purchaseDate;
        this.purchasedQuantity = purchasedQuantity;
        this.supplierName = supplierName;
    }

    public String getPurchaseDate() { return purchaseDate; }
    public int getPurchasedQuantity() { return purchasedQuantity; }
    public String getSupplierName() { return supplierName; }

    @Override
    public String toString() {
        return "Purchase{date=" + purchaseDate + ", qty=" + purchasedQuantity + ", supplier=" + supplierName + "}";
    }
}

class Sale extends Purchase {
    private String saleDate;
    private int soldQuantity;
    private String customerName;

    public Sale(int id, String saleDate, int soldQuantity, String customerName) {
        super(id, "1970-01-01", 1, "supplier");
        if (saleDate == null || saleDate.isEmpty()) throw new IllegalArgumentException("date valid");
        if (soldQuantity <= 0) throw new IllegalArgumentException("soldQuantity > 0");
        this.saleDate = saleDate;
        this.soldQuantity = soldQuantity;
        this.customerName = customerName;
    }

    public String getSaleDate() { return saleDate; }
    public int getSoldQuantity() { return soldQuantity; }
    public String getCustomerName() { return customerName; }

    @Override
    public String toString() {
        return "Sale{date=" + saleDate + ", soldQty=" + soldQuantity + ", customer=" + customerName + "}";
    }
}

class Inventory extends Sale {
    private int totalItems;
    private double stockValue;

    public Inventory(int id, int totalItems, double stockValue) {
        super(id, "1970-01-01", 1, "cust");
        if (totalItems < 0 || stockValue < 0) throw new IllegalArgumentException("totals >= 0");
        this.totalItems = totalItems;
        this.stockValue = stockValue;
    }

    public int getTotalItems() { return totalItems; }
    public double getStockValue() { return stockValue; }

    @Override
    public String toString() {
        return "Inventory{totalItems=" + totalItems + ", stockValue=" + stockValue + "}";
    }
}

final class StockReport extends Inventory {
    private String reportDate;
    private String remarks;

    public StockReport(int totalItems, double stockValue, String remarks) {
        super(1, totalItems, stockValue);
        this.reportDate = new Date().toString();
        this.remarks = remarks;
    }

    public String generateReport() {
        return "StockReport[date=" + reportDate + ", totalItems=" + getTotalItems() + ", stockValue=" + getStockValue() + ", remarks=" + remarks + "]";
    }
}
