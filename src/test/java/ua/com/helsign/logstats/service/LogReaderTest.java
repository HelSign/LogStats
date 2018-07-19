package ua.com.helsign.logstats.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileAttribute;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeFalse;

class LogReaderTest {
    private String testFile = "test.log";

    @BeforeEach
    void setUp() throws IOException {
        Path path = Paths.get(testFile);
        Files.deleteIfExists(path);
        Files.createFile(path);
    }

    @AfterEach
    void tearDown() throws IOException {
        Path path = Paths.get(testFile);
        Files.deleteIfExists(path);
    }

    @Test
    void shouldNotFindFile() {
        LogReader logReader = new LogReader();
        assertThrows(NoSuchFileException.class, () -> logReader.readData("/test.log"));
    }

    @Test
    void shouldFindFile() throws IOException {
        LogReader logReader = new LogReader();
        logReader.readData("test.log");
    }
}