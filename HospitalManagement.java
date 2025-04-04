/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package rhms;

/**
 *
 * @author maryamasif
 */
import java.util.*;

abstract class User {
    protected String name;
    protected int id;
    protected String role;

    public User(String name, int id, String role) {
        this.name = name;
        this.id = id;
        this.role = role;
    }

    public String getName() { return name; }
    public int getId() { return id; }
    public String getRole() { return role; }
}

class Patient extends User {
    private List<VitalSign> vitals = new ArrayList<>();
    private List<Appointment> appointments = new ArrayList<>();
    private List<Feedback> feedbacks = new ArrayList<>();
    private List<Prescription> prescriptions = new ArrayList<>();
    private MedicalHistory medicalHistory;

    public Patient(String name, int id) {
        super(name, id, "Patient");
    }

    public void addVitalSign(VitalSign vital) { vitals.add(vital); }
    public void scheduleAppointment(Appointment appointment) { appointments.add(appointment); }
    public void receiveFeedback(Feedback feedback) { feedbacks.add(feedback); }
    public void addPrescription(Prescription prescription) { prescriptions.add(prescription); }
    public void setMedicalHistory(MedicalHistory medicalHistory) { this.medicalHistory = medicalHistory; }

    public List<VitalSign> getVitals() { return vitals; }
    public List<Appointment> getAppointments() { return appointments; }
    public List<Feedback> getFeedbacks() { return feedbacks; }
    public List<Prescription> getPrescriptions() { return prescriptions; }
    public MedicalHistory getMedicalHistory() { return medicalHistory; }
}

class Doctor extends User {
    private List<Patient> patients = new ArrayList<>();

    public Doctor(String name, int id) {
        super(name, id, "Doctor");
    }

    public void addPatient(Patient patient) { patients.add(patient); }
    public void provideFeedback(Patient patient, String message) {
        Feedback feedback = new Feedback(this, patient, message);
        patient.receiveFeedback(feedback);
    }
    public void viewPatientData(Patient patient) {
        System.out.println("\n=== Patient Data for " + patient.getName() + " ===");
        for (VitalSign v : patient.getVitals()) {
            System.out.println(v);
        }
        if (patient.getMedicalHistory() != null) {
            System.out.println("\nMedical History: " + patient.getMedicalHistory());
        }
    }
    public void providePrescription(Patient patient, String prescriptionDetails) {
        Prescription prescription = new Prescription(this, patient, prescriptionDetails);
        patient.addPrescription(prescription);
    }

    public List<Patient> getPatients() { return patients; }
    
    
}


class VitalSign {
    private int heartRate;
    private int oxygenLevel;
    private int bloodPressure;
    private double temperature;

    public VitalSign(int heartRate, int oxygenLevel, int bloodPressure, double temperature) {
        this.heartRate = heartRate;
        this.oxygenLevel = oxygenLevel;
        this.bloodPressure = bloodPressure;
        this.temperature = temperature;
    }
class VitalsDatabase {
    private Map<Integer, List<VitalSign>> vitalsData; 

    public VitalsDatabase() {
        vitalsData = new HashMap<>();
    }

    // Method to add vitals for a patient
    public void addVitals(int patientId, VitalSign vitalSign) {
        vitalsData.putIfAbsent(patientId, new ArrayList<>());
        vitalsData.get(patientId).add(vitalSign);
    }

    // Method to retrieve vitals for a patient
    public List<VitalSign> getVitals(int patientId) {
        return vitalsData.getOrDefault(patientId, new ArrayList<>());
    }

    // Method to display vitals of a patient
    public void displayVitals(int patientId) {
        List<VitalSign> vitals = vitalsData.get(patientId);
        if (vitals != null && !vitals.isEmpty()) {
            System.out.println("=== Vitals for Patient ID: " + patientId + " ===");
            for (VitalSign vital : vitals) {
                System.out.println(vital);
            }
        } else {
            System.out.println("No vitals found for Patient ID: " + patientId);
        }
    }
}

    public String toString() {
        return "Heart Rate: " + heartRate + " bpm, Oxygen: " + oxygenLevel + "%" + ", BP: " + bloodPressure + " mmHg, Temp: " + temperature + "°C";
    }
}

class Appointment {
    private String date;
    private Doctor doctor;
    private Patient patient;
    private String status;

    public Appointment(String date, Doctor doctor, Patient patient) {
        this.date = date;
        this.doctor = doctor;
        this.patient = patient;
        this.status = "Pending";
    }

    public void approve() { status = "Approved"; }
    public void cancel() { status = "Canceled"; }

    public String toString() {
        return "Appointment on " + date + " with Dr. " + doctor.getName() + " (Status: " + status + ")";
    }
}

class AppointmentManager {
    private List<Appointment> appointments = new ArrayList<>();

    public void requestAppointment(Appointment appointment) { appointments.add(appointment); }
    public void approveAppointment(Appointment appointment) { appointment.approve(); }
    public void cancelAppointment(Appointment appointment) { appointment.cancel(); }

