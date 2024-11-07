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
        String NIC = null;
        while (NIC == null) {
            System.out.print("Enter Patient NIC: ");
            try {
                NIC = validateInput(sc.nextLine().trim());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        String Name = null;
        while (Name == null) {
            System.out.print("Enter Patient Name: ");
            try {
                Name = validateInput(sc.nextLine().trim());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        String Email = null;
        while (Email == null) {
            System.out.print("Enter Patient Email: ");
            try {
                Email = validateInput(sc.nextLine().trim());
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }

        Integer TelNo = null;
        while (TelNo == null) {
            System.out.print("Enter Patient Phone (eg:0712345678): ");
            try {
                TelNo = Integer.parseInt(validateInput(sc.nextLine().trim()));
                // Check if TelNo is a 10-digit number
                if (TelNo.toString().length() != 9) {
                    System.out.println("Phone number must be exactly 9 digits excluding the 0");
                    TelNo = null;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Phone number must be exactly 9 digits excluding the 0");

            }
        }

        return new Patient(NIC, Name, Email, TelNo);


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
