package ua.com.helsign.logstats.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

class StatisticTest {
    private String bigFile = "resources/sqlcmd.log";
    private String smallFile = "resources/test.log";

    @BeforeEach
    void setUp() throws IOException {
        Files.deleteIfExists(Paths.get(smallFile));
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Paths.get(smallFile));
    }

    @Test
    void shouldWriteFile() throws IOException {
        Path path = Paths.get(smallFile);
        Statistic.writeStatistic(getExpectedList(), smallFile);
        assertTrue(Files.exists(path));
    }

    private List<String> getExpectedList() {
        List<String> expected = new ArrayList<String>();
        expected.add("org.sprin.conte.suppo.AbstractApplicationContext,DEBUG");
        expected.add("org.sprin.ui.conte.suppo.UiApplicationContextUtils,DEBUG");
        expected.add("org.sprin.beans.facto.suppo.DefaultListableBeanFactory,DEBUG");
        expected.add("org.sprin.beans.facto.suppo.AbstractBeanFactory,DEBUG");
        expected.add("org.sprin.beans.facto.suppo.AbstractBeanFactory,DEBUG");
        expected.add("ua.com.juja.cmd.model.JDBCManager,DEBUG");
        expected.add("ua.com.juja.cmd.model.JDBCManager,DEBUG");
        expected.add("ua.com.juja.cmd.model.JDBCManager,DEBUG");
        expected.add("ua.com.juja.cmd.model.JDBCManager,WARN");
        expected.add("ua.com.juja.cmd.model.JDBCManager,DEBUG");
        return expected;
    }
}