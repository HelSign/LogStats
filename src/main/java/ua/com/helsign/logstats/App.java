package ua.com.helsign.logstats;

import ua.com.helsign.logstats.service.LogReader;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {
    private static Logger LOGGER = Logger.getLogger(LogReader.class.getName());
    public static void main(String... args) throws IOException {
        LogReader logReader = new LogReader();
        logReader.setFileName("resources/sqlcmd.log");
        logReader.readData();
        logReader.countRecords();
        logReader.writeStatistic();
       /* ForkJoinPool commonPool = ForkJoinPool.commonPool();
        commonPool.execute(logReader.com);
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executorService.submit(logReader.readData());
        }
        executorService.shutdown();
        try {
            executorService.awaitTermination(10, TimeUnit.SECONDS);
        } catch (InterruptedException ie) {
            LOGGER.log(Level.SEVERE, "ERROR: Waiting on executor service shutdown!");
        }*/
    }

}
