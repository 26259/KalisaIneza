package _26259.q6;

import java.util.*;

public class Payroll_Management {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== PAYROLL MANAGEMENT SYSTEM (Q6) | 26259 ===");

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
            System.out.print("Enter dept code (>=3 chars): ");
            String deptCode = sc.nextLine();
            System.out.print("Enter manager name: ");
            String manager = sc.nextLine();

            Department dept = new Department(org.getId(), deptName, deptCode, manager);

            System.out.print("Enter employee ID (>=1000): ");
            int empId = Integer.parseInt(sc.nextLine());
            System.out.print("Enter full name: ");
            String fullName = sc.nextLine();
            System.out.print("Enter position: ");
            String position = sc.nextLine();
            System.out.print("Enter base salary (>0): ");
            double baseSalary = Double.parseDouble(sc.nextLine());
            System.out.print("Is RSSB registered? (true/false): ");
            boolean rssbRegistered = Boolean.parseBoolean(sc.nextLine());

            Employee emp = new Employee(dept.getId(), empId, fullName, position, baseSalary, rssbRegistered);

            System.out.print("Enter payroll month (1-12): ");
            int month = Integer.parseInt(sc.nextLine());
            System.out.print("Enter year (>=2000): ");
            int year = Integer.parseInt(sc.nextLine());
            System.out.print("Enter start date: ");
            String startDate = sc.nextLine();
            System.out.print("Enter end date: ");
            String endDate = sc.nextLine();

            PayrollPeriod pp = new PayrollPeriod(emp.getEmployeeID(), month, year, startDate, endDate);

            System.out.print("Enter basic pay: ");
            double basicPay = Double.parseDouble(sc.nextLine());
            System.out.print("Enter transport allowance: ");
            double transport = Double.parseDouble(sc.nextLine());
            System.out.print("Enter housing allowance: ");
            double housing = Double.parseDouble(sc.nextLine());

            SalaryStructure ss = new SalaryStructure(pp.getId(), basicPay, transport, housing);

            System.out.print("Enter overtime hours: ");
            double oHours = Double.parseDouble(sc.nextLine());
            System.out.print("Enter overtime rate: ");
            double oRate = Double.parseDouble(sc.nextLine());
            System.out.print("Enter bonus: ");
            double bonus = Double.parseDouble(sc.nextLine());

            Allowance al = new Allowance(ss.getId(), oHours, oRate, bonus);

            Deduction ded = new Deduction(al.getId(), ss.getBasicPay() * 0.05, 0, 0); // rssb 5% of basic

            Payroll payroll = new Payroll(ded.getId(), ss.getBasicPay() + al.getTotalAllowances(), ded.getRssbContribution(), 0);

            Payslip payslip = new Payslip(payroll.getId(), "PS" + new Date().getTime(), new Date().toString(), payroll);

            System.out.println("\n---- ALL INSERTED DATA | 26259 ----");
            System.out.println(org);
            System.out.println(dept);
            System.out.println(emp);
            System.out.println(pp);
            System.out.println(ss);
            System.out.println(al);
            System.out.println(ded);
            System.out.println(payroll);
            System.out.println(payslip.generatePayslip() + " | 26259");

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage() + " | 26259");
        } finally {
            sc.close();
        }
    }
}

class Entity {
    private int id;
    public Entity(int id){ if (id <= 0) throw new IllegalArgumentException("id > 0"); this.id = id; }
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
    private String deptName, deptCode, managerName;
    public Department(int id, String deptName, String deptCode, String managerName) {
        super(id, "org", "ORG", "00000000", "a@b.com");
        if (deptCode == null || deptCode.length() < 3) throw new IllegalArgumentException("deptCode >= 3 chars");
        this.deptName = deptName; this.deptCode = deptCode; this.managerName = managerName;
    }
    @Override public String toString(){ return "Department{name=" + deptName + ", code=" + deptCode + ", manager=" + managerName + "}"; }
}

class Employee extends Department {
    private int employeeID;
    private String fullName, position;
    private double baseSalary;
    private boolean rssbRegistered;
    public Employee(int id, int employeeID, String fullName, String position, double baseSalary, boolean rssbRegistered) {
        super(id, "dept", "DPT", "mgr");
        if (employeeID < 1000) throw new IllegalArgumentException("employeeID >= 1000");
        if (baseSalary <= 0) throw new IllegalArgumentException("baseSalary > 0");
        this.employeeID = employeeID; this.fullName = fullName; this.position = position; this.baseSalary = baseSalary; this.rssbRegistered = rssbRegistered;
    }
    public int getEmployeeID(){ return employeeID; }
    @Override public String toString(){ return "Employee{ID=" + employeeID + ", name=" + fullName + ", position=" + position + ", base=" + baseSalary + ", rssb=" + rssbRegistered + "}"; }
}

