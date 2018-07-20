package ua.com.helsign.logstats.service;

import ua.com.helsign.logstats.model.LogFile;
import ua.com.helsign.logstats.model.LogRecord;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LogReader {
    private static Logger LOGGER;
    private LogFile logFile;
    // todo private ConcurrentHashMap<String, LogRecord> logData;
    private List<LogRecord> logData;

    public LogReader() {
        logData = new ArrayList<>();
        addLogger();
    }

    void readData(String fileName) throws IOException {
        Path path = FileSystems.getDefault().getPath(fileName);
        LOGGER.log(Level.INFO, "path=" + path.toAbsolutePath());
        Scanner scanner = null;
        try {
            scanner = new Scanner(Files.newBufferedReader(path.toAbsolutePath())).useDelimiter("\r");
            while (scanner.hasNextLine()) {
                filterData(scanner);
                scanner.nextLine();
            }
        } finally {
            if (scanner != null)
                scanner.close();
        }
        writeData();
    }

    private void addLogger() {
        LOGGER = Logger.getLogger(LogReader.class.getName());
        ConsoleHandler consoleHandler = new ConsoleHandler();
        LOGGER.addHandler(consoleHandler);
    }

    void filterData(Scanner scanner) throws IOException {
        String dataLogRecord = scanner.findInLine("[\\d-\\s\\d:,]++");
        String severityLogRecord = scanner.findInLine("\\w++\\s");
        String classLogRecord = scanner.findInLine("[\\w.]++\\s");

        if (dataLogRecord != null && severityLogRecord != null) {
            LogRecord logRecord = new LogRecord();
            logRecord.setData(dataLogRecord);
            logRecord.setSeverity(severityLogRecord);
            logRecord.setClassName(classLogRecord);
            logData.add(logRecord);
        }

    }

    void writeData() {
        logData.forEach((record) -> System.out.println(record.getClassName() + "->" + record.getSeverity()));
    }
}
