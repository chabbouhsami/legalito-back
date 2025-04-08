package com.legalito.service;

import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PdfService {

    private final Configuration freemarkerConfig;

    public byte[] generatePdf(String rawTemplate, Map<String, Object> data) throws IOException, TemplateException {
        Template template = new Template("template", new StringReader(rawTemplate), freemarkerConfig);

        StringWriter stringWriter = new StringWriter();
        template.process(data, stringWriter);
        String innerHtml = stringWriter.toString();

        String html = """
                <!DOCTYPE html>
                <html lang="fr">
                  <head>
                    <meta charset="UTF-8"/>
                    <title>Document</title>
                  </head>
                  <body>
                    %s
                  </body>
                </html>
                """.formatted(innerHtml);

        Path tempFile = Files.createTempFile("legalito_", ".pdf");
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
