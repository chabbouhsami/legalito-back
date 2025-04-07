package com.legalito.controller;

import com.legalito.model.Template;
import com.legalito.repository.TemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/templates")
@RequiredArgsConstructor
public class TemplateController {

    private final TemplateRepository templateRepository;

    @GetMapping
    public List<Template> getAllTemplates() {
        return templateRepository.findAll();
    }
}
