package com.satwik.splitwiseclone.service.interfaces;

import com.satwik.splitwiseclone.persistence.dto.report.ReportDTO;
import com.satwik.splitwiseclone.persistence.dto.report.TempReport;

import java.nio.file.AccessDeniedException;
import java.util.UUID;
import java.util.List;

public interface ReportService {

    public List<ReportDTO> generateReport(UUID groupId, UUID userId) throws AccessDeniedException;

    public String exportReport(UUID groupId, UUID userId, String fileType) throws AccessDeniedException;
}
