package ua.com.helsign.logstats.service;


import ua.com.helsign.logstats.model.Severity;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

public class LogReader extends RecursiveTask<List<String>> {

    List<String> logData;
    List<String> statistic;
    private String fileName;
    private int lengthLogData;
    private int threshold = 100;
    private int start;

    public LogReader(List<String> logData) {
        this.logData = logData;
    }

    @Override
    protected List<String> compute() {
        statistic = new LinkedList<>();
        lengthLogData = logData.size();
        if (lengthLogData < threshold)
            return filterData();

        int split = lengthLogData / 2;
        List<RecursiveTask> tasks = new LinkedList<>();
        LogReader task1 = new LogReader(logData.subList(start, split));
        tasks.add(task1);
        task1.fork();
        LogReader task2 = new LogReader(logData.subList(start + split, lengthLogData - split));
        tasks.add(task2);
        task2.fork();

        for (RecursiveTask<List<String>> joinTask : tasks) {
            statistic.addAll(joinTask.join());
        }
        return statistic;
        // invokeAll(new LogReader(logData.subList(0,split)), new LogReader(logData.subList(0,split)) );
    }

    public static List<String> getData(List<String> dataFromFile) {
        LogReader logReader = new LogReader(dataFromFile);
        ForkJoinPool pool = new ForkJoinPool();

        return pool.invoke(logReader);
    }

    public List<String> filterData() {
        statistic = new ArrayList<>();
        logData.stream().map(this::helper)
                .filter(this::compareSeverity)
                .collect(Collectors.groupingBy(o -> o, Collectors.counting()))
                .forEach((str, l) -> statistic.add(str + "->" + "" + l));
        return statistic;
    }

    private String helper(String record) {
        Scanner scanner = new Scanner(record);
        String dataLogRecord = scanner.findInLine("[\\d-\\s\\d:,]++");
        String severityLogRecord = scanner.findInLine("\\w++\\s");
        String classLogRecord = scanner.findInLine("[\\w.]++\\s");
        if (!(dataLogRecord == null || severityLogRecord == null || classLogRecord == null)) {
            return classLogRecord.trim() + "," + severityLogRecord.trim();
        } else return "";
    }

    private boolean compareSeverity(String value) {
        if (value == null)
            return false;
        for (Severity severity : Severity.values()) {
            if (value.endsWith(severity.name()))
                return true;
        }
        return false;
    }

}
