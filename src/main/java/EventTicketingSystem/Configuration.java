package EventTicketingSystem;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Configuration {
    int totalTickets;
    int ticketReleaseRate;
    int customerRetrievalRate;
    int maxTicketCapacity;


    //Method to get user inputs and validate them
    public void getInputs(){
        Scanner input = new Scanner(System.in);


        //Get the details for inputs with validation
        System.out.println("Enter the total number of tickets: ");
        totalTickets = numberChecker(input);
        System.out.println("Enter the tickets release rate: ");
        ticketReleaseRate = numberChecker(input);
        System.out.println("Enter the customer retrieval rate: ");
        customerRetrievalRate = numberChecker(input);
        System.out.println("Enter the maximum ticket capacity: ");
        maxTicketCapacity = numberChecker(input);


        //Save configuration to JSON and text files
        SaveConfigToJSON.saveToJSON(totalTickets, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity);
        SaveConfigToTextFile.saveToTextFile(totalTickets, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity);
    }

    public int getTotalTickets(){

        return totalTickets;
    }

    public int getTicketReleaseRate(){

        return ticketReleaseRate;
    }

    public int getCustomerRetrievalRate(){

        return customerRetrievalRate;
    }

    public int getMaxTicketCapacity(){

        return maxTicketCapacity;
    }


    //Method to check user inputs if it's a valid integer or not a number
    public int numberChecker(Scanner input){

        int value;

        while(true){

            //Check if input is an integer
            if(input.hasNextInt()){

                value = input.nextInt();

                //Check if the value is greater than 0
                if (value>0){
                    return value; //only when valid input, return the number
                }else{
                    System.out.println("Number must be greater than 0");
                    System.out.println("Enter again: ");
                }

            }else {
                System.out.println("Please enter an integer value: ");
                input.next(); //Clear the invalid input from the Scanner
            }

        }

    }



    //Class to save configuration to a json file
    public class SaveConfigToJSON{

        public static void saveToJSON(int totalTickets, int ticketReleaseRate, int customerRetrievalRate, int maxTicketCapacity){
            //Create JSON String
            String jsonContent = String.format(
                    "{\"totalTickets\":%d,\"ticketReleaseRate\":%d,\"customerRetrievalRate\":%d,\"maxTicketCapacity\":%d}",
                    totalTickets, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity);

            //Write to file
            try(FileWriter fileWriter = new FileWriter("config.json")){
                fileWriter.write(jsonContent);
                System.out.println("Configuration saved to config.json successfully!");
            } catch (IOException e) {
                System.out.println("An error occurred while saving configuration to config.json!");
                e.printStackTrace();
            }
        }
    }



    //Class to save configuration to text file
    public static class SaveConfigToTextFile {
        public static void saveToTextFile(int totalTickets, int ticketReleaseRate, int customerRetrievalRate, int maxTicketCapacity){
            //Create text content
            String textContent = String.format(
                    "Total Tickets: %d\nTicket Release Rate: %d\nCustomer Retrieval Rate: %d\nMax Ticket Capacity: %d",
                    totalTickets, ticketReleaseRate, customerRetrievalRate, maxTicketCapacity);

            //Write to file
            try(FileWriter fileWriter = new FileWriter("config.txt")){
                fileWriter.write(textContent);
                System.out.println("Configuration saved to config.txt successfully!");
            } catch (IOException e) {
                System.out.println("An error occurred while saving configuration to config.txt!");
                e.printStackTrace();
            }
        }
    }



}
