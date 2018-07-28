package ua.com.helsign.logstats;

import ua.com.helsign.logstats.model.LogFile;
import ua.com.helsign.logstats.model.Statistic;
import ua.com.helsign.logstats.service.LogReader;

import java.io.IOException;
import java.util.List;

public class App {

    public static void main(String... args) throws IOException {
        long startTime = System.currentTimeMillis();

        List<String> dataFromFile;
        LogFile logFile = LogFile.readData("resources/sqlcmd.log");
        dataFromFile = logFile.getFile();
        Statistic statistic = new Statistic();
        statistic.writeStatistic( LogReader.getData(dataFromFile), "resources/stats.csv");

        long endTime = System.currentTimeMillis();
        System.out.println("Task took " + (endTime - startTime) + " milliseconds.");
    }

}
