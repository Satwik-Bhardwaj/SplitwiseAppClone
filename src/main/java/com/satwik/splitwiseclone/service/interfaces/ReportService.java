package com.satwik.splitwiseclone.service.interfaces;

import com.satwik.splitwiseclone.persistence.dto.report.ReportDTO;

import java.util.UUID;
import java.util.List;

public interface ReportService {

    List<ReportDTO> generateReport(UUID groupId);

    String exportReport(UUID groupId, String fileType);
}
