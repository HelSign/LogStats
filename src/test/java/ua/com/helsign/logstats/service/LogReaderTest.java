package ua.com.helsign.logstats.service;

class LogReaderTest {
    private String bigFile = "resources/sqlcmd.log";
    private String smallFile = "resources/test.log";
/*
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
        LogReader logReader = new LogReader();
        logReader.setFileName("/" + bigFile);
        assertThrows(NoSuchFileException.class, () -> logReader.readData());
    }

    @Test
    void shouldFindFile() throws IOException {
        LogReader logReader = new LogReader();
        logReader.setFileName(bigFile);
        logReader.readData();
    }

    @Test
    void shouldReadSmallFile() throws IOException {
        LogReader logReader = new LogReader();
        logReader.setFileName(smallFile);
        logReader.readData();
    }

    @Test
    void shouldReadData() throws IOException {
        LogReader logReader = new LogReader();
        logReader.setFileName(smallFile);
        logReader.readData();
        List<String> actual = logReader.getLogData();
        List<String> expected = getExpectedList();
        assertEquals(expected, actual);
    }

    @Test
    void shouldCountRecords() throws IOException {
        LogReader logReader = new LogReader();
        logReader.setFileName(smallFile);
        logReader.readData();
        logReader.countRecords();
        Map<String, Long> actual = logReader.getStatistic();
        Map<String, Long> expected = getExpectedMap();
        assertEquals(expected, actual);
    }

    @Test
    void shouldWriteFile() throws IOException {
        LogReader logReader = new LogReader();
        logReader.setFileName(smallFile);
        logReader.readData();
        logReader.countRecords();
        logReader.writeStatistic();
        Path path = Paths.get(smallFile);
        assertTrue(Files.exists(path));
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
                "2018-07-12 12:31:19,789 DEBUG org.sprin.ui.conte.suppo.UiApplicationContextUtils Unable to locate ThemeSource with name 'themeSource': using default [org.springframework.ui.context.support.DelegatingThemeSource@b9ea7f6]\n" +
                "2018-07-12 12:31:19,791 DEBUG org.sprin.beans.facto.suppo.DefaultListableBeanFactory Pre-instantiating singletons in org.springframework.beans.factory.support.DefaultListableBeanFactory@2f816072: defining beans [org.springframework.context.annotation.internalConfigurationAnnotationProcessor,org.springframework.context.annotation.internalAutowiredAnnotationProcessor,org.springframework.context.annotation.internalRequiredAnnotationProcessor,org.springframework.context.annotation.internalCommonAnnotationProcessor,org.springframework.context.annotation.internalPersistenceAnnotationProcessor,org.springframework.context.event.internalEventListenerProcessor,org.springframework.context.event.internalEventListenerFactory,mainController,restService,mvcResourceUrlProvider,org.springframework.web.servlet.handler.MappedInterceptor#0,mvcPathMatcher,mvcUrlPathHelper,org.springframework.web.servlet.resource.ResourceHttpRequestHandler#0,mvcCorsConfigurations,org.springframework.web.servlet.handler.SimpleUrlHandlerMapping#0,org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping,org.springframework.web.servlet.mvc.HttpRequestHandlerAdapter,org.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter,mvcHandlerMappingIntrospector,mvcContentNegotiationManager,org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping,org.springframework.format.support.FormattingConversionServiceFactoryBean#0,org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter,mvcUriComponentsContributor,org.springframework.web.servlet.handler.MappedInterceptor#1,org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver#0,org.springframework.web.servlet.mvc.annotation.ResponseStatusExceptionResolver#0,org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver#0,org.springframework.web.servlet.view.InternalResourceViewResolver#0,org.springframework.web.servlet.handler.SimpleUrlHandlerMapping#1,urlFilenameViewController]; parent: org.springframework.beans.factory.support.DefaultListableBeanFactory@5b4aa6b7\n" +
                "2018-07-12 12:31:19,791 DEBUG org.sprin.beans.facto.suppo.AbstractBeanFactory Returning cached instance of singleton bean 'org.springframework.context.annotation.internalConfigurationAnnotationProcessor'\n" +
                "2018-07-12 12:31:19,792 DEBUG org.sprin.beans.facto.suppo.AbstractBeanFactory Returning cached instance of singleton bean 'org.springframework.context.annotation.internalAutowiredAnnotationProcessor'\n" +
                "2018-07-12 15:11:22,914 DEBUG ua.com.juja.cmd.model.JDBCManager Trying to connect to DB with url='jdbc:postgresql://127.0.0.1:5432/sqlcmd'\n" +
                "2018-07-12 15:11:23,024 DEBUG ua.com.juja.cmd.model.JDBCManager Connection has been created\n" +
                "2018-07-12 15:11:32,462 DEBUG ua.com.juja.cmd.model.JDBCManager SELECT table_name FROM information_schema.tables WHERE table_schema='public' AND table_type='BASE TABLE'\n" +
                "2018-07-12 15:11:35,644 WARN ua.com.juja.cmd.model.JDBCManager !!!!DROP TABLE public.dsdsdsd\n" +
                "2018-07-12 15:11:35,691 DEBUG ua.com.juja.cmd.model.JDBCManager SELECT table_name FROM information_schema.tables WHERE table_schema='public' AND table_type='BASE TABLE'\n";
    }

    private Map<String, Long> getExpectedMap() {
        Map<String, Long> expected = new HashMap<>();
        expected.put("org.sprin.conte.suppo.AbstractApplicationContext,DEBUG", Long.valueOf(1));
        expected.put("ua.com.juja.cmd.model.JDBCManager,DEBUG", Long.valueOf(4));
        expected.put("org.sprin.beans.facto.suppo.DefaultListableBeanFactory,DEBUG", Long.valueOf(1));
        expected.put("org.sprin.ui.conte.suppo.UiApplicationContextUtils,DEBUG", Long.valueOf(1));
        expected.put("org.sprin.beans.facto.suppo.AbstractBeanFactory,DEBUG", Long.valueOf(2));
        expected.put("ua.com.juja.cmd.model.JDBCManager,WARN", Long.valueOf(1));
        return expected;
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
    }*/
}