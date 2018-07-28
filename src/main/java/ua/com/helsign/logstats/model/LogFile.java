package ua.com.helsign.logstats.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class LogFile {
    private final List<String> file;

    LogFile(List<String> file) {
        this.file = file;
    }

    public List<String> getFile() {
        return file;
    }

    public static LogFile readData(String fileName) throws IOException {
        Path path = FileSystems.getDefault().getPath(fileName);
        BufferedReader reader = Files.newBufferedReader(path.toAbsolutePath());
        List<String> file = reader.lines().collect(Collectors.toList());
        return new LogFile(file);
    }
}
