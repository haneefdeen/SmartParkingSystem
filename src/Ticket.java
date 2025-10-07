// Ticket.java
import java.time.LocalDateTime;

public class Ticket {
    private static int counter = 1000;

    private String ticketId;
    private Vehicle vehicle;
    private ParkingSlot slot;
    private LocalDateTime entryTime;

    public Ticket(Vehicle vehicle, ParkingSlot slot, LocalDateTime entryTime) {
        this.vehicle = vehicle;
        this.slot = slot;
        this.entryTime = entryTime;
        this.ticketId = "T" + counter++;
    }

    public String getTicketId() {
        return ticketId;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public ParkingSlot getSlot() {
        return slot;
    }

    public LocalDateTime getEntryTime() {
        return entryTime;
    }

    @Override
    public String toString() {
        return ticketId + " | " + vehicle + " | " + slot + " | entry: " + entryTime;
    }
}
