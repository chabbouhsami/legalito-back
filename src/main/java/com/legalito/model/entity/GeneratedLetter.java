package com.legalito.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class GeneratedLetter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Users user;

    @ManyToOne
    private Template template;

    @Column(columnDefinition = "TEXT")
    private String dataJson;

    private String pdfUrl;
    private LocalDateTime createdAt;
}
