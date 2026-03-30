package com.pdftool.worker;


import com.pdftool.controller.PdfController;
import com.pdftool.model.Job;
import com.pdftool.service.PdfService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class Worker {

    @Autowired
    private PdfService pdfService;

    @RabbitListener(queues = "pdf-queue")
    public void process(Job job) {
        try {
            File input = new File(job.getFilePath());
            File output = pdfService.resize(input);

            job.setStatus("DONE");
            job.setOutputPath(output.getAbsolutePath());

        } catch (Exception e) {
            job.setStatus("FAILED");
        }

        PdfController.jobStore.put(job.getId(), job);
    }
}