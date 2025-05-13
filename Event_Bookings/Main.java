package Event_Bookings;
import java.util.Scanner;

public class Main{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        EventBookingSystem system = new EventBookingSystem();
        system.loadEvents();

        while (true) {
            System.out.println("\n--- Event Ticket Booking System ---");
            System.out.println("1. Register Attendee");
            System.out.println("2. Add Event");
            System.out.println("3. Book Ticket");
            System.out.println("4. Show Events");
            System.out.println("5. Save & Exit");
            System.out.print("Choose option: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Enter ID: ");
                        String id = sc.nextLine();
                        System.out.print("Enter Name: ");
                        String name = sc.nextLine();
                        system.registerUser(new Attendee(id, name));
                        break;
                    case 2:
                        System.out.print("Enter Event Title: ");
                        String title = sc.nextLine();
                        System.out.print("Enter Ticket Count: ");
                        int count = sc.nextInt();
                        sc.nextLine();
                        system.addEvent(new Event(title, count));
                        break;
                    case 3:
                        System.out.print("Enter Attendee ID: ");
                        String aid = sc.nextLine();
                        System.out.print("Enter Event Title: ");
                        String etitle = sc.nextLine();
                        system.bookTicket(aid, etitle);
                        break;
                    case 4:
                        system.showEvents();
                        break;
                    case 5:
                        system.saveEvents();
                        System.out.println("Saved. Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice.");
                }
            } catch (InvalidBookingException e) {
                System.out.println("Booking failed: " + e.getMessage());
                sc.close();
            }
        }
    }
}
