package be.ucll.craftsmanship.hotel.rooms.components;

import jakarta.persistence.Embeddable;

@Embeddable
public class Wifi {
    private String serviceProvider;
    private int connectionSpeedMbps;

    protected Wifi() {
        this.serviceProvider = "";
        this.connectionSpeedMbps = 0;
    }

    public Wifi(String serviceProvider, int connectionSpeedMbps) {
        this.serviceProvider = serviceProvider;
        this.connectionSpeedMbps = connectionSpeedMbps;
    }

    public String getStatus() {
        return String.format("Connected via %s at %d Mbps.", serviceProvider, connectionSpeedMbps);
    }

    public String getServiceProvider() {
        return serviceProvider;
    }

    public int getConnectionSpeedMbps() {
        return connectionSpeedMbps;
    }
}
