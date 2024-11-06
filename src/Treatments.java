import java.util.*;

public class Treatments {
    static HashMap<String, Double> treatmentPrices = new HashMap<>() {{
        put("Acne Treatment", 2750.00);
        put("Skin Whitening", 7650.00);
        put("Mole Removal", 3850.00);
        put("Laser Treatment", 12500.00);
    }};


    public HashMap<String, Double> getTreatmentPrices() {
        return treatmentPrices;
    }

    public String listAndGetTreatment() {
        int i = 1;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Available treatments:");
        HashMap<String, Double> treatmentPrices = getTreatmentPrices();
        for (Map.Entry<String, Double> entry : treatmentPrices.entrySet()) {  //Lists the available treatments in singular lines
            System.out.println("[" + i + "] " + entry.getKey() + ": " + entry.getValue() + " LKR");
            i++;
        }
        List<String> treatmentList = new ArrayList<>(treatmentPrices.keySet());

        int option = 0;
        while (option < 1 || option > 4) { // Loop  if a number out of the range is entered
            System.out.print("Select the required option by entering a number: ");
            if (scanner.hasNextInt()) { // Check if the input is an integer
                option = scanner.nextInt();
                if (option < 1 || option > 4) {
                    System.out.println("Please enter a number between 1 and 4.");
                }
            } else {
                System.out.println("Please enter a valid integer.");
                scanner.next();
            }
        }
        return treatmentList.get(option - 1);
    }
    }
