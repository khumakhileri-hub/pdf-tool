package com.pdftool.service;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class PdfService {

    public File resize(File inputFile) throws Exception {
        PDDocument document = Loader.loadPDF(inputFile);

        for (PDPage page : document.getPages()) {
            PDRectangle box = page.getMediaBox();
            page.setMediaBox(new PDRectangle(
                    box.getWidth() * 0.8f,
                    box.getHeight() * 0.8f
            ));
        }

        String basePath = System.getProperty("user.dir");

        if (!basePath.endsWith("backend")) {
            basePath = basePath + "/backend";
        }

        String outputDir = basePath + "/outputs/";

        File outDir = new File(outputDir);
        if (!outDir.exists()) {
            outDir.mkdirs();
        }

        File output = new File(outputDir + "resized_" + System.currentTimeMillis() + ".pdf");
        document.save(output);
        document.close();

        return output;
    }
}