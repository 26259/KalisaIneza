package _26259.q3;

import java.util.*;

public class Tax_Administration {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== TAX ADMINISTRATION SYSTEM (Q3) | 26259 ===");

        try {
            System.out.print("Enter Entity id (>0): ");
            int id = Integer.parseInt(sc.nextLine());

            System.out.print("Enter authority name: ");
            String authName = sc.nextLine();
            System.out.print("Enter region: ");
            String region = sc.nextLine();
            System.out.print("Enter email: ");
            String authEmail = sc.nextLine();

            TaxAuthority ta = new TaxAuthority(id, authName, region, authEmail);

            System.out.print("Enter tax category name: ");
            String catName = sc.nextLine();
            System.out.print("Enter rate (>0): ");
            double rate = Double.parseDouble(sc.nextLine());
            System.out.print("Enter code (>=3 chars): ");
            String code = sc.nextLine();

            TaxCategory tc = new TaxCategory(ta.getId(), catName, rate, code);

            System.out.print("Enter taxpayer TIN (9 digits): ");
            String tin = sc.nextLine();
            System.out.print("Enter taxpayer name: ");
            String tName = sc.nextLine();
            System.out.print("Enter address: ");
            String addr = sc.nextLine();

            Taxpayer tp = new Taxpayer(tc.getId(), tin, tName, addr);

            System.out.print("Enter employer name: ");
            String empName = sc.nextLine();
            System.out.print("Enter employer TIN (9 digits): ");
            String empTIN = sc.nextLine();
            System.out.print("Enter employer phone (10 digits): ");
            String empPhone = sc.nextLine();

            Employer emp = new Employer(tp.getId(), empName, empTIN, empPhone);

            System.out.print("Enter employee name: ");
            String eName = sc.nextLine();
            System.out.print("Enter salary (>0): ");
            double salary = Double.parseDouble(sc.nextLine());
            System.out.print("Enter employee TIN (9 digits): ");
            String eTIN = sc.nextLine();

            Employee empy = new Employee(emp.getId(), eName, salary, eTIN);

            System.out.print("Enter declaration month (1-12): ");
            int month = Integer.parseInt(sc.nextLine());
            System.out.print("Enter total income (>=0): ");
            double income = Double.parseDouble(sc.nextLine());

            TaxDeclaration decl = new TaxDeclaration(empy.getId(), month, income);

            TaxAssessment assess = new TaxAssessment(decl.getId(), new Date().toString(), income * tc.getRate());

            System.out.print("Enter payment date: ");
            String payDate = sc.nextLine();
            System.out.print("Enter payment amount (>0): ");
            double payAmt = Double.parseDouble(sc.nextLine());

            Payment pay = new Payment(assess.getId(), payDate, payAmt);

            TaxRecord record = new TaxRecord(pay.getId(), "R" + new Date().getTime(), assess.getAssessedTax());

            System.out.println("\n---- ALL INSERTED DATA | 26259 ----");
            System.out.println(ta);
            System.out.println(tc);
            System.out.println(tp);
            System.out.println(emp);
            System.out.println(empy);
            System.out.println(decl);
            System.out.println(assess);
            System.out.println(pay);
            System.out.println(record.computeTax() + " | 26259");

        } catch (TaxDataException tx) {
            System.out.println("TaxDataException: " + tx.getMessage() + " | 26259");
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage() + " | 26259");
        } finally {
            sc.close();
        }
    }
}

class TaxDataException extends Exception {
    public TaxDataException(String msg) { super(msg); }
}

class Entity {
    private int id; public Entity(int id) { if (id <= 0) throw new IllegalArgumentException("id > 0"); this.id = id; } public int getId(){return id;}
}

class TaxAuthority extends Entity {
    private String authorityName, region, email;
    public TaxAuthority(int id, String authorityName, String region, String email) {
        super(id);
        if (!email.contains("@")) throw new IllegalArgumentException("invalid email");
        this.authorityName = authorityName; this.region = region; this.email = email;
    }
    @Override public String toString(){ return "TaxAuthority{name=" + authorityName + ", region=" + region + ", email=" + email + "}"; }
}

class TaxCategory extends TaxAuthority {
    private String categoryName, code;
    private double rate;
    public TaxCategory(int id, String categoryName, double rate, String code) {
        super(id, "auth", "r", "a@b.com");
        if (rate <= 0) throw new IllegalArgumentException("rate > 0");
        if (code == null || code.length() < 3) throw new IllegalArgumentException("code >=3 chars");
        this.categoryName = categoryName; this.rate = rate; this.code = code;
    }
    public double getRate(){ return rate; }
    @Override public String toString(){ return "TaxCategory{name=" + categoryName + ", rate=" + rate + ", code=" + code + "}"; }
}

