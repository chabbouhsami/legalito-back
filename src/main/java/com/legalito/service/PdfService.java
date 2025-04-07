package com.legalito.service;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PdfService {

    private final Configuration freemarkerConfig;

    public byte[] generatePdf(String templateName, Map<String, Object> data) throws IOException, TemplateException {
        Template template = freemarkerConfig.getTemplate(templateName);
        StringWriter stringWriter = new StringWriter();
        template.process(data, stringWriter);
        String html = stringWriter.toString();

        Path tempFile = Files.createTempFile("legalito-", ".pdf");
        try (OutputStream os = new FileOutputStream(tempFile.toFile())) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.withHtmlContent(html, null);
            builder.toStream(os);
            builder.run();
        }

        return Files.readAllBytes(tempFile);
    }
}
