package com.satwik.splitwiseclone.controller;

import com.satwik.splitwiseclone.persistence.dto.report.ReportDTO;
import com.satwik.splitwiseclone.service.interfaces.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/report")
public class ReportController {

    @Autowired
    ReportService reportService;

    @GetMapping("/{groupId}")
    public ResponseEntity<List<ReportDTO>> generateReport(@PathVariable UUID groupId) {

        try {

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            UUID userId = UUID.fromString(authentication.getName());

            return ResponseEntity.status(HttpStatus.OK).body(reportService.generateReport(groupId, userId));

        } catch (AccessDeniedException e) {
            throw new RuntimeException(e);
        }

    }

}
