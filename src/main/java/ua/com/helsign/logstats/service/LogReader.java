package ua.com.helsign.logstats.service;

import ua.com.helsign.logstats.model.LogFile;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogReader {
    private static Logger LOGGER;
    private LogFile logFile;

    void readData(String fileName) throws IOException {
        addLogger();
        ConcurrentHashMap<LogFile, String> logData = new ConcurrentHashMap<>();
        Scanner scanner = null;
        try {
            Path path = FileSystems.getDefault().getPath(fileName);
            LOGGER.log(Level.INFO, "path=" + path.toAbsolutePath());

            scanner = new Scanner(Files.newBufferedReader(path.toAbsolutePath())).useDelimiter("\r");

            while (scanner.hasNextLine()) {
                System.out.println("|||" + scanner.findInLine("[\\d-\\s\\d:,]++"));
                System.out.println("|||" + scanner.findInLine("\\w++\\s"));
                System.out.println("|||" + scanner.findInLine("\\w++\\s"));
                System.out.println("!!!" + scanner.nextLine());
            }

        } finally {
            if (scanner != null)
                scanner.close();
        }
    }

    private void addLogger() {
        LOGGER = Logger.getLogger(LogReader.class.getName());
        ConsoleHandler consoleHandler = new ConsoleHandler();
        LOGGER.addHandler(consoleHandler);
    }

    void filterData() {

    }

    void writeData() {

    }
}
