package be.ucll.craftsmanship.hotel.rooms.components;

public class AirConditioning {
    private int temperature;
    private boolean isOn;

    public AirConditioning(int initialTemp) {
        this.temperature = initialTemp;
        this.isOn = false;
    }

    public void turnOn() {
        this.isOn = true;
    }

    public void setTemperature(int temp) {
        this.temperature = temp;
    }
}