package app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.*;

public class ReportService {
    private static final String LOGFILE = "smart_logs.tsv";

    private record LogEntry(LocalDateTime timestamp, String deviceId, String deviceType, String roomName, String eventType, String description) {}

    private static List<LogEntry> loadEntries() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        try (BufferedReader buffer = new BufferedReader(new FileReader(LOGFILE))) {
            return buffer.lines()
                    .skip(1)
                    .map(line -> {
                        String[] cols = line.split("\t", 6);
                        return new LogEntry(
                                LocalDateTime.parse(cols[0], fmt),
                                cols[1],
                                cols[2],
                                cols[3],
                                cols[4],
                                cols[5]
                        );
                    })
                    .collect(Collectors.toList());
        } catch (Exception exception) {
            System.out.println("Something is wrong, try again.");
            return Collections.emptyList();
        }
    }

    public static void printStatusChangeLogs() {
        System.out.println("\n--- STATUS CHANGE LOGS ---");
        loadEntries().stream()
                .filter(log -> "CHANGED_STATUS".equals(log.eventType()))
                .forEach(log -> System.out.printf("%s\t%s -> %s%n",
                        log.timestamp(), log.deviceId(), log.description()));
    }

    public static void printRuleTriggeredLogs() {
        System.out.println("\n--- RULE TRIGGERED LOGS ---");
        loadEntries().stream()
                .filter(log -> "RULE_TRIGGERED".equals(log.eventType()))
                .forEach(log -> System.out.printf("%s\t%s -> %s%n",
                        log.timestamp(), log.deviceId(), log.description()));
    }

    public static void printCurrentlyOnDevices() {
        System.out.println("\n--- DEVICES CURRENTLY ON ---");
        Map<String, LogEntry> lastStatus = loadEntries().stream()
                .filter(log -> "CHANGED_STATUS".equals(log.eventType()))
                .collect(Collectors.toMap(
                        LogEntry::deviceId,
                        log -> log,
                        (oldKey, newKey) -> newKey
                ));
        lastStatus.values().stream()
                .filter(log -> log.description().contains("to ON"))
                .forEach(log -> System.out.printf("%s\t%s%n", log.deviceId(), log.deviceType()));
    }

    public static void printCurrentlyOffDevices() {
        System.out.println("\n--- DEVICES CURRENTLY OFF ---");
        Map<String, LogEntry> lastStatus = loadEntries().stream()
                .filter(log -> "CHANGED_STATUS".equals(log.eventType()))
                .collect(Collectors.toMap(
                        LogEntry::deviceId,
                        log -> log,
                        (oldKey, newKey) -> newKey
                ));
        lastStatus.values().stream()
                .filter(log -> log.description().contains("to OFF"))
                .forEach(log -> System.out.printf("%s\t%s%n", log.deviceId(), log.deviceType()));
    }
}
