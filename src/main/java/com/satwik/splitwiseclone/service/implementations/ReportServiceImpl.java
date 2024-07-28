package com.satwik.splitwiseclone.service.implementations;

import com.satwik.splitwiseclone.persistence.dto.report.ReportDTO;
import com.satwik.splitwiseclone.persistence.dto.report.TempReport;
import com.satwik.splitwiseclone.persistence.entities.Group;
import com.satwik.splitwiseclone.persistence.entities.User;
import com.satwik.splitwiseclone.repository.ExpenseShareRepository;
import com.satwik.splitwiseclone.repository.GroupRepository;
import com.satwik.splitwiseclone.repository.UserRepository;
import com.satwik.splitwiseclone.service.interfaces.ReportService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExpenseShareRepository expenseShareRepository;

    @Value("${my.reportFilePath}")
    private String REPORT_FILE_PATH;

    @Override
    public List<ReportDTO> generateReport(UUID groupId) {

        Group group = groupRepository.findById(groupId).orElseThrow(() -> new RuntimeException("Group not found"));
        // preparing report
        List<TempReport> tempReportList = groupRepository.generateReportById(group.getId());
        List<ReportDTO> reportDTOS = new ArrayList<>();

        for (TempReport tempReport : tempReportList) {
            ReportDTO reportDTO = new ReportDTO();
            reportDTO.setExpenseOwner(tempReport.getExpenseOwner());
            reportDTO.setExpenseName(tempReport.getExpenseName());
            reportDTO.setGroupName(tempReport.getGroupName());
            reportDTO.setTotalExpenseAmount(tempReport.getTotalExpenseAmount());

//          getting contributors
            List<String> contributors = expenseShareRepository.findPayeesById(tempReport.getExpenseId());
            reportDTO.setExpenseContributors(contributors);
            reportDTOS.add(reportDTO);
        }

        return reportDTOS;
    }

    @Override
    public String exportReport(UUID groupId, String fileType) {
        User user = authorizationService.getAuthorizedUser();
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new RuntimeException("Group not found"));

        if(user == null || user.getId() != group.getUser().getId()) throw new RuntimeException("Access Denied");

        if(fileType.equals("XLSX"))
            exportToXLSX(groupId);
        else if (fileType.equals("CSV"))
            exportToCSV(groupId);
        else
            throw new RuntimeException("Invalid file type");

        return "File successfully created at path - " + REPORT_FILE_PATH;
    }

    private void exportToCSV(UUID groupId) {

        List<TempReport> tempReportList = groupRepository.generateReportById(groupId);
        List<ReportDTO> reportDTOS = new ArrayList<>();

        for (TempReport tempReport : tempReportList) {
            ReportDTO reportDTO = new ReportDTO();
            reportDTO.setExpenseOwner(tempReport.getExpenseOwner());
            reportDTO.setExpenseName(tempReport.getExpenseName());
            reportDTO.setGroupName(tempReport.getGroupName());
            reportDTO.setTotalExpenseAmount(tempReport.getTotalExpenseAmount());

//          getting contributors
            List<String> contributors = expenseShareRepository.findPayeesById(tempReport.getExpenseId());
            reportDTO.setExpenseContributors(contributors);
            reportDTOS.add(reportDTO);
        }

        try (FileOutputStream fos = new FileOutputStream(REPORT_FILE_PATH + "data.csv")) {
            StringBuilder csvData = new StringBuilder();
            csvData.append("groupName,expenseName,expenseOwner,expenseContributors,totalExpenseAmount\n");
            for (ReportDTO reportDTO : reportDTOS) {
                csvData.append("\"").append(reportDTO.getGroupName()).append("\",");
                csvData.append("\"").append(reportDTO.getExpenseName()).append("\",");
                csvData.append("\"").append(reportDTO.getExpenseOwner()).append("\",");
                csvData.append("\"").append(String.join(",", reportDTO.getExpenseContributors())).append("\",");
                csvData.append(reportDTO.getTotalExpenseAmount()).append("\n");
            }
            fos.write(csvData.toString().getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void exportToXLSX(UUID groupId) {
        List<TempReport> tempReportList = groupRepository.generateReportById(groupId);
        List<ReportDTO> reportDTOS = new ArrayList<>();

        for (TempReport tempReport : tempReportList) {
            ReportDTO reportDTO = new ReportDTO();
            reportDTO.setExpenseOwner(tempReport.getExpenseOwner());
            reportDTO.setExpenseName(tempReport.getExpenseName());
            reportDTO.setGroupName(tempReport.getGroupName());
            reportDTO.setTotalExpenseAmount(tempReport.getTotalExpenseAmount());

//          getting contributors
            List<String> contributors = expenseShareRepository.findPayeesById(tempReport.getExpenseId());
            reportDTO.setExpenseContributors(contributors);
            reportDTOS.add(reportDTO);
        }

        try (FileOutputStream outputStream = new FileOutputStream(REPORT_FILE_PATH + "data.xlsx")) {

            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Data");
            int rowNum = 0;

            Row headerRow = sheet.createRow(rowNum++);
            headerRow.createCell(0).setCellValue("groupName");
            headerRow.createCell(1).setCellValue("expenseName");
            headerRow.createCell(2).setCellValue("expenseOwner");
            headerRow.createCell(3).setCellValue("expenseContributors");
            headerRow.createCell(4).setCellValue("totalExpenseAmount");

            for (ReportDTO reportDTO : reportDTOS) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(reportDTO.getGroupName());
                row.createCell(1).setCellValue(reportDTO.getExpenseName());
                row.createCell(2).setCellValue(reportDTO.getExpenseOwner());
                row.createCell(3).setCellValue(String.join(",", reportDTO.getExpenseContributors()));
                row.createCell(4).setCellValue(reportDTO.getTotalExpenseAmount());
            }
            workbook.write(outputStream);
            workbook.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}