    public List<Appointment> getAppointments() { return appointments; }
    public void viewAppointments() {
        for (Appointment appointment : appointments) {
            System.out.println(appointment);
        }
    }
}

class Feedback {
    private Doctor doctor;
    private Patient patient;
    private String message;

    public Feedback(Doctor doctor, Patient patient, String message) {
        this.doctor = doctor;
        this.patient = patient;
        this.message = message;
    }

    public String toString() {
        return "Feedback by Dr. " + doctor.getName() + " to " + patient.getName() + ": " + message;
    }
}

import java.util.*;

class Feedback {
    private Doctor doctor;
    private Patient patient;
    private String message;
    private Date date;

    public Feedback(Doctor doctor, Patient patient, String message, Date date) {
        this.doctor = doctor;
        this.patient = patient;
        this.message = message;
        this.date = date;
    }

    public String toString() {
        return "Feedback by Dr. " + doctor.getName() + " to " + patient.getName() + ": " 
               + message + " (Date: " + date + ")";
    }

    public Date getDate() {
        return date;
    }
}

class Prescription {
    private Doctor doctor;
    private Patient patient;
    private List<Medication> medications; // List of medications prescribed
    private Date date;

    public Prescription(Doctor doctor, Patient patient, List<Medication> medications, Date date) {
        this.doctor = doctor;
        this.patient = patient;
        this.medications = medications;
        this.date = date;
    }

    public String toString() {
        StringBuilder prescriptionDetails = new StringBuilder("Prescription for " + patient.getName() + ": ");
        for (Medication medication : medications) {
            prescriptionDetails.append(medication.toString() + ", ");
        }
        prescriptionDetails.append("(Date: " + date + ")");
        return prescriptionDetails.toString();
    }

    public Date getDate() {
        return date;
    }
}

class Medication {
    private String name;
    private String dosage;
    private String schedule;

    public Medication(String name, String dosage, String schedule) {
        this.name = name;
        this.dosage = dosage;
        this.schedule = schedule;
    }

    public String toString() {
        return name + " (" + dosage + ", " + schedule + ")";
    }
}

class MedicalHistory {
    private List<String> medicalConditions = new ArrayList<>();
    private List<Prescription> prescriptions = new ArrayList<>(); 

    public void addCondition(String condition) {
        medicalConditions.add(condition);
    }

    public void addPrescription(Prescription prescription) {
        prescriptions.add(prescription);
    }

    public String toString() {
        StringBuilder historyDetails = new StringBuilder("Medical Conditions: " + String.join(", ", medicalConditions));
        historyDetails.append("\nPrescriptions: ");
        for (Prescription prescription : prescriptions) {
            historyDetails.append(prescription.toString() + "\n");
        }
        return historyDetails.toString();
    }
}


class Administrator extends User {
    public Administrator(String name, int id) {
        super(name, id, "Administrator");
    }

    public void addPatient(Doctor doctor, Patient patient) {
        doctor.addPatient(patient);
    }

    public void removePatient(Doctor doctor, Patient patient) {
        doctor.getPatients().remove(patient);
    }

    public void manageAppointments(AppointmentManager apptMgr) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("\n=== Manage Appointments ===");
        apptMgr.viewAppointments();
        System.out.print("Enter the appointment number to approve/cancel: ");
        int appointmentIndex = scanner.nextInt();
        scanner.nextLine();
        if (appointmentIndex >= 0 && appointmentIndex < apptMgr.getAppointments().size()) {
            Appointment appt = apptMgr.getAppointments().get(appointmentIndex);
            System.out.print("Approve or Cancel appointment? (approve/cancel): ");
            String action = scanner.nextLine();
            if (action.equalsIgnoreCase("approve")) {
                apptMgr.approveAppointment(appt);
            } else if (action.equalsIgnoreCase("cancel")) {
                apptMgr.cancelAppointment(appt);
            } else {
                System.out.println("Invalid action.");
            }
        } else {
            System.out.println("Invalid appointment number.");
        }
    }
}

public class HospitalManagement {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Doctor> doctors = new ArrayList<>();
        List<Patient> patients = new ArrayList<>();
        AppointmentManager apptMgr = new AppointmentManager();
        Administrator admin = new Administrator("Admin", 1);

