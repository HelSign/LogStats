package ua.com.helsign.logstats.service;


import ua.com.helsign.logstats.model.Severity;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    private String fileName;

    public LogReader() {
        logData = new ArrayList<>();
        statistic = new HashMap<>();
        addLogger();
    }

    public void readData() throws IOException {
        Path path = FileSystems.getDefault().getPath(fileName);
        LOGGER.log(Level.INFO, "path=" + path.toAbsolutePath());
        try (Scanner scanner = new Scanner(Files.newBufferedReader(path.toAbsolutePath())).useDelimiter("\r")) {
            while (scanner.hasNextLine()) {
                filterData(scanner);
                scanner.nextLine();
            }
        }
    }

    private void addLogger() {
        LOGGER = Logger.getLogger(LogReader.class.getName());
        ConsoleHandler consoleHandler = new ConsoleHandler();
        LOGGER.addHandler(consoleHandler);
    }

    private void filterData(Scanner scanner) {
        String dataLogRecord = scanner.findInLine("[\\d-\\s\\d:,]++");
        String severityLogRecord = scanner.findInLine("\\w++\\s");
        String classLogRecord = scanner.findInLine("[\\w.]++\\s");

        if (dataLogRecord == null || severityLogRecord == null || classLogRecord == null)
            return;
        severityLogRecord = severityLogRecord.trim();
        classLogRecord = classLogRecord.trim();
        for (Severity severity : Severity.values()) {
            if (severity.name().equals(severityLogRecord)) {
                logData.add(String.format("%s,%s", classLogRecord, severityLogRecord));
            }
        }
    }

    public void countRecords() {
        logData.forEach(System.out::println);
        Map<String, Long> counted = logData.stream().collect(Collectors.groupingBy(o -> o, Collectors.counting()));
        counted.forEach((record, count) -> statistic.put(record, count));
        statistic.forEach((record, count) -> System.out.println(record + "=" + count));
    }

    public void writeStatistic() throws IOException {
        Path path = Paths.get("resources/stats.csv");
        Files.deleteIfExists(path);
        Path resultFile = Files.createFile(path);
        Charset charset = Charset.forName("UTF-8");
        try (BufferedWriter writer = Files.newBufferedWriter(resultFile, charset)) {
            statistic.forEach((record, counter) -> {
                try {
                    writer.write(String.format("%s,%d", record, counter));
                    writer.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    public List<String> getLogData() {
        return logData;
    }

    public Map<String, Long> getStatistic() {
        return statistic;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
