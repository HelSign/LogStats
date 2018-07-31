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

    private List<String> logData;
    private List<String> statistic;
    private int threshold = 50;
    private int start;

    private LogReader(List<String> logData) {
        this.logData = logData;
    }

    @Override
    protected List<String> compute() {
        statistic = new ArrayList<>();
        int lengthLogData = logData.size();
        if (lengthLogData < threshold) {
            return filterData();
        }
        int split = lengthLogData / 2;
        List<RecursiveTask> tasks = new LinkedList<>();
        LogReader task1 = new LogReader(logData.subList(start, split));
        tasks.add(task1);
        task1.fork();
        LogReader task2 = new LogReader(logData.subList(split + 1, lengthLogData));
        tasks.add(task2);
        task2.fork();

        for (RecursiveTask<List<String>> joinTask : tasks) {
            statistic.addAll(joinTask.join());
        }
        return statistic;
    }

    public static List<String> getData(List<String> dataFromFile) {
        List<String> finalStatistic = new ArrayList<>();
        LogReader logReader = new LogReader(dataFromFile);
        ForkJoinPool pool = new ForkJoinPool();
        List<String> statistic = pool.invoke(logReader);
        System.out.println("number of elements =" + statistic.size());
        statistic.stream()
                .collect(Collectors.groupingBy(o -> o, Collectors.counting()))
                .forEach((record, number) -> finalStatistic.add(record + "," + number));
        System.out.println("final =" + finalStatistic.size());
        return finalStatistic;
    }

    private List<String> filterData() {
        List<String> filteredData = logData.stream()
                .map(this::splitLine)
                .filter(this::checkSeverity)
                .collect(Collectors.toList());
        return filteredData;
    }

    private String splitLine(String line) {
        Scanner scanner = new Scanner(line);
        String dataLogRecord = scanner.findInLine("[\\d-\\s\\d:,]++");
        String severityLogRecord = scanner.findInLine("\\w++\\s");
        String classLogRecord = scanner.findInLine("[\\w.]++\\s");
        if (!(dataLogRecord == null || severityLogRecord == null || classLogRecord == null)) {
            return classLogRecord.trim() + "," + severityLogRecord.trim();
        } else return "";
    }

    private boolean checkSeverity(String value) {
        if (value == null)
            return false;
        for (Severity severity : Severity.values()) {
            if (value.endsWith(severity.name()))
                return true;
        }
        return false;
    }

}
