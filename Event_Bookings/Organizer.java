package Event_Bookings;
public class Organizer extends User {
    private boolean active = true;

    public Organizer(String id, String name) {
        super(id, name);
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public void showProfile() {
        System.out.println("Organizer ID: " + id + ", Name: " + name + ", Active: " + active);
    }
}


