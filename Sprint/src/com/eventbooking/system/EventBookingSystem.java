package com.eventbooking.system;

import com.eventbooking.exception.InvalidBookingException;
import com.eventbooking.model.*;
import java.io.*;
import java.util.*;

public class EventBookingSystem {
    private List<Attendee> attendees = new ArrayList<>();
    private List<Organizer> organizers = new ArrayList<>();
    private List<Event> events = new ArrayList<>();
    private List<Ticket> tickets = new ArrayList<>();
    private Admin admin;
    
    public void registerUser(User user) {
        if (user instanceof Attendee) {
            attendees.add((Attendee) user);
        } else if (user instanceof Organizer) {
            organizers.add((Organizer) user);
        } else if (user instanceof Admin) {
            this.admin = (Admin) user;
        }
    }
    
    public void addEvent(Event event) {
        events.add(event);
    }
    
    public void bookTicket(Attendee attendee, Event event) throws InvalidBookingException {
        if (!event.isAvailable()) {
            throw new InvalidBookingException("No tickets available for " + event.getTitle());
        }
        
        Ticket ticket = new Ticket(attendee, event);
        tickets.add(ticket);
        event.setAvailableTickets(event.getAvailableTickets() - 1);
        System.out.println("Ticket booked successfully!");
    }
    
    public void saveEvents() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("events.dat"))) {
            oos.writeObject(events);
            System.out.println("Events saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving events: " + e.getMessage());
        }
    }
    
    @SuppressWarnings("unchecked")
    public void loadEvents() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("events.dat"))) {
            events = (List<Event>) ois.readObject();
            System.out.println("Events loaded successfully.");
        } catch (FileNotFoundException e) {
            System.out.println("No saved events found. Starting with empty event list.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading events: " + e.getMessage());
        }
    }
    
    public void showEvents() {
        if (events.isEmpty()) {
            System.out.println("No events available.");
            return;
        }
        
        System.out.println("\nAvailable Events:");
        for (int i = 0; i < events.size(); i++) {
            System.out.println((i+1) + ". " + events.get(i));
        }
    }
    
    public List<Event> getEvents() {
        return events;
    }
    
    public List<Attendee> getAttendees() {
        return attendees;
    }
    
    public Admin getAdmin() {
        return admin;
    }
    
    public List<Ticket> getTickets() {
        return tickets;
    }
}
