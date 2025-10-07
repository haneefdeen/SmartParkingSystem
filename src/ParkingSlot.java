// ParkingSlot.java
public class ParkingSlot implements Comparable<ParkingSlot> {
    public enum Size { COMPACT, REGULAR, LARGE }

    private int slotId;
    private Size size;
    private boolean isVip;
    private boolean occupied;

    public ParkingSlot(int slotId, Size size, boolean isVip) {
        this.slotId = slotId;
        this.size = size;
        this.isVip = isVip;
        this.occupied = false;
    }

    public int getSlotId() {
        return slotId;
    }

    public Size getSize() {
        return size;
    }

    public boolean isVip() {
        return isVip;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public void occupy() {
        occupied = true;
    }

    public void free() {
        occupied = false;
    }

    @Override
    public int compareTo(ParkingSlot other) {
        return Integer.compare(this.slotId, other.slotId);
    }

    @Override
    public String toString() {
        String vipText = isVip ? ", VIP" : "";
        String status = occupied ? "(OCCUPIED)" : "(AVAILABLE)";
        return "Slot#" + slotId + " [" + size + vipText + "] " + status;
    }
}
