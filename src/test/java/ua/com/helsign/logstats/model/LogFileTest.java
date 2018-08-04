package ua.com.helsign.logstats.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class LogFileTest {
    private String bigFile = "resources/sqlcmd.log";
    private String smallFile = "resources/test.log";

    @BeforeEach
    void setUp() throws IOException {
        Files.deleteIfExists(Paths.get(smallFile));
        createTestFile(smallFile);
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Paths.get(smallFile));
    }

    @Test
    void shouldNotFindFile() {
        assertThrows(NoSuchFileException.class, () -> LogFile.readData("/" + bigFile));
    }

    @Test
    void shouldFindFile() throws IOException {
        LogFile.readData(bigFile); //todo any check?
    }

    @Test
    void shouldReadSmallFile() throws IOException {
        LogFile.readData(smallFile); //todo any check?
    }

    @Test
    void shouldReadData() throws IOException {
        LogFile logFile = LogFile.readData(smallFile);

        List<String> actual = logFile.getFileLines();
        List<String> expected = getExpectedList();
        assertEquals(expected, actual);
    }

    private void createTestFile(String fileName) throws IOException {
        Charset charset = Charset.forName("UTF-8");
        Path file = Files.createFile(Paths.get(fileName));
        String s = getTestString();
        try (BufferedWriter writer = Files.newBufferedWriter(file, charset)) {
            writer.write(s, 0, s.length());
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }

    private String getTestString() {
        return "2018-07-12 12:31:19,788 DEBUG org.sprin.conte.suppo.AbstractApplicationContext Unable to locate ApplicationEventMulticaster with name 'applicationEventMulticaster': using default [org.springframework.context.event.SimpleApplicationEventMulticaster@7ce43a40]\n" +
                "2018-07-12 15:11:32,462 DEBUG ua.com.juja.cmd.model.JDBCManager SELECT table_name FROM information_schema.tables WHERE table_schema='public' AND table_type='BASE TABLE'\n" +
                "2018-07-12 15:11:35,644 WARN ua.com.juja.cmd.model.JDBCManager !!!!DROP TABLE public.dsdsdsd\n" +
                "2018-07-12 15:11:35,691 DEBUG ua.com.juja.cmd.model.JDBCManager SELECT table_name FROM information_schema.tables WHERE table_schema='public' AND table_type='BASE TABLE'\n";
    }

    private List<String> getExpectedList() {
        List<String> expected = new ArrayList<String>();
        expected.add("2018-07-12 12:31:19,788 DEBUG org.sprin.conte.suppo.AbstractApplicationContext Unable to " +
                "locate ApplicationEventMulticaster with name 'applicationEventMulticaster': using default [org" +
                ".springframework.context.event.SimpleApplicationEventMulticaster@7ce43a40]");
        expected.add("2018-07-12 15:11:32,462 DEBUG ua.com.juja.cmd.model.JDBCManager SELECT table_name " +
                "FROM information_schema.tables WHERE table_schema='public' AND table_type='BASE TABLE'");
        expected.add("2018-07-12 15:11:35,644 WARN ua.com.juja.cmd.model.JDBCManager !!!!DROP TABLE public.dsdsdsd");
        expected.add("2018-07-12 15:11:35,691 DEBUG ua.com.juja.cmd.model.JDBCManager SELECT table_name " +
                "FROM information_schema.tables WHERE table_schema='public' AND table_type='BASE TABLE'");

        return expected;
    }
}