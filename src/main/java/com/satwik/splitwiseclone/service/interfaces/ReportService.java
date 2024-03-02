package com.satwik.splitwiseclone.service.interfaces;

import com.satwik.splitwiseclone.persistence.dto.report.ReportDTO;

import java.nio.file.AccessDeniedException;
import java.util.UUID;
import java.util.List;

public interface ReportService {

    List<ReportDTO> generateReport(UUID groupId, UUID userId) throws Exception;

    String exportReport(UUID groupId, UUID userId, String fileType) throws AccessDeniedException;
}
