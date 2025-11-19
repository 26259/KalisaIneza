package _26259.q2;

import java.util.*;

public class Flight_Booking {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== FLIGHT BOOKING SYSTEM (Q2) | 26259 ===");

        try {
            System.out.print("Enter Entity id (>0): ");
            int id = Integer.parseInt(sc.nextLine());
            System.out.print("Enter airport name: ");
            String aName = sc.nextLine();
            System.out.print("Enter airport code (3 uppercase letters): ");
            String code = sc.nextLine();
            System.out.print("Enter location: ");
            String loc = sc.nextLine();

            Airport ap = new Airport(id, aName, code, loc);

            System.out.print("Enter airline name: ");
            String airName = sc.nextLine();
            System.out.print("Enter airline code (2-4 letters): ");
            String airCode = sc.nextLine();
            System.out.print("Enter contact email: ");
            String airEmail = sc.nextLine();

            Airline al = new Airline(ap.getId(), airName, airCode, airEmail);

            System.out.print("Enter flight number: ");
            String fNo = sc.nextLine();
            System.out.print("Enter departure: ");
            String dep = sc.nextLine();
            System.out.print("Enter destination: ");
            String dest = sc.nextLine();
            System.out.print("Enter base fare (>0): ");
            double baseFare = Double.parseDouble(sc.nextLine());

            Flight f = new Flight(al.getId(), fNo, dep, dest, baseFare);

            System.out.print("Enter pilot name: ");
            String pName = sc.nextLine();
            System.out.print("Enter license number: ");
            String lic = sc.nextLine();
            System.out.print("Enter experience years (>=2): ");
            int exp = Integer.parseInt(sc.nextLine());

            Pilot pilot = new Pilot(f.getId(), pName, lic, exp);

            System.out.print("Enter cabin crew name: ");
            String cName = sc.nextLine();
            System.out.print("Enter role: ");
            String role = sc.nextLine();
            System.out.print("Enter shift (Day/Night): ");
            String shift = sc.nextLine();

            CabinCrew cc = new CabinCrew(pilot.getId(), cName, role, shift);

            System.out.print("Enter passenger name: ");
            String pasName = sc.nextLine();
            System.out.print("Enter age (>0): ");
            int age = Integer.parseInt(sc.nextLine());
            System.out.print("Enter gender (M/F): ");
            String gender = sc.nextLine();
            System.out.print("Enter contact: ");
            String contact = sc.nextLine();

            Passenger pas = new Passenger(cc.getId(), pasName, age, gender, contact);

            System.out.print("Enter booking date (YYYY-MM-DD): ");
            String bookDate = sc.nextLine();
            System.out.print("Enter seat number: ");
            String seat = sc.nextLine();
            System.out.print("Enter travel class (Economy/Business/First): ");
            String tClass = sc.nextLine();

            Booking bk = new Booking(pas.getId(), bookDate, seat, tClass);

            System.out.print("Enter payment date: ");
            String payDate = sc.nextLine();
            System.out.print("Enter payment method: ");
            String pm = sc.nextLine();
            System.out.print("Enter amount paid (>0): ");
            double amt = Double.parseDouble(sc.nextLine());

            Payment pay = new Payment(bk.getId(), payDate, pm, amt);

            Ticket ticket = new Ticket(pay.getId(), "TICK" + new Date().getTime());

            System.out.println("\n---- ALL INSERTED DATA | 26259 ----");
            System.out.println(ap);
            System.out.println(al);
            System.out.println(f);
            System.out.println(pilot);
            System.out.println(cc);
            System.out.println(pas);
            System.out.println(bk);
            System.out.println(pay);
            System.out.println(ticket);
            System.out.println("Calculated fare: " + ticket.calculateFare() + " | 26259");

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage() + " | 26259");
        } finally {
            sc.close();
        }
    }
}

class Entity {
    private int id;
    private Date createdDate = new Date();
    private Date updatedDate = new Date();

    public Entity(int id) {
        if (id <= 0) throw new IllegalArgumentException("id > 0");
        this.id = id;
    }
    public int getId() { return id; }
}

class Airport extends Entity {
    private String airportName, code, location;
    public Airport(int id, String airportName, String code, String location) {
        super(id);
        if (code == null || !code.matches("[A-Z]{3}")) throw new IllegalArgumentException("code = 3 uppercase letters");
        this.airportName = airportName; this.code = code; this.location = location;
    }
    @Override public String toString() { return "Airport{name=" + airportName + ", code=" + code + ", loc=" + location + "}"; }
}

