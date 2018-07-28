package ua.com.helsign.logstats.model;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Statistic {


    public void writeStatistic(List<String> statistic, String fileName) throws IOException {
        Path path = Paths.get(fileName);
        Files.deleteIfExists(path);
        Path resultFile = Files.createFile(path);
        Charset charset = Charset.forName("UTF-8");
        try (BufferedWriter writer = Files.newBufferedWriter(resultFile, charset)) {
            statistic.forEach((record) -> {
                try {
                    writer.write(record);
                    writer.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
