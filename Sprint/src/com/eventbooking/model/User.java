package com.eventbooking.model;

public abstract class User {
    protected String id;
    protected String name;
    
    public User(String id, String name) {
        this.id = id;
        this.name = name;
    }
    
    public abstract void showProfile();
    
    public String getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
}
