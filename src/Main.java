import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        String choice;

        do {
            System.out.println("\n----- |Aurora Skin Care System| -----\n");
            System.out.println("[1]. Make an Appointment");
            System.out.println("[2]. View Appointments by Day");
            System.out.println("[3]. Search Appointment by Patient Name");
            System.out.println("[4]. Search Appointment by ID");
            System.out.println("[5]. Generate Invoice");
            System.out.println("[6]. Update Appointment Details\n");
            System.out.print("Select the required option by entering the specific number: ");
            int option = scanner.nextInt();

            if (option == 1) { //Selects the option based on the number entered
                Appointment.makeAppointment();
            } else if (option == 2) {
                Appointment.viewAppointment();
            } else if (option == 3) {
                Appointment.searchAppointmentfromName();
            } else if (option == 4) {
                Appointment.searchAppointmentByID();
            } else if (option == 5) {
                Invoice.printInvoice();
            } else if (option == 6) {
                Appointment.updateAppointmentDetails();
            } else if (option == 7) {
                System.out.println("Closing the system.");
            } else {
                System.out.println("Invalid option");
            }
            scanner.nextLine();

            System.out.print("If you need to perform another operation Select (yes/no): ");
            choice = scanner.nextLine();
        } while (choice.equalsIgnoreCase("yes"));

        scanner.close();
    }
}

