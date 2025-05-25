import smartDevices.SmartDevice;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger {
    private static final String LOG_DIR_NAME = "logs";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter LOG_DATETIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    static {
        File dir = new File(LOG_DIR_NAME);
        if(!dir.exists()) {
            dir.mkdirs();
        }
    }

    public static void log(String message, SmartDevice device, String roomName, String eventType) {
        String currentDate = LocalDate.now().format(DATE_FORMAT);
        String logStamp = LocalDateTime.now().format(LOG_DATETIME_FORMAT);
        String logMessage = "[" + logStamp + "] [" + device.getName() + "] [" + device.getType() + "] [" + roomName + "] [" + eventType + "] " + message;

        String logFindPath = LOG_DIR_NAME + File.separator + currentDate + ".log";

        try(BufferedWriter writer = new BufferedWriter(new FileWriter(logFindPath, true))) {
            writer.write(logMessage);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Failed to write log: " + e.getMessage());
        }
    }
}


