package com.satwik.splitwiseclone.service.implementations;

import com.satwik.splitwiseclone.persistence.dto.report.ReportDTO;
import com.satwik.splitwiseclone.persistence.models.Group;
import com.satwik.splitwiseclone.persistence.models.User;
import com.satwik.splitwiseclone.repository.GroupRepository;
import com.satwik.splitwiseclone.repository.UserRepository;
import com.satwik.splitwiseclone.service.interfaces.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.UUID;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    UserRepository userRepository;
    @Override
    public List<ReportDTO> generateReport(UUID groupId, UUID userId) throws AccessDeniedException {

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new RuntimeException("Group not found"));

        if(user == null || user.getId() != group.getUser().getId()) throw new AccessDeniedException("Access Denied");

//        return groupRepository.generateReportById(groupId);
        return null;
    }
}
