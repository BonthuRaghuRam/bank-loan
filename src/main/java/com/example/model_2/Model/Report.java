package com.example.model_2.Model;

import java.util.List;
import java.util.Map;

/**
 * A simple model to represent a financial report.
 * This class can be extended to include more detailed structures
 * for different types of reports.
 */
public class Report {
    private String reportName;
    private String generatedBy;
    private String generatedDate;
    private List<Map<String, Object>> data;

    // Constructors
    public Report() {
    }

    public Report(String reportName, String generatedBy, String generatedDate, List<Map<String, Object>> data) {
        this.reportName = reportName;
        this.generatedBy = generatedBy;
        this.generatedDate = generatedDate;
        this.data = data;
    }

    // Getters and Setters
    public String getReportName() {
        return reportName;
    }

    public void setReportName(String reportName) {
        this.reportName = reportName;
    }

    public String getGeneratedBy() {
        return generatedBy;
    }

    public void setGeneratedBy(String generatedBy) {
        this.generatedBy = generatedBy;
    }

    public String getGeneratedDate() {
        return generatedDate;
    }

    public void setGeneratedDate(String generatedDate) {
        this.generatedDate = generatedDate;
    }

    public List<Map<String, Object>> getData() {
        return data;
    }

    public void setData(List<Map<String, Object>> data) {
        this.data = data;
    }
}
