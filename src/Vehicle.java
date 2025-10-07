// Vehicle.java
public class Vehicle {
    public enum Type { BIKE, CAR, BUS }

    private String licensePlate;
    private Type type;
    private boolean hasVipPass;

    public Vehicle(String licensePlate, Type type, boolean hasVipPass) {
        this.licensePlate = licensePlate.toUpperCase();
        this.type = type;
        this.hasVipPass = hasVipPass;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public Type getType() {
        return type;
    }

    public boolean hasVipPass() {
        return hasVipPass;
    }

    @Override
    public String toString() {
        return licensePlate + " (" + type + (hasVipPass ? ", VIP" : "") + ")";
    }
}
