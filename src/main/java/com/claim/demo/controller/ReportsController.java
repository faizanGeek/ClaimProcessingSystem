package com.claim.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.claim.demo.dto.ClaimReportDTO;
import com.claim.demo.dto.ClaimsSummaryDTO;
import com.claim.demo.service.ReportService;

/**
 * REST controller for handling report related operations.
 * This controller provides endpoints for accessing reports about claims' statuses and summaries.
 */
@RestController
@RequestMapping("/reports")  // Base URI for all handlers in this controller.
public class ReportsController {
    @Autowired
    private ReportService reportService;  // Service layer handling the business logic for generating reports.

    /**
     * Endpoint to retrieve a report of claims grouped by their status.
     * This can be useful for dashboard or monitoring purposes to see the distribution of claims' statuses.
     * 
     * @return ResponseEntity containing a list of ClaimReportDTO with detailed claims status report.
     */
    @GetMapping("/claims/status")
    public ResponseEntity<List<ClaimReportDTO>> getClaimsReportByStatus() {
        List<ClaimReportDTO> reports = reportService.generateReportByStatus();  // Call service to generate the report.
        return ResponseEntity.ok(reports);  // Return the report with HTTP status 200 (OK).
    }

    /**
     * Endpoint to get a summary of claims.
     * This summary might include aggregated data like total claims, total approved, total rejected, etc.
     * 
     * @return ResponseEntity containing a ClaimsSummaryDTO with summary details of all claims.
     */
    @GetMapping("/claims/summary")
    public ResponseEntity<ClaimsSummaryDTO> getClaimsSummary() {
        ClaimsSummaryDTO summary = reportService.generateClaimsSummary();  // Call service to generate the summary.
        return ResponseEntity.ok(summary);  // Return the summary with HTTP status 200 (OK).
    }
}
