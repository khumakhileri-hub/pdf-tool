package com.pdftool.controller;


import com.pdftool.model.Job;
import com.pdftool.queue.QueueProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@CrossOrigin(origins = "*")
public class PdfController {

    public static Map<String, Job> jobStore = new ConcurrentHashMap<>();

    @Autowired
    private QueueProducer producer;

    @PostMapping("/resize")
    public String resize(@RequestParam MultipartFile file) throws Exception {

        if (!file.getOriginalFilename().endsWith(".pdf")) {
            throw new RuntimeException("Only PDF allowed");
        }

        String fileName = UUID.randomUUID() + ".pdf";
        String basePath = System.getProperty("user.dir");

        if (!basePath.endsWith("backend")) {
            basePath = basePath + "/backend";
        }

        String uploadDir = basePath + "/uploads/";

        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File saved = new File(uploadDir + fileName);
        file.transferTo(saved);

        Job job = new Job(UUID.randomUUID().toString(), saved.getAbsolutePath(), "PENDING");
        jobStore.put(job.getId(), job);

        producer.send(job);

        return job.getId();
    }

    @GetMapping("/status/{id}")
    public Job status(@PathVariable String id) {
        return jobStore.get(id);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<Resource> download(@PathVariable String id) throws Exception {

        Job job = jobStore.get(id);

        if (job == null || !"DONE".equals(job.getStatus())) {
            throw new RuntimeException("File not ready");
        }

        File file = new File(job.getOutputPath());
        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .header("Content-Disposition", "attachment; filename=output.pdf")
                .contentLength(file.length())
                .body(resource);
    }
}