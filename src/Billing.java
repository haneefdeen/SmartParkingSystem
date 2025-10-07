// Billing.java
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Billing {
    private static final int RATE_COMPACT = 20;
    private static final int RATE_REGULAR = 40;
    private static final int RATE_LARGE = 60;

    // Formatter for readable timestamps
    private static final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MMM-yyyy hh:mm a");

    public static long computeCharge(ParkingSlot.Size size, LocalDateTime in, LocalDateTime out) {
        long minutes = Duration.between(in, out).toMinutes();
        long hours = (minutes + 59) / 60; // round up
        int rate = rateFor(size);
        return hours * rate;
    }

    private static int rateFor(ParkingSlot.Size size) {
        switch (size) {
            case COMPACT: return RATE_COMPACT;
            case REGULAR: return RATE_REGULAR;
            case LARGE: return RATE_LARGE;
            default: return RATE_REGULAR;
        }
    }

    // Improved readable summary
    public static String summary(ParkingSlot.Size size, LocalDateTime in, LocalDateTime out) {
        long minutes = Duration.between(in, out).toMinutes();
        long hours = (minutes + 59) / 60;
        long charge = computeCharge(size, in, out);

        return String.format(
            "Entry: %s\nExit:  %s\nDuration: %d minutes (~%d hour%s billed)\nAmount: Rs.%d",
            fmt.format(in),
            fmt.format(out),
            minutes,
            hours,
            (hours > 1 ? "s" : ""),
            charge
        );
    }

    public static String formatTime(LocalDateTime time) {
        return fmt.format(time);
    }
}
