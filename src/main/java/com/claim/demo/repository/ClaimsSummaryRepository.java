package com.claim.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.claim.demo.entity.ClaimsSummary;

import java.util.Date;
import java.util.List;

@Repository
public interface ClaimsSummaryRepository extends JpaRepository<ClaimsSummary, Long> {
    // Method to find the most recent summary report
    ClaimsSummary findTopByOrderByReportGeneratedDesc();

    // Additional method to find summaries over a specific period
    List<ClaimsSummary> findByReportGeneratedBetween(Date start, Date end);
}
