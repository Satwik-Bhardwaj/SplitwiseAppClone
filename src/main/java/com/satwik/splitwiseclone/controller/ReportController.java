package com.satwik.splitwiseclone.controller;

import com.satwik.splitwiseclone.configuration.security.LoggedInUser;
import com.satwik.splitwiseclone.persistence.dto.report.ReportDTO;
import com.satwik.splitwiseclone.service.interfaces.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/report")
public class ReportController {

    @Autowired
    ReportService reportService;

    @Autowired
    LoggedInUser loggedInUser;

    @GetMapping("/{groupId}")
    public ResponseEntity<List<ReportDTO>> generateReport(@PathVariable UUID groupId) {
        log.info("Get Endpoint: generate a report for a group with groupId: {}", groupId);
        List<ReportDTO> response = reportService.generateReport(groupId);
        log.info("Get Endpoint: generate a report for a group with response: {}", response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{groupId}/export")
    public ResponseEntity<String> exportReport(@PathVariable UUID groupId, @RequestParam String fileType) {
        log.info("Get Endpoint: export the report triggered");
        String response = reportService.exportReport(groupId, fileType);
        log.info("Get Endpoint: export the report ended");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
