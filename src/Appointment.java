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

    static Dermatologist DermatalogistA = new Dermatologist("jhon",initializeSchedule());
    static Dermatologist DermatalogistB = new Dermatologist("david",initializeSchedule());

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
        Patient patient = Patient.createPatient();
        Treatments treatments = new Treatments();
        String Treatment = treatments.listAndGetTreatment();

        String dermatologist = null;
        while (dermatologist == null) {
            System.out.print("Select a Dermatologist (Jhon / David): ");
            try {
                dermatologist = validateInput(scanner.nextLine()).toLowerCase();
                if (!dermatologist.equals("jhon") && !dermatologist.equals("david")) {
                    dermatologist = null;
                    System.out.println("Please enter an available Dermatologist");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        String day = null;
        while (day == null) {
            System.out.print("Enter Appointment Day (Monday/Wednesday/Friday/Saturday): ");
            try {
                day = validateInput(scanner.nextLine()).trim();
                // Check if the entered day is one of the allowed options
                if (!day.equalsIgnoreCase("Monday") && !day.equalsIgnoreCase("Wednesday") &&
                        !day.equalsIgnoreCase("Friday") && !day.equalsIgnoreCase("Saturday")) {
                    day = null;
                    System.out.println("Please enter an available day");
                }
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        String date = null;
        while (date == null) {
            System.out.print("Enter Appointment Date (YYYY-MM-DD): ");
            try {
                date = validateDateInput(scanner.nextLine());
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter in YYYY-MM-DD format.");
            }
        }

        AvailableSlots(day, dermatologist.toLowerCase());
        String time = null;
        while (time == null) {
            System.out.print("Enter Appointment Time (HH:mm): ");
            try {
                time = validateInput(scanner.nextLine());
                checkifAppointmentTimeAvailable(day, time, dermatologist);
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        String registrationFee = getRegistrationfee();

        Appointment appointment = new Appointment(time, date, day, patient, dermatologist, Treatment, registrationFee);
        System.out.println(appointment);
        appointments.add(appointment);

    }




    public static void updateAppointmentDetails(){
        Appointment appointmentToUpdate = null;
        appointmentToUpdate = searchAppointmentfromName();
        int choice2ForMap = 0;
        if(appointmentToUpdate != null) {
            while (choice2ForMap < 1 || choice2ForMap > 4) {
                System.out.print("Which Parameter should be changed\n[1] Treatment Type\n[2] Dermatologist\n[3] Appointment Date\n[4] Appointment Time\nSelection: ");
//                System.out.print("Selection: ");
                if (scanner.hasNextInt()) { // Check if the input is an integer
                    choice2ForMap = scanner.nextInt();
                    if (choice2ForMap < 1 || choice2ForMap > 4) {
                        System.out.println("Please enter a number between 1 and 4.");
                    }
                } else {
                    System.out.println("Please enter a valid integer.");
                    scanner.next();
                }
            }
            String choice2 = appointmentUpdate.get(choice2ForMap);

            if (choice2.equalsIgnoreCase("treatment type")) {
                Treatments treatments = new Treatments();
                // Assuming we have a way to update the treatment in the appointment or invoice
                String newTreatment = null;
                while (newTreatment == null) {
                    try {
                        newTreatment = treatments.listAndGetTreatment();
                        appointmentToUpdate.treatment = newTreatment;
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                System.out.println("Treatment updated to: " + newTreatment);

            } else if (choice2.equalsIgnoreCase("Dermatologist")) {
                scanner.nextLine();
                String newDermatologist = null;
                while (newDermatologist == null) {
                    System.out.print("Select a new Dermatologist (Jhon / David): ");
                    try {
                        newDermatologist = validateInput(scanner.nextLine());
                        appointmentToUpdate.Dermatologist = newDermatologist;
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                }
                System.out.println("Dermatologist updated to: " + newDermatologist);

            } else if (choice2.equalsIgnoreCase("Appointment Date")) {
                scanner.nextLine();
                String newDate = null;
                while (newDate == null) {
                    System.out.print("Enter new appointment date (YYYY-MM-DD): ");
                    try {
                        newDate = validateDateInput(scanner.nextLine());
                        appointmentToUpdate.date = newDate;
                    } catch (DateTimeParseException e) {
                        System.out.println("Invalid date format. Please enter in YYYY-MM-DD format.");
                    }
                }
                System.out.println("Appointment date updated to: " + newDate);

            } else if (choice2.equalsIgnoreCase("Appointment Time")) {
                scanner.nextLine();
                AvailableSlots(appointmentToUpdate.day, appointmentToUpdate.Dermatologist);
                String newTime = null;
                while (newTime == null) {
                    System.out.print("Enter new appointment time (HH:mm): ");
                    try {
                        newTime = validateInput(scanner.nextLine());
                        checkifAppointmentTimeAvailable(appointmentToUpdate.day, newTime, appointmentToUpdate.Dermatologist);
                        appointmentToUpdate.Time = newTime;
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                }
                System.out.println("Appointment time updated to: " + newTime);

            } else {
                System.out.println("Invalid choice. No updates made.");
            }

            // Print the updated appointment details only if an update was made

            System.out.println("Appointment Updated\n" + appointmentToUpdate);

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
                return;
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
        String date = null;
        while (date == null) {
            System.out.print("Enter Appointment Date (YYYY-MM-DD): ");
            try {
                date = validateInput(scanner.nextLine()).trim();
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        for (Appointment appointment : appointments) {
            if (appointment.date.equalsIgnoreCase(date)) {
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
