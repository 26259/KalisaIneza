package _26259.q5;

import java.util.*;

public class Attendance_Management {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("=== ATTENDANCE MANAGEMENT SYSTEM (Q5) | 26259 ===");

        try {
            System.out.print("Enter Entity id (>0): ");
            int id = Integer.parseInt(sc.nextLine());

            System.out.print("Enter institution name: ");
            String instName = sc.nextLine();
            System.out.print("Enter code (>=3 chars): ");
            String code = sc.nextLine();
            System.out.print("Enter address: ");
            String addr = sc.nextLine();

            Institution inst = new Institution(id, instName, code, addr);

            System.out.print("Enter department name: ");
            String deptName = sc.nextLine();
            System.out.print("Enter department head: ");
            String deptHead = sc.nextLine();

            Department dept = new Department(inst.getId(), deptName, deptHead);

            System.out.print("Enter course name: ");
            String courseName = sc.nextLine();
            System.out.print("Enter course code: ");
            String courseCode = sc.nextLine();
            System.out.print("Enter credits (>0): ");
            int credits = Integer.parseInt(sc.nextLine());

            Course course = new Course(dept.getId(), courseName, courseCode, credits);

            System.out.print("Enter instructor name: ");
            String instructorName = sc.nextLine();
            System.out.print("Enter email: ");
            String instrEmail = sc.nextLine();
            System.out.print("Enter phone (10 digits): ");
            String instrPhone = sc.nextLine();

            Instructor instr = new Instructor(course.getId(), instructorName, instrEmail, instrPhone);

            System.out.print("Enter student name: ");
            String studentName = sc.nextLine();
            System.out.print("Enter student ID (non-empty): ");
            String studentID = sc.nextLine();
            System.out.print("Enter age (>0): ");
            int age = Integer.parseInt(sc.nextLine());

            Student student = new Student(instr.getId(), studentName, studentID, age);

            System.out.print("Enter session date: ");
            String sessionDate = sc.nextLine();
            System.out.print("Enter topic: ");
            String topic = sc.nextLine();

            ClassSession session = new ClassSession(student.getId(), sessionDate, topic);

            System.out.print("Enter status (Present/Absent): ");
            String status = sc.nextLine();

            AttendanceRecord ar = new AttendanceRecord(session.getId(), student.getStudentID(), session.getSessionDate(), status);

            AttendanceSummary summary = new AttendanceSummary(1, new Date().toString(), 0, 0);
            summary.generateSummary(Arrays.asList(ar));

            System.out.println("\n---- ALL INSERTED DATA | 26259 ----");
            System.out.println(inst);
            System.out.println(dept);
            System.out.println(course);
            System.out.println(instr);
            System.out.println(student);
            System.out.println(session);
            System.out.println(ar);
            System.out.println("Attendance Summary: " + summary.getReport() + " | 26259");

        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage() + " | 26259");
        } finally {
            sc.close();
        }
    }
}

class Entity {
    private int id; public Entity(int id){ if (id <= 0) throw new IllegalArgumentException("id > 0"); this.id = id;} public int getId(){ return id;}
}

class Institution extends Entity {
    private String institutionName, code, address;
    public Institution(int id, String institutionName, String code, String address) {
        super(id);
        if (code == null || code.length() < 3) throw new IllegalArgumentException("code >= 3 chars");
        this.institutionName = institutionName; this.code = code; this.address = address;
    }
    @Override public String toString(){ return "Institution{name=" + institutionName + ", code=" + code + ", addr=" + address + "}"; }
}

class Department extends Institution {
    private String departmentName, departmentHead;
    public Department(int id, String departmentName, String departmentHead) {
        super(id, "inst", "INS", "addr");
        if (departmentHead == null || departmentHead.isEmpty()) throw new IllegalArgumentException("departmentHead not empty");
        this.departmentName = departmentName; this.departmentHead = departmentHead;
    }
    @Override public String toString(){ return "Department{name=" + departmentName + ", head=" + departmentHead + "}"; }
}

