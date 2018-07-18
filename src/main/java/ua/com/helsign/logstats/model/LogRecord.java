package ua.com.helsign.logstats.model;

public class LogRecord {
    private String data;
    private String className;
    private String severity;


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }
}
