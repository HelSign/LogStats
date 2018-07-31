package ua.com.helsign.logstats;

import ua.com.helsign.logstats.model.LogFile;
import ua.com.helsign.logstats.model.Statistic;
import ua.com.helsign.logstats.service.LogReader;

import java.io.IOException;
import java.util.List;

public class App {

    public static void main(String... args) throws IOException {
        long startTime = System.currentTimeMillis();


        LogFile logFile = LogFile.readData("resources/sqlcmd.log");
        List<String>  dataFromFile = logFile.getFileLines();
        List<String> filteredData = LogReader.getData(dataFromFile);
        Statistic.writeStatistic( filteredData, "resources/stats.csv");

        long endTime = System.currentTimeMillis();
        System.out.println("Task took " + (endTime - startTime) + " milliseconds.");
    }

}
