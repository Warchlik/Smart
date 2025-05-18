package app;

import app.Devices.SmartDevice;
import app.Interfaces.DeviceObserver;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileLogger implements DeviceObserver {

    private final String filename;
    private final DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public FileLogger(String filename) {
        this.filename = filename;
        this.initLogFile();
    }

    private void initLogFile() {
        String header = String.join("\t", "TIMESTAMP", "DEVICE_ID", "DEVICE_TYPE", "ROOM_NAME", "EVENT_TYPE", "DESCRIPTION");
        try (BufferedWriter file = new BufferedWriter(new FileWriter(filename))) {
            file.write(header);
            file.newLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(SmartDevice<?> device, String eventType, String description) {
        String line = String.join("\t",
                LocalDateTime.now().format(fmt),
                device.getId().toString(),
                device.getClass().getSimpleName(),
                device.getRoomName().getType().name(),
                eventType,
                description
        );

        try (BufferedWriter file = new BufferedWriter(new FileWriter(filename, true))) {
            file.write(line);
            file.newLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}