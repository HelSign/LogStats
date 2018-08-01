package ua.com.helsign.logstats.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.helsign.logstats.model.LogFile;
import ua.com.helsign.logstats.model.Statistic;

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
import static org.junit.jupiter.api.Assertions.assertTrue;

class LogReaderTest {

    @Test
    void shouldReadData() {
        List<String> actual = LogReader.getData(getTestList());
        List<String> expected = getExpectedList();
        assertEquals(expected, actual);
    }


    private List<String> getTestList() {
        List<String> testList = new ArrayList<>();
        testList.add("2018-07-12 12:31:19,788 DEBUG org.sprin.conte.suppo.AbstractApplicationContext Unable");
        testList.add(" to ");
        testList.add("locate ");
        testList.add("ApplicationEventMulticaster with name 'applicationEventMulticaster': using default [org.springframework.context.event.SimpleApplicationEventMulticaster@7ce43a40]\n");
        testList.add("2018-07-12 12:31:19,789 DEBUG org.sprin.ui.conte.suppo.UiApplicationContextUtils Unable to locate ThemeSource with name 'themeSource': using default [org.springframework.ui.context.support.DelegatingThemeSource@b9ea7f6]\n");
        testList.add("2018-07-12 12:31:19,791 DEBUG org.sprin.beans.facto.suppo.DefaultListableBeanFactory Pre-instantiating singletons in org.springframework.beans.factory.support.DefaultListableBeanFactory@2f816072: defining beans [org.springframework.context.annotation.internalConfigurationAnnotationProcessor,org.springframework.context.annotation.internalAutowiredAnnotationProcessor,org.springframework.context.annotation.internalRequiredAnnotationProcessor,org.springframework.context.annotation.internalCommonAnnotationProcessor,org.springframework.context.annotation.internalPersistenceAnnotationProcessor,org.springframework.context.event.internalEventListenerProcessor,org.springframework.context.event.internalEventListenerFactory,mainController,restService,mvcResourceUrlProvider,org.springframework.web.servlet.handler.MappedInterceptor#0,mvcPathMatcher,mvcUrlPathHelper,org.springframework.web.servlet.resource.ResourceHttpRequestHandler#0,mvcCorsConfigurations,org.springframework.web.servlet.handler.SimpleUrlHandlerMapping#0,org.springframework.web.servlet.handler.BeanNameUrlHandlerMapping,org.springframework.web.servlet.mvc.HttpRequestHandlerAdapter,org.springframework.web.servlet.mvc.SimpleControllerHandlerAdapter,mvcHandlerMappingIntrospector,mvcContentNegotiationManager,org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping,org.springframework.format.support.FormattingConversionServiceFactoryBean#0,org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter,mvcUriComponentsContributor,org.springframework.web.servlet.handler.MappedInterceptor#1,org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver#0,org.springframework.web.servlet.mvc.annotation.ResponseStatusExceptionResolver#0,org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver#0,org.springframework.web.servlet.view.InternalResourceViewResolver#0,org.springframework.web.servlet.handler.SimpleUrlHandlerMapping#1,urlFilenameViewController]; parent: org.springframework.beans.factory.support.DefaultListableBeanFactory@5b4aa6b7\n");
        testList.add("2018-07-12 12:31:19,791 DEBUG org.sprin.beans.facto.suppo.AbstractBeanFactory Returning cached instance of singleton bean 'org.springframework.context.annotation.internalConfigurationAnnotationProcessor'\n");
        testList.add("2018-07-12 12:31:19,792 DEBUG org.sprin.beans.facto.suppo.AbstractBeanFactory Returning cached instance of singleton bean 'org.springframework.context.annotation.internalAutowiredAnnotationProcessor'\n");
        testList.add("2018-07-12 15:11:22,914 DEBUG ua.com.juja.cmd.model.JDBCManager Trying to connect to DB with url='jdbc:postgresql://127.0.0.1:5432/sqlcmd'\n");
        testList.add("2018-07-12 15:11:23,024 DEBUG ua.com.juja.cmd.model.JDBCManager Connection has been created\n");
        testList.add("2018-07-12 15:11:32,462 DEBUG ua.com.juja.cmd.model.JDBCManager SELECT table_name FROM information_schema.tables WHERE table_schema='public' AND table_type='BASE TABLE'\n");
        testList.add("2018-07-12 15:11:35,644 WARN ua.com.juja.cmd.model.JDBCManager !!!!DROP TABLE public.dsdsdsd\n");
        testList.add("2018-07-12 15:11:35,691 DEBUG ua.com.juja.cmd.model.JDBCManager SELECT table_name FROM information_schema.tables WHERE table_schema='public' AND table_type='BASE TABLE'\n");
        return testList;
    }

    private List<String> getExpectedList() {
        List<String> expected = new ArrayList<>();
        expected.add("org.sprin.beans.facto.suppo.AbstractBeanFactory,DEBUG,2");
        expected.add("org.sprin.beans.facto.suppo.DefaultListableBeanFactory,DEBUG,1");
        expected.add("ua.com.juja.cmd.model.JDBCManager,DEBUG,4");
        expected.add("org.sprin.conte.suppo.AbstractApplicationContext,DEBUG,1");
        expected.add("org.sprin.ui.conte.suppo.UiApplicationContextUtils,DEBUG,1");
        expected.add("ua.com.juja.cmd.model.JDBCManager,WARN,1");
        return expected;
    }
}