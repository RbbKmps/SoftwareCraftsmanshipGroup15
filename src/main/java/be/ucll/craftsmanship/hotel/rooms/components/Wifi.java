package be.ucll.craftsmanship.hotel.rooms.components;

public class Wifi {
    private final String serviceProvider;
    private final int connectionSpeedMbps;

    public Wifi(String serviceProvider, int connectionSpeedMbps) {
        this.serviceProvider = serviceProvider;
        this.connectionSpeedMbps = connectionSpeedMbps;
    }

    public String getStatus() {
        return String.format("Connected via %s at %d Mbps.", serviceProvider, connectionSpeedMbps);
    }

    public int getConnectionSpeedMbps() {
        return connectionSpeedMbps;
    }
}
