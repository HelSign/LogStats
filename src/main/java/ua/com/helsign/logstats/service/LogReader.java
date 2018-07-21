package ua.com.helsign.logstats.service;


import ua.com.helsign.logstats.model.Severity;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class LogReader {
    private static Logger LOGGER;
    // todo private ConcurrentHashMap<String, LogRecord> logData;
    private List<String> logData;
    private Map<String, Long> statistic;

    public LogReader() {
        logData = new ArrayList<>();
        statistic = new HashMap<>();
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
    }

    private void addLogger() {
        LOGGER = Logger.getLogger(LogReader.class.getName());
        ConsoleHandler consoleHandler = new ConsoleHandler();
        LOGGER.addHandler(consoleHandler);
    }

    void filterData(Scanner scanner) {
        String dataLogRecord = scanner.findInLine("[\\d-\\s\\d:,]++");
        String severityLogRecord = scanner.findInLine("\\w++\\s");
        String classLogRecord = scanner.findInLine("[\\w.]++\\s");

        if (dataLogRecord != null && severityLogRecord != null && classLogRecord != null) {
            severityLogRecord = severityLogRecord.trim();
            for (Severity severity : Severity.values()) {
                if (severity.name().equals(severityLogRecord)) {
                    logData.add(classLogRecord.trim() + "/" + severityLogRecord);
                }
            }
        }
    }

    void countRecords() {
        logData.forEach(System.out::println);
        Map<String, Long> counted = logData.stream().collect(Collectors.groupingBy(o -> o, Collectors.counting()));
        counted.forEach((record, count) -> statistic.put(record, count));
        statistic.forEach((record, count) -> System.out.println(record + "=" + count));
    }

    public List<String> getLogData() {
        return logData;
    }

    public Map<String, Long> getStatistic() {
        return statistic;
    }
}
