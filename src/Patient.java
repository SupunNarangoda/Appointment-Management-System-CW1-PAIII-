import java.util.Scanner;

public class Patient {
    String NIC;
    String Name;
    String Email;
    Integer TelNo;

    static Scanner sc = new Scanner(System.in);

    public Patient(String NIC, String Name, String Email, Integer TelNo){
        this.NIC = NIC;
        this.Name = Name;
        this.Email = Email;
        this.TelNo = TelNo;

    }
    public static Patient createPatient(){ //creates the patient instance
        try {
            System.out.print("Enter Patient NIC: ");
            String NIC = validateInput(sc.nextLine().trim());

            System.out.print("Enter Patient Name: ");
            String Name = validateInput(sc.nextLine().trim());

            System.out.print("Enter Patient Email: ");
            String Email = validateInput(sc.nextLine().trim());

            System.out.print("Enter Patient Phone: ");
            Integer TelNo = Integer.parseInt(validateInput(sc.nextLine().trim()));

            return new Patient(NIC, Name, Email, TelNo);

        } catch (NumberFormatException e) {
            System.out.println("Invalid phone number format. Please enter only digits.");
            return createPatient(); // Retry on invalid phone number input

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage()); // Display the validation error message
            return createPatient(); // Retry on invalid input

        } catch (Exception e) {
            System.out.println("An unexpected error occurred. Please try again.");
            return createPatient(); // Retry on unexpected errors
        }

    }
    private static String validateInput(String input) {
        if (input == null || input.trim().isEmpty()) {

            throw new IllegalArgumentException("Input cannot be empty.");
        }return input.trim();

    }
    @Override
    public String toString() {
        return "Patient Details:\n" + "-----------------\n" + "NIC: " + NIC + "\n" + "Name: " + Name + "\n" +
                "Email: " + Email + "\n" + "Phone: " + TelNo + "\n";
    }


}
