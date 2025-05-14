package com.eventbooking.main;

import com.eventbooking.model.*;
import com.eventbooking.system.EventBookingSystem;
import com.eventbooking.annotation.RoleRequired;
import com.eventbooking.exception.InvalidBookingException;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static EventBookingSystem system = new EventBookingSystem();
    
    public static void main(String[] args) {
        // Initialize system with admin
        system.registerUser(new Admin("admin1", "System Admin"));
        
        // Load events from file
        system.loadEvents();
        
        boolean running = true;
        while (running) {
            System.out.println("\nEvent Ticket Booking System");
            System.out.println("1. Register Attendee");
            System.out.println("2. Add Event (Organizer only)");
            System.out.println("3. Book Ticket");
            System.out.println("4. Show Events");
            System.out.println("5. Admin Functions");
            System.out.println("6. Save & Exit");
            System.out.print("Choose an option: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            switch (choice) {
                case 1:
                    registerAttendee();
                    break;
                case 2:
                    addEvent();
                    break;
                case 3:
                    bookTicket();
                    break;
                case 4:
                    system.showEvents();
                    break;
                case 5:
                    adminFunctions();
                    break;
                case 6:
                    system.saveEvents();
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private static void registerAttendee() {
        System.out.print("Enter attendee ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter attendee name: ");
        String name = scanner.nextLine();
        
        Attendee attendee = new Attendee(id, name);
        system.registerUser(attendee);
        System.out.println("Attendee registered successfully!");
    }
    
    private static void addEvent() {
        System.out.print("Are you an organizer? (y/n): ");
        String response = scanner.nextLine();
        
        if (!response.equalsIgnoreCase("y")) {
            System.out.println("Only organizers can add events.");
            return;
        }
        
        System.out.print("Enter organizer ID: ");
        String id = scanner.nextLine();
        
        // Check if organizer exists (simplified for demo)
        System.out.print("Enter event title: ");
        String title = scanner.nextLine();
        System.out.print("Enter available tickets: ");
        int tickets = scanner.nextInt();
        scanner.nextLine(); // consume newline
        
        Event event = new Event(title, tickets);
        system.addEvent(event);
        System.out.println("Event added successfully!");
    }
    
    private static void bookTicket() {
        if (system.getAttendees().isEmpty()) {
            System.out.println("No attendees registered. Please register first.");
            return;
        }
        
        system.showEvents();
        if (system.getEvents().isEmpty()) {
            return;
        }
        
        System.out.print("Select an event (number): ");
        int eventChoice = scanner.nextInt();
        scanner.nextLine(); // consume newline
        
        if (eventChoice < 1 || eventChoice > system.getEvents().size()) {
            System.out.println("Invalid event selection.");
            return;
        }
        
        Event selectedEvent = system.getEvents().get(eventChoice - 1);
        
        System.out.print("Enter attendee ID: ");
        String attendeeId = scanner.nextLine();
        
        Attendee attendee = null;
        for (Attendee a : system.getAttendees()) {
            if (a.getId().equals(attendeeId)) {
                attendee = a;
                break;
            }
        }
        
        if (attendee == null) {
            System.out.println("Attendee not found.");
            return;
        }
        
        try {
            system.bookTicket(attendee, selectedEvent);
        } catch (InvalidBookingException e) {
            System.out.println("Booking failed: " + e.getMessage());
        }
    }
    
    private static void adminFunctions() {
        Admin admin = system.getAdmin();
        if (admin == null) {
            System.out.println("No admin configured.");
            return;
        }
        
        // Check for RoleRequired annotation using reflection
        Class<?> adminClass = admin.getClass();
        if (!adminClass.isAnnotationPresent(RoleRequired.class)) {
            System.out.println("Access denied. Admin role required.");
            return;
        }
        
        RoleRequired role = adminClass.getAnnotation(RoleRequired.class);
        if (!"Admin".equals(role.role())) {
            System.out.println("Access denied. Admin role required.");
            return;
        }
        
        System.out.println("\nAdmin Functions");
        System.out.println("1. Remove Event");
        System.out.println("2. View All Tickets");
        System.out.print("Choose an option: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine(); // consume newline
        
        switch (choice) {
            case 1:
                system.showEvents();
                if (!system.getEvents().isEmpty()) {
                    System.out.print("Enter event title to remove: ");
                    String title = scanner.nextLine();
                    admin.removeEvent(system.getEvents(), title);
                }
                break;
            case 2:
                viewAllTickets();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }
    
    private static void viewAllTickets() {
        List<Ticket> tickets = system.getTickets();
        if (tickets.isEmpty()) {
            System.out.println("No tickets booked yet.");
            return;
        }
        
        System.out.println("\nAll Booked Tickets:");
        for (int i = 0; i < tickets.size(); i++) {
            System.out.println((i+1) + ". " + tickets.get(i).ticketDetails());
            System.out.println("-------------------");
        }
    }
}