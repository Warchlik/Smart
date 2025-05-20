package app.Devices;

import app.Interfaces.Switchable;
import app.SmartEnums.DefaultDeviceEnum;

import java.awt.Color;

public class Lightbulb extends SmartDevice<DefaultDeviceEnum> implements Switchable {

    private double hue;
    private double saturation;
    private double value;

    public static final double DEFAULT_HUE = 30.0;
    public static final double DEFAULT_SATURATION = 0.01;
    public static final double DEFAULT_VALUE = 0.98;

    public Lightbulb(String name) {
        super(name , DefaultDeviceEnum.OFF);
        this.hue = DEFAULT_HUE;
        this.saturation = DEFAULT_SATURATION;
        this.value = DEFAULT_VALUE;
    }

    @Override
    public void turnOn() {
        if (!isOn()){
            this.hue = DEFAULT_HUE;
            this.saturation = DEFAULT_SATURATION;
            this.value = DEFAULT_VALUE;
            this.setStatus(DefaultDeviceEnum.ON);
            this.simulate();
            System.out.println("\nYou turn ON Lightbulb.");
        }else {
            System.out.println("\nLightbulb is in use now, you can not ON it second time.");
        }

    }

    @Override
    public void turnOff() {
        if (isOn()){
            this.setStatus(DefaultDeviceEnum.OFF);
            synchronized (this){
                this.notifyAll();
            }
            System.out.println("\nYou turn OFF Lightbulb.");
        }else {
            System.out.println("\nYou can not turn of Lightbulb now.");
        }
    }

    @Override
    public boolean isOn() {
        return this.getStatus() != DefaultDeviceEnum.OFF;
    }

    public void setHue(double hue) {
        if (hue < 0 || hue > 360) {
            throw new IllegalArgumentException("\nHue must be in [0, 360]");
        }
        this.hue = hue;
    }

    public void setSaturation(double saturation) {
        if (saturation < 0.0 || saturation > 1.0) {
            throw new IllegalArgumentException("\nSaturation must be in [0, 1]");
        }
        this.saturation = saturation;
    }

    public void setValue(double value) {
        if (value < 0.0 || value > 1.0) {
            throw new IllegalArgumentException("\nValue must be in [0, 1]");
        }
        this.value = value;
    }

    public double getHue() {
        return hue;
    }

    public double getSaturation() {
        return saturation;
    }

    public double getValue() {
        return value;
    }

    public Color getRGBColor() {
        double H = hue;
        double S = saturation;
        double V = value;

        double C = V * S;
        double X = C * (1 - Math.abs((H / 60.0) % 2 - 1));
        double m = V - C;

        double r1, g1, b1;
        if (H >= 0 && H < 60) {
            r1 = C; g1 = X; b1 = 0;
        } else if (H < 120) {
            r1 = X; g1 = C; b1 = 0;
        } else if (H < 180) {
            r1 = 0; g1 = C; b1 = X;
        } else if (H < 240) {
            r1 = 0; g1 = X; b1 = C;
        } else if (H < 300) {
            r1 = X; g1 = 0; b1 = C;
        } else {
            r1 = C; g1 = 0; b1 = X;
        }

        int r = (int) Math.round((r1 + m) * 255);
        int g = (int) Math.round((g1 + m) * 255);
        int b = (int) Math.round((b1 + m) * 255);
        return new Color(r, g, b);
    }

//    @Override
//    public void simulate() {
//
//    }

    @Override
    public void simulate() {
        Thread thread = new Thread(() -> {
            synchronized (this) {
                try {
                    while (isOn()) {
                        this.getRGBColor();
                        notifyObservers("CHANGED_COLOR", "Color has been changed.");
                        this.wait(5000);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });

        thread.setDaemon(true);
        thread.start();
    }
}