class Course extends Department {
    private String courseName, courseCode;
    private int credits;
    public Course(int id, String courseName, String courseCode, int credits) {
        super(id, "dept", "head");
        if (credits <= 0) throw new IllegalArgumentException("credits > 0");
        this.courseName = courseName; this.courseCode = courseCode; this.credits = credits;
    }
    @Override public String toString(){ return "Course{name=" + courseName + ", code=" + courseCode + ", credits=" + credits + "}"; }
}

class Instructor extends Course {
    private String instructorName, email, phone;
    public Instructor(int id, String instructorName, String email, String phone) {
        super(id, "course", "C1", 1);
        if (!email.contains("@")) throw new IllegalArgumentException("invalid email");
        if (phone == null || phone.length()!=10) throw new IllegalArgumentException("phone 10 digits");
        this.instructorName = instructorName; this.email = email; this.phone = phone;
    }
    @Override public String toString(){ return "Instructor{name=" + instructorName + ", email=" + email + ", phone=" + phone + "}"; }
}

class Student extends Instructor {
    private String studentName, studentID;
    private int age;
    public Student(int id, String studentName, String studentID, int age) {
        super(id, "ins", "a@b.com", "0123456789");
        if (age <= 0) throw new IllegalArgumentException("age > 0");
        if (studentID == null || studentID.isEmpty()) throw new IllegalArgumentException("studentID not empty");
        this.studentName = studentName; this.studentID = studentID; this.age = age;
    }
    public String getStudentID(){ return studentID; }
    @Override public String toString(){ return "Student{name=" + studentName + ", id=" + studentID + ", age=" + age + "}"; }
}

class ClassSession extends Student {
    private String sessionDate, topic;
    public ClassSession(int id, String sessionDate, String topic) {
        super(id, "st", "S1", 1);
        if (sessionDate == null || sessionDate.isEmpty()) throw new IllegalArgumentException("sessionDate not null");
        this.sessionDate = sessionDate; this.topic = topic;
    }
    public String getSessionDate(){ return sessionDate; }
    @Override public String toString(){ return "ClassSession{date=" + sessionDate + ", topic=" + topic + "}"; }
}

class AttendanceRecord extends ClassSession {
    private String studentID, sessionID, status;
    public AttendanceRecord(int id, String studentID, String sessionID, String status) {
        super(id, "d", "topic");
        if (!status.equalsIgnoreCase("Present") && !status.equalsIgnoreCase("Absent")) throw new IllegalArgumentException("status Present/Absent");
        this.studentID = studentID; this.sessionID = sessionID; this.status = status;
    }
    public String getStatus(){ return status; }
    @Override public String toString(){ return "AttendanceRecord{studentID=" + studentID + ", sessionID=" + sessionID + ", status=" + status + "}"; }
}

class LeaveRequest extends AttendanceRecord {
    private String requestDate, reason;
    private boolean approved;
    public LeaveRequest(int id, String requestDate, String reason, boolean approved) {
        super(id, "stud", "sess", "Absent");
        if (reason == null || reason.isEmpty()) throw new IllegalArgumentException("reason not empty");
        this.requestDate = requestDate; this.reason = reason; this.approved = approved;
    }
    @Override public String toString(){ return "LeaveRequest{date=" + requestDate + ", reason=" + reason + ", approved=" + approved + "}"; }
}

final class AttendanceSummary extends LeaveRequest {
    private String reportDate;
    private int totalPresent, totalAbsent;
    private String report;
    public AttendanceSummary(int id, String reportDate, int totalPresent, int totalAbsent) {
        super(id, "d", "r", true);
        this.reportDate = reportDate; this.totalPresent = totalPresent; this.totalAbsent = totalAbsent;
    }
    public void generateSummary(List<AttendanceRecord> records) {
        int present = 0, absent = 0;
        for (AttendanceRecord r : records) {
            if ("Present".equalsIgnoreCase(r.getStatus())) present++; else absent++;
        }
        this.totalPresent = present;
        this.totalAbsent = absent;
        this.report = "ReportDate=" + reportDate + ", Present=" + present + ", Absent=" + absent;
    }
    public String getReport(){ return report; }
}
