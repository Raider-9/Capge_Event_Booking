public class Attendee extends User {
    public Attendee(String id, String name) {
        super(id, name);
    }

    @Override
    public void showProfile() {
        System.out.println("Attendee ID: " + id + ", Name: " + name);
    }
}
