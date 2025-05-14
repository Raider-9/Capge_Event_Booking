package com.eventbooking.model;

import com.eventbooking.annotation.RoleRequired;
import java.util.List;
import java.util.Iterator;

@RoleRequired(role = "Admin")
public class Admin extends User {
    public Admin(String id, String name) {
        super(id, name);
    }
    
    @Override
    public void showProfile() {
        System.out.println("Admin ID: " + id);
        System.out.println("Name: " + name);
    }
    
    public void removeEvent(List<Event> events, String title) {
        Iterator<Event> iterator = events.iterator();
        while (iterator.hasNext()) {
            Event event = iterator.next();
            if (event.getTitle().equals(title)) {
                iterator.remove();
                System.out.println("Event '" + title + "' removed successfully.");
                return;
            }
        }
        System.out.println("Event '" + title + "' not found.");
    }
}