        while (true) {
            System.out.println("\n=== Hospital Management System ===");
            System.out.println("1. Add Doctor");
            System.out.println("2. Add Patient");
            System.out.println("3. Add Vital Sign to Patient");
            System.out.println("4. Schedule Appointment");
            System.out.println("5. View Patient Data");
            System.out.println("6. Provide Feedback");
            System.out.println("7. Manage Appointments");
            System.out.println("8. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter doctor name: ");
                    String doctorName = scanner.nextLine();
                    System.out.print("Enter doctor ID: ");
                    int doctorId = scanner.nextInt();
                    scanner.nextLine();
                    Doctor doctor = new Doctor(doctorName, doctorId);
                    doctors.add(doctor);
                    break;

                case 2:
                    System.out.print("Enter patient name: ");
                    String patientName = scanner.nextLine();
                    System.out.print("Enter patient ID: ");
                    int patientId = scanner.nextInt();
                    scanner.nextLine();
                    Patient patient = new Patient(patientName, patientId);
                    patients.add(patient);
                    break;

                case 3:
                    System.out.print("Enter patient ID: ");
                    int patientIdForVitals = scanner.nextInt();
                    scanner.nextLine();
                    Patient patientForVitals = null;
                    for (Patient p : patients) {
                        if (p.getId() == patientIdForVitals) {
                            patientForVitals = p;
                            break;
                        }
                    }
                    if (patientForVitals != null) {
                        System.out.print("Enter heart rate: ");
                        int heartRate = scanner.nextInt();
                        System.out.print("Enter oxygen level: ");
                        int oxygenLevel = scanner.nextInt();
                        System.out.print("Enter blood pressure: ");
                        int bloodPressure = scanner.nextInt();
                        System.out.print("Enter temperature: ");
                        double temperature = scanner.nextDouble();
                        scanner.nextLine();
                        VitalSign vital = new VitalSign(heartRate, oxygenLevel, bloodPressure, temperature);
                        patientForVitals.addVitalSign(vital);
                    } else {
                        System.out.println("Patient not found.");
                    }
                    break;

                case 4:
                    System.out.print("Enter patient ID for appointment: ");
                    int patientIdForAppt = scanner.nextInt();
                    scanner.nextLine();
                    Patient patientForAppt = null;
                    for (Patient p : patients) {
                        if (p.getId() == patientIdForAppt) {
                            patientForAppt = p;
                            break;
                        }
                    }
                    if (patientForAppt != null) {
                        System.out.print("Enter doctor ID for appointment: ");
                        int doctorIdForAppt = scanner.nextInt();
                        scanner.nextLine();
                        Doctor doctorForAppt = null;
                        for (Doctor d : doctors) {
                            if (d.getId() == doctorIdForAppt) {
                                doctorForAppt = d;
                                break;
                            }
                        }
                        if (doctorForAppt != null) {
                            System.out.print("Enter appointment date (YYYY-MM-DD): ");
                            String date = scanner.nextLine();
                            Appointment appt = new Appointment(date, doctorForAppt, patientForAppt);
                            patientForAppt.scheduleAppointment(appt);
                            apptMgr.requestAppointment(appt);
                        } else {
                            System.out.println("Doctor not found.");
                        }
                    } else {
                        System.out.println("Patient not found.");
                    }
                    break;

                case 5:
                    System.out.print("Enter patient ID to view data: ");
                    int patientIdForData = scanner.nextInt();
                    scanner.nextLine();
                    Patient patientForData = null;
                    for (Patient p : patients) {
                        if (p.getId() == patientIdForData) {
                            patientForData = p;
                            break;
                        }
                    }
                    if (patientForData != null) {
                        System.out.print("Enter doctor ID to view data: ");
                        int doctorIdForData = scanner.nextInt();
                        scanner.nextLine();
                        Doctor doctorForData = null;
                        for (Doctor d : doctors) {
                            if (d.getId() == doctorIdForData) {
                                doctorForData = d;
                                break;
                            }
                        }
                        if (doctorForData != null) {
                            doctorForData.viewPatientData(patientForData);
                        } else {
                            System.out.println("Doctor not found.");
                        }
                    } else {
                        System.out.println("Patient not found.");
                    }
                    break;

                case 6:
                    System.out.print("Enter patient ID for feedback: ");
                    int patientIdForFeedback = scanner.nextInt();
                    scanner.nextLine();
                    Patient patientForFeedback = null;
                    for (Patient p : patients) {
                        if (p.getId() == patientIdForFeedback) {
                            patientForFeedback = p;
                            break;
                        }
                    }
                    if (patientForFeedback != null) {
                        System.out.print("Enter feedback: ");
                        String feedbackMessage = scanner.nextLine();
                        System.out.print("Enter doctor ID for feedback: ");
                        int doctorIdForFeedback = scanner.nextInt();
                        scanner.nextLine();
                        Doctor doctorForFeedback = null;
                        for (Doctor d : doctors) {
                            if (d.getId() == doctorIdForFeedback) {
                                doctorForFeedback = d;
                                break;
                            }
                        }
                        if (doctorForFeedback != null) {
                            doctorForFeedback.provideFeedback(patientForFeedback, feedbackMessage);
                        } else {
                            System.out.println("Doctor not found.");
                        }
                    } else {
                        System.out.println("Patient not found.");
                    }
                    break;

                case 7:
                    admin.manageAppointments(apptMgr);
                    break;

                case 8:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid option! Please try again.");
            }
        }
    }
}
