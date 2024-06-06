package com.claim.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.claim.demo.dto.ClaimReportDTO;
import com.claim.demo.dto.ClaimsSummaryDTO;
import com.claim.demo.entity.ClaimReport;
import com.claim.demo.entity.ClaimsSummary;
import com.claim.demo.repository.ClaimReportRepository;
import com.claim.demo.repository.ClaimsSummaryRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReportService {
    @Autowired
    private ClaimReportRepository claimReportRepository;

    @Autowired
    private ClaimsSummaryRepository claimsSummaryRepository;

    public List<ClaimReportDTO> generateReportByStatus() {
        List<ClaimReport> reports = claimReportRepository.findAll(); // This can be adjusted to specific needs
        return reports.stream()
                .map(this::convertToClaimReportDTO)
                .collect(Collectors.toList());
    }

    public ClaimsSummaryDTO generateClaimsSummary() {
        ClaimsSummary summary = claimsSummaryRepository.findTopByOrderByReportGeneratedDesc();
        return convertToClaimsSummaryDTO(summary);
    }

    private ClaimReportDTO convertToClaimReportDTO(ClaimReport report) {
        return new ClaimReportDTO(report.getClaimStatus(), report.getTotalClaims(), report.getTotalClaimAmount());
    }

    private ClaimsSummaryDTO convertToClaimsSummaryDTO(ClaimsSummary summary) {
        return new ClaimsSummaryDTO(summary.getReportGenerated(), summary.getNumberOfClaims(), summary.getTotalAmount());
    }
}

