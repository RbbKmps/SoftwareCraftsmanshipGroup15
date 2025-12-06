package be.ucll.craftsmanship.hotel.rooms.components;

import jakarta.persistence.Embeddable;

@Embeddable
public class AirConditioning {
    private int temperature;
    private boolean isOn;

    protected AirConditioning() {
        this.temperature = 0;
        this.isOn = false;
    }

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

    public int getTemperature() {
        return temperature;
    }

    public boolean isOn() {
        return isOn;
    }
}