import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Appointment {
    int appointmentID;
    String Time;
    String date;
    String day;
    Patient patient;
    String Dermatologist;
    String treatment;
    String registrationFee;
    static int aIDCounter = 1;

    static List<Appointment> appointments = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);

    public Appointment(String Time, String date,String day, Patient patient, String Dermatologist,String treatment, String registrationFee) {
        this.appointmentID = aIDCounter++;
        this.Time = Time;
        this.date = date;
        this.day = day;
        this.patient = patient;
        this.Dermatologist = Dermatologist;
        this.treatment = treatment;
        this.registrationFee = registrationFee;

    }

    static Dermatalogist DermatalogistA = new Dermatalogist("jhon",initializeSchedule());
    static Dermatalogist DermatalogistB = new Dermatalogist("david",initializeSchedule());

    private static Map<String, List<String>> initializeSchedule() {
        Map<String, List<String>> schedule = new HashMap<>();
        schedule.put("monday", Arrays.asList("10:00", "10:15", "10:30", "10:45", "11:00", "11:15", "11:30", "11:45", "12:00", "12:15", "12:30", "12:45"));
        schedule.put("wednesday", Arrays.asList("14:00", "14:15", "14:30", "14:45", "15:00", "15:15", "15:30", "15:45", "16:00", "16:15", "16:30", "16:45"));
        schedule.put("friday", Arrays.asList("16:00", "16:15", "16:30", "16:45", "17:00", "17:15", "17:30", "17:45", "18:00", "18:15", "18:30", "18:45", "19:00", "19:15", "19:30", "19:45"));
        schedule.put("saturday", Arrays.asList("09:00", "09:15", "09:30", "09:45", "10:00", "10:15", "10:30", "10:45", "11:00", "11:15", "11:30", "11:45", "12:00", "12:15"));
        return schedule;
    }
    private static final HashMap<Integer,String> appointmentUpdate = new HashMap<>() {{
        put(1,"Treatment Type");
        put(2,"Dermatologist");
        put(3,"Appointment Date");
        put(4,"Appointment Time");
    }};

    public static void makeAppointment() { // Creates an appointment
        try {
            Patient patient = Patient.createPatient();
            Treatments treatments = new Treatments();
            String Treatment = treatments.listAndGetTreatment();
            System.out.print("Select a Dermatologist (Jhon / David): ");
            String Dermatologist = validateInput(scanner.nextLine());
            System.out.print("\nEnter Appointment Day (Monday/Wednesday/Friday/Saturday): ");
            String day = validateInput(scanner.nextLine()).trim();
            System.out.print("\nEnter Appointment Date (YYYY-MM-DD): ");
            String date = validateDateInput(scanner.nextLine());
            AvailableSlots(day,Dermatologist.toLowerCase());
            System.out.print("Enter Appointment Time (00:00am): ");
            String Time = validateInput(scanner.nextLine());
            checkifAppointmentTimeAvailable(day, Time, Dermatologist);
            String registrationFee = getRegistrationfee();

            Appointment appointment = new Appointment(Time, date, day, patient, Dermatologist, Treatment, registrationFee);
            System.out.println(appointment);
            appointments.add(appointment);
        } catch (InputMismatchException | IndexOutOfBoundsException e) {
            System.out.println("Error: Invalid selection. Please enter a valid option.");
            makeAppointment();
        } catch (IllegalArgumentException | DateTimeParseException e) {
            System.out.println("Error: " + e.getMessage());
            makeAppointment();
        }
    }




    public static void updateAppointmentDetails(){
        try {
            Appointment appointmentToUpdate = null;
            appointmentToUpdate = searchAppointmentfromName();
            System.out.println("Which Parameter should be changed\n[1] Treatment Type\n[2] Dermatologist\n[3] Appointment Date\n[4] Appointment Time");
            int choice2ForMap = scanner.nextInt();
            String choice2 = appointmentUpdate.get(choice2ForMap);

            boolean Updated = false;
            if(choice2.equalsIgnoreCase("treatment type")){
                Treatments treatments = new Treatments();

                String newTreatment = treatments.listAndGetTreatment();

                // Assuming we have a way to update the treatment in the appointment or invoice
                appointmentToUpdate.treatment = newTreatment;
                System.out.println("Treatment updated to: " + newTreatment);
                Updated = true;

            } else if (choice2.equalsIgnoreCase("Dermatologist")) {
                scanner.nextLine();
                System.out.print("Select a new Dermatologist (Jhon / David): ");
                String newDermatologist = validateInput(scanner.nextLine());
                appointmentToUpdate.Dermatologist = newDermatologist;
                System.out.println("Dermatologist updated to: " + newDermatologist);
                Updated = true;

            } else if (choice2.equalsIgnoreCase("Appointment Date")) {
                scanner.nextLine();
                System.out.println("Enter new appointment date (YYYY-MM-DD): ");
                String newDate = validateDateInput(scanner.nextLine());
                appointmentToUpdate.date = newDate;
                System.out.println("Appointment date updated to: " + newDate);
                Updated = true;

            } else if (choice2.equalsIgnoreCase("Appointment Time")) {
                scanner.nextLine();
                AvailableSlots(appointmentToUpdate.day,appointmentToUpdate.Dermatologist);
                System.out.println("Enter new appointment time (00:00): ");
                String newTime = validateInput(scanner.nextLine());
                appointmentToUpdate.Time = newTime;
                System.out.println("Appointment time updated to: " + newTime);
                Updated = true;

            } else {
                System.out.println("Invalid choice. No updates made.");
            }

            // Print the updated appointment details only if an update was made
            if (Updated) {
                System.out.println("Appointment Updated\n" + appointmentToUpdate);
            }


        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            updateAppointmentDetails(); // Retry on invalid input
        } catch (Exception e) {
            System.out.println("An unexpected error occurred. Please try again.");
            updateAppointmentDetails(); // Retry on unexpected errors
        }

    }

    public static Appointment searchAppointmentfromName(){ // Search the appointment list for the specific name
        scanner.nextLine();
        System.out.print("Enter the Patient Name to search: ");
        String name = scanner.nextLine().trim();
        for (Appointment appointment : appointments) {
            if (appointment.patient.Name.equalsIgnoreCase(name)) {
                System.out.println(appointment);
                return appointment;
            }
        }
        System.out.println("No appointment under the following name");
        return null;

    }
    public static void checkifAppointmentTimeAvailable(String day, String time, String dermatologist){ //Checks if timeslot is available
        for (Appointment appointment : appointments) {
            if (appointment.day.equalsIgnoreCase(day) && appointment.Time.equals(time)&& appointment.Dermatologist.equalsIgnoreCase(dermatologist)) {
                System.out.println("The selected time slot is already taken for Dermatologist " + dermatologist + ". Please choose a different time.");
                makeAppointment();
                return; // Exit the method if the time slot is taken
            }
        }
    }
    public static void AvailableSlots(String day,String Dermatologist) { //Lists all available time slots

        List<String> availableSlotsA = new ArrayList<>(DermatalogistA.getSchedule().get(day.toLowerCase()));
        List<String> availableSlotsB = new ArrayList<>(DermatalogistB.getSchedule().get(day.toLowerCase()));

        // Remove booked slots for each dermatologist
        for (Appointment appointment : appointments) {
            if (appointment.day.equalsIgnoreCase(day)) {
                if (appointment.Dermatologist.equalsIgnoreCase(DermatalogistA.getName())) {
                    availableSlotsA.remove(appointment.Time);
                } else if (appointment.Dermatologist.equalsIgnoreCase(DermatalogistB.getName())) {
                    availableSlotsB.remove(appointment.Time);
                }
            }
        }
        if (Dermatologist.equalsIgnoreCase(DermatalogistA.getName())) {
            // Display available slots for Dermatologist A
            System.out.println("Available slots for " + DermatalogistA.getName() + " on " + day + ": " + availableSlotsA);
        } else {
            // Display available slots for Dermatologist B
            System.out.println("Available slots for " + DermatalogistB.getName() + " on " + day + ": " + availableSlotsB);

        }
    }
    public static void viewAppointment(){
        System.out.print("Enter the Date (Monday): ");
        String date = validateInput(scanner.nextLine());

        for (Appointment appointment : appointments) {
            if (appointment.day.equalsIgnoreCase(date)) {
                System.out.println("Appointments on " + date + ":");
                System.out.println(appointment);
            }
            else{
                System.out.println("No Appointments on "+ date);
            }
        }
    }
    public static void searchAppointmentByID() {
        System.out.print("Enter the Appointment ID to search: ");
        int appointmentID = scanner.nextInt();
        scanner.nextLine();
        for (Appointment appointment : appointments) {
            if (appointment.appointmentID == appointmentID) {
                System.out.println(appointment);
            }
        }
    }
    public static String getRegistrationfee(){
        System.out.print("Select (Yes/No) for the payment of the registration fee: ");
        return  validateInput(scanner.nextLine());
    }
    // Helper method for general input validation
    private static String validateInput(String input) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException("Input cannot be empty.");
        }return input.trim();

    }

    // Helper method to validate date input
    private static String validateDateInput(String date) {
        LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return date;
    }

    @Override
    public String toString() {
        return "Appointment Details:\n" + "-----------------------\n" + "ID: " + appointmentID + "\n" +
                "Dermatologist: " + Dermatologist + "\n" + "Date: " + date + "\n" + "Time: " + Time + "\n" +
                "Treatment: " + treatment + "\n" + patient + "\n";
    }
}
