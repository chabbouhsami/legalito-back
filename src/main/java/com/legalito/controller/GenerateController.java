package com.legalito.controller;

import com.legalito.model.dto.GenerateRequest;
import com.legalito.model.entity.Template;
import com.legalito.repository.GeneratedLetterRepository;
import com.legalito.repository.TemplateRepository;
import com.legalito.service.PdfService;
import freemarker.template.TemplateException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/generate")
@RequiredArgsConstructor
public class GenerateController {

    private final GeneratedLetterRepository letterRepository;
    private final TemplateRepository templateRepository;
    private final PdfService pdfService;

    @PostMapping
    public ResponseEntity<byte[]> generateLetter(@RequestBody GenerateRequest request) throws IOException, TemplateException {
        Template template = templateRepository.findById(request.getTemplateId())
                                              .orElseThrow(() -> new RuntimeException("Template not found"));

        byte[] pdf = pdfService.generatePdf(template.getContentTemplate(), request.getFormData());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.inline().filename("legalito.pdf").build());

        return ResponseEntity.ok().headers(headers).body(pdf);
    }
}