class Taxpayer extends TaxCategory {
    private String tin, taxpayerName, address;
    public Taxpayer(int id, String tin, String taxpayerName, String address) throws TaxDataException {
        super(id, "cat", 1.0, "C1");
        if (tin == null || !tin.matches("\\d{9}")) throw new TaxDataException("tin must be 9 digits");
        this.tin = tin; this.taxpayerName = taxpayerName; this.address = address;
    }
    @Override public String toString(){ return "Taxpayer{TIN=" + tin + ", name=" + taxpayerName + ", addr=" + address + "}"; }
}

class Employer extends Taxpayer {
    private String employerName, employerTIN, contact;
    public Employer(int id, String employerName, String employerTIN, String contact) throws TaxDataException {
        super(id, "000000000", "t", "addr");
        if (employerTIN == null || !employerTIN.matches("\\d{9}")) throw new TaxDataException("invalid TIN");
        if (contact == null || contact.length() != 10) throw new TaxDataException("phone = 10 digits");
        this.employerName = employerName; this.employerTIN = employerTIN; this.contact = contact;
    }
    @Override public String toString(){ return "Employer{name=" + employerName + ", TIN=" + employerTIN + ", contact=" + contact + "}"; }
}

class Employee extends Employer {
    private String employeeName, employeeTIN;
    private double salary;
    public Employee(int id, String employeeName, double salary, String employeeTIN) throws TaxDataException {
        super(id, "emp", "000000000", "0123456789");
        if (salary <= 0) throw new TaxDataException("salary > 0");
        if (employeeTIN == null || !employeeTIN.matches("\\d{9}")) throw new TaxDataException("invalid TIN");
        this.employeeName = employeeName; this.salary = salary; this.employeeTIN = employeeTIN;
    }
    public double getSalary(){ return salary; }
    @Override public String toString(){ return "Employee{name=" + employeeName + ", salary=" + salary + ", TIN=" + employeeTIN + "}"; }
}

class TaxDeclaration extends Employee {
    private int declarationMonth;
    private double totalIncome;
    public TaxDeclaration(int id, int declarationMonth, double totalIncome) throws TaxDataException {
        super(id, "emp", 1.0, "000000000");
        if (declarationMonth < 1 || declarationMonth > 12) throw new TaxDataException("month invalid");
        if (totalIncome < 0) throw new TaxDataException("income >= 0");
        this.declarationMonth = declarationMonth; this.totalIncome = totalIncome;
    }
    public double getTotalIncome(){ return totalIncome; }
    @Override public String toString(){ return "TaxDeclaration{month=" + declarationMonth + ", income=" + totalIncome + "}"; }
}

class TaxAssessment extends TaxDeclaration {
    private String assessmentDate;
    private double assessedTax;
    public TaxAssessment(int id, String assessmentDate, double assessedTax) throws TaxDataException {
        super(id, 1, 0.0);
        if (assessedTax < 0) throw new TaxDataException("tax >= 0");
        this.assessmentDate = assessmentDate; this.assessedTax = assessedTax;
    }
    public double getAssessedTax(){ return assessedTax; }
    @Override public String toString(){ return "TaxAssessment{date=" + assessmentDate + ", tax=" + assessedTax + "}"; }
}

class Payment extends TaxAssessment {
    private String paymentDate;
    private double paymentAmount;
    public Payment(int id, String paymentDate, double paymentAmount) throws TaxDataException {
        super(id, "d", 0.0);
        if (paymentAmount <= 0) throw new TaxDataException("paymentAmount > 0");
        this.paymentDate = paymentDate; this.paymentAmount = paymentAmount;
    }
    @Override public String toString(){ return "Payment{date=" + paymentDate + ", amount=" + paymentAmount + "}"; }
}

final class TaxRecord extends Payment {
    private String receiptNo;
    private double totalTax;
    public TaxRecord(int id, String receiptNo, double totalTax) throws TaxDataException {
        super(id, "d", 1.0);
        this.receiptNo = receiptNo; this.totalTax = totalTax;
    }
    public String computeTax() {
        return "TaxRecord{receipt=" + receiptNo + ", totalTax=" + totalTax + "}";
    }
}