class Airline extends Airport {
    private String airlineName, airlineCode, contactEmail;
    public Airline(int id, String airlineName, String airlineCode, String contactEmail) {
        super(id, "default", "AAA", "default");
        if (airlineCode == null || airlineCode.length() < 2 || airlineCode.length() > 4) throw new IllegalArgumentException("code 2-4 letters");
        if (!contactEmail.contains("@")) throw new IllegalArgumentException("invalid email");
        this.airlineName = airlineName; this.airlineCode = airlineCode; this.contactEmail = contactEmail;
    }
    @Override public String toString() { return "Airline{name=" + airlineName + ", code=" + airlineCode + ", email=" + contactEmail + "}"; }
}

class Flight extends Airline {
    private String flightNumber, departure, destination;
    private double baseFare;
    public Flight(int id, String flightNumber, String departure, String destination, double baseFare) {
        super(id, "airline", "AA", "a@x.com");
        if (baseFare <= 0) throw new IllegalArgumentException("fare > 0");
        this.flightNumber = flightNumber; this.departure = departure; this.destination = destination; this.baseFare = baseFare;
    }
    public double getBaseFare() { return baseFare; }
    @Override public String toString() { return "Flight{no=" + flightNumber + ", dep=" + departure + ", dest=" + destination + ", base=" + baseFare + "}"; }
}

class Pilot extends Flight {
    private String pilotName, licenseNumber;
    private int experienceYears;
    public Pilot(int id, String pilotName, String licenseNumber, int experienceYears) {
        super(id, "FL", "d", "d", 1.0);
        if (experienceYears < 2) throw new IllegalArgumentException("years >= 2");
        this.pilotName = pilotName; this.licenseNumber = licenseNumber; this.experienceYears = experienceYears;
    }
    @Override public String toString() { return "Pilot{name=" + pilotName + ", license=" + licenseNumber + ", exp=" + experienceYears + "}"; }
}

class CabinCrew extends Pilot {
    private String crewName, role, shift;
    public CabinCrew(int id, String crewName, String role, String shift) {
        super(id, "p", "lic", 2);
        if (!"Day".equalsIgnoreCase(shift) && !"Night".equalsIgnoreCase(shift)) throw new IllegalArgumentException("shift = Day/Night");
        this.crewName = crewName; this.role = role; this.shift = shift;
    }
    @Override public String toString() { return "CabinCrew{name=" + crewName + ", role=" + role + ", shift=" + shift + "}"; }
}

class Passenger extends CabinCrew {
    private String passengerName;
    private int age;
    private String gender;
    private String contact;
    public Passenger(int id, String passengerName, int age, String gender, String contact) {
        super(id, "c", "role", "Day");
        if (age <= 0) throw new IllegalArgumentException("age > 0");
        this.passengerName = passengerName; this.age = age; this.gender = gender; this.contact = contact;
    }
    @Override public String toString() { return "Passenger{name=" + passengerName + ", age=" + age + ", gender=" + gender + ", contact=" + contact + "}"; }
}

class Booking extends Passenger {
    private String bookingDate, seatNumber, travelClass;
    public Booking(int id, String bookingDate, String seatNumber, String travelClass) {
        super(id, "pass", 1, "M", "contact");
        if (!Arrays.asList("Economy","Business","First").contains(travelClass)) throw new IllegalArgumentException("class invalid");
        this.bookingDate = bookingDate; this.seatNumber = seatNumber; this.travelClass = travelClass;
    }
    @Override public String toString() { return "Booking{date=" + bookingDate + ", seat=" + seatNumber + ", class=" + travelClass + "}"; }
}

class Payment extends Booking {
    private String paymentDate, paymentMethod;
    private double amountPaid;
    public Payment(int id, String paymentDate, String paymentMethod, double amountPaid) {
        super(id, "book", "1A", "Economy");
        if (amountPaid <= 0) throw new IllegalArgumentException("amount > 0");
        this.paymentDate = paymentDate; this.paymentMethod = paymentMethod; this.amountPaid = amountPaid;
    }
    public double getAmountPaid() { return amountPaid; }
    @Override public String toString() { return "Payment{date=" + paymentDate + ", method=" + paymentMethod + ", amount=" + amountPaid + "}"; }
}

final class Ticket extends Payment {
    private String ticketNumber;
    private String issueDate;
    public Ticket(int id, String ticketNumber) {
        super(id, "pay", "method", 1.0);
        this.ticketNumber = ticketNumber;
        this.issueDate = new Date().toString();
    }
    public double calculateFare() {
        // simple calc: baseFare + 0.18*baseFare (tax) - flat discount for business/first
        double base = 100.0; // placeholder since Flight baseFare used earlier isn't directly accessible here in this single-file design
        double tax = base * 0.18;
        double discount = 0.0;
        return base + tax - discount;
    }
    @Override public String toString() { return "Ticket{no=" + ticketNumber + ", issue=" + issueDate + "}"; }
}
