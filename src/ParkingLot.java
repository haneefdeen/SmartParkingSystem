// ParkingLot.java
import java.time.LocalDateTime;
import java.util.*;

public class ParkingLot {
    private final PriorityQueue<ParkingSlot> compactSlots = new PriorityQueue<>();
    private final PriorityQueue<ParkingSlot> regularSlots = new PriorityQueue<>();
    private final PriorityQueue<ParkingSlot> largeSlots = new PriorityQueue<>();

    private final PriorityQueue<ParkingSlot> vipRegularSlots = new PriorityQueue<>();
    private final PriorityQueue<ParkingSlot> vipLargeSlots = new PriorityQueue<>();

    private final Map<String, Ticket> activeTickets = new HashMap<>();
    private final Set<String> vipPasses = new HashSet<>();

    public ParkingLot() {
        int id = 1;

        // Compact slots (8, no VIP)
        for (int i = 0; i < 8; i++)
            compactSlots.add(new ParkingSlot(id++, ParkingSlot.Size.COMPACT, false));

        // Regular slots (10, 3 VIP)
        for (int i = 0; i < 7; i++)
            regularSlots.add(new ParkingSlot(id++, ParkingSlot.Size.REGULAR, false));
        for (int i = 0; i < 3; i++)
            vipRegularSlots.add(new ParkingSlot(id++, ParkingSlot.Size.REGULAR, true));

        // Large slots (5, 1 VIP)
        for (int i = 0; i < 4; i++)
            largeSlots.add(new ParkingSlot(id++, ParkingSlot.Size.LARGE, false));
        vipLargeSlots.add(new ParkingSlot(id++, ParkingSlot.Size.LARGE, true));

        System.out.println("Initialized parking lot:");
        System.out.println("Compact: 8 (0 VIP)");
        System.out.println("Regular: 10 (3 VIP)");
        System.out.println("Large: 5 (1 VIP)");
        System.out.println("Total: 23 slots\n");
    }

    public void registerVipPass(String passId) {
        vipPasses.add(passId.toUpperCase());
        System.out.println("Registered VIP Pass: " + passId.toUpperCase());
    }

    public boolean isValidVip(String passId) {
        return vipPasses.contains(passId.toUpperCase());
    }

    public Ticket parkVehicle(String license, Vehicle.Type type, boolean hasVipPass) {
        ParkingSlot allocated = allocateSlot(type, hasVipPass);
        if (allocated == null) {
            System.out.println("No available slots for " + type + ".\n");
            return null;
        }
        allocated.occupy();
        Vehicle v = new Vehicle(license, type, hasVipPass);
        Ticket t = new Ticket(v, allocated, LocalDateTime.now());
        activeTickets.put(t.getTicketId(), t);

        System.out.println("Vehicle " + v + " parked in " + allocated + ".");
        System.out.println("Ticket ID: " + t.getTicketId());
        System.out.println("Entry Time: " + Billing.formatTime(t.getEntryTime()) + "\n");

        return t;
    }

    private ParkingSlot allocateSlot(Vehicle.Type type, boolean hasVipPass) {
        switch (type) {
            case BIKE:
                return pollIfAvailable(compactSlots);
            case CAR:
                if (hasVipPass && !vipRegularSlots.isEmpty())
                    return pollIfAvailable(vipRegularSlots);
                ParkingSlot reg = pollIfAvailable(regularSlots);
                if (reg != null) return reg;
                return pollIfAvailable(largeSlots);
            case BUS:
                if (hasVipPass && !vipLargeSlots.isEmpty())
                    return pollIfAvailable(vipLargeSlots);
                return pollIfAvailable(largeSlots);
            default:
                return null;
        }
    }

    private ParkingSlot pollIfAvailable(PriorityQueue<ParkingSlot> q) {
        while (!q.isEmpty()) {
            ParkingSlot s = q.poll();
            if (!s.isOccupied()) return s;
        }
        return null;
    }

    public void leave(String ticketId) {
        Ticket t = activeTickets.get(ticketId);
        if (t == null) {
            System.out.println("Invalid ticket ID.\n");
            return;
        }

        LocalDateTime out = LocalDateTime.now();
        long charge = Billing.computeCharge(t.getSlot().getSize(), t.getEntryTime(), out);
        ParkingSlot s = t.getSlot();
        s.free();

        // Return slot to queue
        if (s.isVip()) {
            if (s.getSize() == ParkingSlot.Size.REGULAR) vipRegularSlots.add(s);
            else vipLargeSlots.add(s);
        } else {
            switch (s.getSize()) {
                case COMPACT: compactSlots.add(s); break;
                case REGULAR: regularSlots.add(s); break;
                case LARGE: largeSlots.add(s); break;
            }
        }

        activeTickets.remove(ticketId);

        System.out.println("Vehicle " + t.getVehicle().getLicensePlate() + " exited from " + s + ".");
        System.out.println(Billing.summary(s.getSize(), t.getEntryTime(), out));
        System.out.println("Total payable: Rs." + charge + "\n");
    }

    public void showStatus() {
        System.out.println("=== Parking Lot Status ===");
        if (activeTickets.isEmpty()) System.out.println("No vehicles parked.");
        else {
            for (Ticket t : activeTickets.values())
                System.out.println("  " + t);
        }
        System.out.println("\nAvailable Slots:");
        System.out.println("Compact: " + compactSlots.size());
        System.out.println("Regular: " + regularSlots.size());
        System.out.println("VIP Regular: " + vipRegularSlots.size());
        System.out.println("Large: " + largeSlots.size());
        System.out.println("VIP Large: " + vipLargeSlots.size());
        System.out.println("==========================\n");
    }
}