class PayrollPeriod extends Employee {
    private int month, year;
    private String startDate, endDate;
    public PayrollPeriod(int id, int month, int year, String startDate, String endDate) {
        super(id, 1000, "name", "pos", 1.0, true);
        if (month < 1 || month > 12) throw new IllegalArgumentException("month invalid");
        if (year < 2000) throw new IllegalArgumentException("year >= 2000");
        this.month = month; this.year = year; this.startDate = startDate; this.endDate = endDate;
    }
    @Override public String toString(){ return "PayrollPeriod{month=" + month + ", year=" + year + ", start=" + startDate + ", end=" + endDate + "}"; }
    public int getId(){ return super.getId(); }
}

class SalaryStructure extends PayrollPeriod {
    private double basicPay, transportAllowance, housingAllowance;
    public SalaryStructure(int id, double basicPay, double transportAllowance, double housingAllowance) {
        super(id, 1, 2000, "s", "e");
        if (basicPay < 0 || transportAllowance < 0 || housingAllowance < 0) throw new IllegalArgumentException("allowances >= 0");
        this.basicPay = basicPay; this.transportAllowance = transportAllowance; this.housingAllowance = housingAllowance;
    }
    public double getBasicPay(){ return basicPay; }
    @Override public String toString(){ return "SalaryStructure{basic=" + basicPay + ", transport=" + transportAllowance + ", housing=" + housingAllowance + "}"; }
}

class Deduction extends SalaryStructure {
    private double rssbContribution, payeTax, loanDeduction;
    public Deduction(int id, double rssbContribution, double payeTax, double loanDeduction) {
        super(id, 1.0, 1.0, 1.0);
        if (rssbContribution < 0 || payeTax < 0 || loanDeduction < 0) throw new IllegalArgumentException("deductions >= 0");
        this.rssbContribution = rssbContribution; this.payeTax = payeTax; this.loanDeduction = loanDeduction;
    }
    public double getRssbContribution(){ return rssbContribution; }
    @Override public String toString(){ return "Deduction{rssb=" + rssbContribution + ", paye=" + payeTax + ", loan=" + loanDeduction + "}"; }
}

class Allowance extends Deduction {
    private double overtimeHours, overtimeRate, bonus;
    public Allowance(int id, double overtimeHours, double overtimeRate, double bonus) {
        super(id, 0.0, 0.0, 0.0);
        if (overtimeHours < 0 || overtimeRate < 0 || bonus < 0) throw new IllegalArgumentException("allowances non-negative");
        this.overtimeHours = overtimeHours; this.overtimeRate = overtimeRate; this.bonus = bonus;
    }
    public double getTotalAllowances(){ return overtimeHours * overtimeRate + bonus; }
    @Override public String toString(){ return "Allowance{overtime=" + overtimeHours + ", rate=" + overtimeRate + ", bonus=" + bonus + "}"; }
}

class Payroll extends Allowance {
    private double grossSalary, totalDeductions, netSalary;
    public Payroll(int id, double grossSalary, double rssbContribution, double totalDeductions) {
        super(id, 0, 0, 0);
        if (grossSalary < 0) throw new IllegalArgumentException("gross >=0");
        this.grossSalary = grossSalary;
        this.totalDeductions = rssbContribution + totalDeductions;
        this.netSalary = grossSalary - this.totalDeductions;
    }
    public double getNetSalary(){ return netSalary; }
    @Override public String toString(){ return "Payroll{gross=" + grossSalary + ", deductions=" + totalDeductions + ", net=" + netSalary + "}"; }
}

final class Payslip extends Payroll {
    private String payslipNumber, issueDate;
    private Payroll payroll;
    public Payslip(int id, String payslipNumber, String issueDate, Payroll payroll) {
        super(id, payroll.getNetSalary(), 0, 0);
        this.payslipNumber = payslipNumber; this.issueDate = issueDate; this.payroll = payroll;
    }
    public String generatePayslip() {
        return "Payslip{no=" + payslipNumber + ", date=" + issueDate + ", netSalary=" + payroll.getNetSalary() + "}";
    }
}
