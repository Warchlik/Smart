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
    }

    @Override
    public void update(SmartDevice<?> device, String eventType, String description) {
        String line = String.join("\t",
                LocalDateTime.now().format(fmt),
                device.getId().toString(),
                device.getClass().getSimpleName(),
                eventType,
                description
        );

        try (BufferedWriter out = new BufferedWriter(new FileWriter(filename, true))) {

            out.write(line);
            out.newLine();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}