// Main.java
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ParkingLot lot = new ParkingLot();

        System.out.println("Commands:");
        System.out.println("  park <LICENSE> <TYPE> [vip_pass]");
        System.out.println("  leave <TICKET_ID>");
        System.out.println("  status");
        System.out.println("  register_vip_pass <PASS_ID>");
        System.out.println("  exit\n");

        while (true) {
            System.out.print("> ");
            String input = sc.nextLine().trim();
            if (input.isEmpty()) continue;

            String[] parts = input.split("\\s+");
            String cmd = parts[0].toLowerCase();

            switch (cmd) {
                case "park":
                    if (parts.length < 3) {
                        System.out.println("Usage: park <LICENSE> <TYPE> [vip_pass]\n");
                        break;
                    }
                    String license = parts[1];
                    String typeStr = parts[2].toLowerCase();
                    boolean vip = parts.length >= 4 && lot.isValidVip(parts[3]);
                    Vehicle.Type type;
                    switch (typeStr) {
                        case "bike": type = Vehicle.Type.BIKE; break;
                        case "car": type = Vehicle.Type.CAR; break;
                        case "bus": type = Vehicle.Type.BUS; break;
                        default:
                            System.out.println("Invalid vehicle type.\n");
                            continue;
                    }
                    lot.parkVehicle(license, type, vip);
                    break;

                case "leave":
                    if (parts.length < 2) {
                        System.out.println("Usage: leave <TICKET_ID>\n");
                        break;
                    }
                    lot.leave(parts[1]);
                    break;

                case "status":
                    lot.showStatus();
                    break;

                case "register_vip_pass":
                    if (parts.length < 2) {
                        System.out.println("Usage: register_vip_pass <PASS_ID>\n");
                        break;
                    }
                    lot.registerVipPass(parts[1]);
                    break;

                case "exit":
                    System.out.println("Exiting Smart Parking System. Bye!");
                    sc.close();
                    return;

                default:
                    System.out.println("Unknown command.\n");
            }
        }
    }
}
