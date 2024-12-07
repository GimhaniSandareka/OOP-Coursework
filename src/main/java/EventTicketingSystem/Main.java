package EventTicketingSystem;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);

        String answer;
        while (true) {

            //Initialize the Configuration object to collect inputs
            Configuration configuration = new Configuration();  //Create a Configuration object to get the details

            configuration.getInputs(); //get user inputs for ticketing system

            System.out.println("Do you want to continue the program : Y/N");
            answer = input.next();

            //If user wants to continue, proceeding the program
            if (answer.equalsIgnoreCase("Y")) {

                //Threading for Vendor and Customer

                //Creating TicketPool object with  maximum ticket capacity
                TicketPool ticketPool = new TicketPool(configuration.maxTicketCapacity);
                //Creating Vendor object to manage ticket adding to ticket pool
                Vendor vendor = new Vendor(configuration.totalTickets,configuration.ticketReleaseRate, ticketPool);

                Thread v1 = new Thread(vendor);
                v1.setName("vendor1");
                v1.start();

                Thread v2 = new Thread(vendor);
                v2.setName("vendor2");
                v2.start();

                Thread v3 = new Thread(vendor);
                v3.setName("vendor3");
                v3.start();

                Thread v4 = new Thread(vendor);
                v4.setName("vendor4");
                v4.start();

                Thread v5 = new Thread(vendor);
                v5.setName("vendor5");
                v5.start();



                //Creating array of customers
                Customer [] customers = new Customer[10];
                Thread [] customerThreads = new Thread[10];

                for (int i = 0; i < customers.length; i++) {
                    //Retrieve tickets from the ticketPool
                    customers[i] = new Customer(ticketPool, configuration);
                    customerThreads[i]  = new Thread(customers[i], "ID - " + (i + 1));
                    customerThreads[i].start();
                }


                //Get the input from user to stop the program
                System.out.println("Enter 'stop' to terminate the program");
                while (true) {
                    String ans = input.nextLine();
                    if (ans.equalsIgnoreCase("stop")) {
                        v1.interrupt();
                        v2.interrupt();
                        v3.interrupt();
                        v4.interrupt();
                        v5.interrupt();

                        for (Thread customerThread : customerThreads) {
                            customerThread.interrupt();
                        }
                        break; //Exit the loop if the user wants to stop
                    }
                }



            }else if (answer.equalsIgnoreCase("N")) {
                System.out.println("Exiting the program...");
                System.exit(0); //Exit the program if user entered 'N'
            } else {
                System.out.println("Invalid Input! Please enter 'Y' or 'N'.");
            }
            System.out.println("System stopped."); //Stopping the system
            break;
        }

    }

}
