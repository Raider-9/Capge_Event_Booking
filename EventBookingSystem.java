import java.io.*;
import java.util.*;

public class EventBookingSystem {
    List<Attendee> attendees = new ArrayList<>();
    List<Organizer> organizers = new ArrayList<>();
    List<Event> events = new ArrayList<>();
    List<Ticket> tickets = new ArrayList<>();

    public void registerUser(Attendee attendee) {
        attendees.add(attendee);
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public void bookTicket(String attendeeId, String eventTitle) throws InvalidBookingException {
        Attendee attendee = attendees.stream()
                .filter(a -> a.id.equals(attendeeId))
                .findFirst().orElse(null);
        Event event = events.stream()
                .filter(e -> e.getTitle().equalsIgnoreCase(eventTitle))
                .findFirst().orElse(null);

        if (attendee == null || event == null || !event.isAvailable()) {
            throw new InvalidBookingException("Invalid booking.");
        }

        Ticket ticket = new Ticket(attendee, event);
        tickets.add(ticket);
        event.setAvailableTickets(event.getAvailableTickets() - 1);
        System.out.println("Ticket booked: " + ticket.ticketDetails());
    }

    public void showEvents() {
        events.forEach(System.out::println);
    }

    public void saveEvents() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("events.ser"))) {
            oos.writeObject(events);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadEvents() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("events.ser"))) {
            events = (List<Event>) ois.readObject();
        } catch (Exception e) {
            System.out.println("No previous event data found.");
        }
    }
}

