package com.pdftool.model;

import java.io.Serializable;

public class Job implements Serializable {
    private String id;
    private String filePath;
    private String status;
    private String outputPath;

    public Job() {}

    public Job(String id, String filePath, String status) {
        this.id = id;
        this.filePath = filePath;
        this.status = status;
    }

    public String getId() { return id; }
    public String getFilePath() { return filePath; }
    public String getStatus() { return status; }
    public String getOutputPath() { return outputPath; }

    public void setStatus(String status) { this.status = status; }
    public void setOutputPath(String outputPath) { this.outputPath = outputPath; }
}