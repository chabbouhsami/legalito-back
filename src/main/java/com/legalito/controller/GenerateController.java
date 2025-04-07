package com.legalito.controller;

import com.legalito.model.GeneratedLetter;
import com.legalito.model.Template;
import com.legalito.repository.GeneratedLetterRepository;
import com.legalito.repository.TemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/generate")
@RequiredArgsConstructor
public class GenerateController {

    private final GeneratedLetterRepository letterRepository;
    private final TemplateRepository templateRepository;

    @PostMapping
    public GeneratedLetter generateLetter(@RequestBody Map<String, Object> request) {
        Long templateId = Long.valueOf(request.get("templateId").toString());
        Map<String, Object> formData = (Map<String, Object>) request.get("formData");

        Template template = templateRepository.findById(templateId).orElseThrow();

        GeneratedLetter letter = GeneratedLetter.builder()
                                                .template(template)
                                                .dataJson(formData.toString())
                                                .pdfUrl("mock.pdf") // temporairement
                                                .createdAt(LocalDateTime.now())
                                                .build();

        return letterRepository.save(letter);
    }
}
