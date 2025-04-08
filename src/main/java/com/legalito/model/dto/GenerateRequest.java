package com.legalito.model.dto;

import lombok.Data;

import java.util.Map;

@Data
public class GenerateRequest {

    private Long templateId;
    private Map<String, Object> formData;
}
