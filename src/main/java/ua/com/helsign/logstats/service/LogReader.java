package ua.com.helsign.logstats.service;

import ua.com.helsign.logstats.model.LogFile;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogReader {
    private static Logger LOGGER = Logger.getLogger(LogReader.class.getName());
    private static ConsoleHandler consoleHandler = new ConsoleHandler();


    void readData(String fileName) throws  IOException {
        LOGGER.addHandler(consoleHandler);
        ConcurrentHashMap<LogFile, String> logData = new ConcurrentHashMap<>();
        Scanner scanner = null;
        try {

            Path path = FileSystems.getDefault().getPath(fileName);
            LOGGER.log(Level.SEVERE, "path=" + path.toAbsolutePath());

            scanner = new Scanner(Files.newBufferedReader( path.toAbsolutePath()));
            while (scanner.hasNext()) {
                System.out.println(scanner.next());
            }

        } finally {
            if (scanner != null)
                scanner.close();
        }
    }

    void filterData() {

    }

    void writeData() {

    }
}
