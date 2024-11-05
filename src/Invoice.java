import java.util.HashMap;

class Invoice {


    public static double calculateTaxAmount(String treatment){ //Calculates Tax Amount
        Treatments treatments = new Treatments();
        HashMap<String, Double> treatmentPrices = treatments.getTreatmentPrices();
        double tax = 0.025;
        return (treatmentPrices.get(treatment))* tax;
    }
    public static double calculateFinalAmount(String treatment) { // Calculates the amount with tax
        Treatments newInovice = new Treatments();
        HashMap<String, Double> treatmentPrices = newInovice.getTreatmentPrices();
        double tax = 1.025;
        return (treatmentPrices.get(treatment))* tax;

    }
    public static void printInvoice() { //Prints Invoice
        Treatments newInvoice = new Treatments();
        HashMap<String, Double> treatmentPrices = newInvoice.getTreatmentPrices();
        Appointment appointment = null;
        appointment = Appointment.searchAppointmentfromName();
        String treatment = appointment.treatment;
        String registrationFee = appointment.registrationFee;
        if(appointment.registrationFee.equalsIgnoreCase("yes")){
            double basePrice = treatmentPrices.get(treatment);
            double taxAmount = calculateTaxAmount(treatment);
            double finalAmount = (double) Math.round(calculateFinalAmount(treatment) * 100) /100 ;


            System.out.println("Invoice for Appointment ID: " + appointment.appointmentID);
            System.out.println("Patient: " + appointment.patient.Name);
            System.out.println("Dermatologist: " + appointment.Dermatologist);
            System.out.println("Treatment: " + treatment);
            System.out.println("Treatment Price: LKR " + basePrice);
            System.out.println("Tax: LKR " + taxAmount);
            System.out.println("Total Amount: LKR " + finalAmount);
        }
        else{
            System.out.println("Please Pay the registration Fee");
            appointment.registrationFee = Appointment.getRegistrationfee();

        }

    }
}