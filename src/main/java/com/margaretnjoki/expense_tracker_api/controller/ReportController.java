package com.margaretnjoki.expense_tracker_api.controller;

import com.margaretnjoki.expense_tracker_api.dto.CategoryTotalResponse;
import com.margaretnjoki.expense_tracker_api.dto.MonthlyReportResponse;
import com.margaretnjoki.expense_tracker_api.service.ExpenseService;
import com.margaretnjoki.expense_tracker_api.service.ReportService;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
@RestController
@Validated
@RequestMapping("/reports")
public class ReportController {
    private final ReportService service;
    public ReportController(ReportService service) {
        this.service = service;
    }

    @GetMapping("/monthly")
    public MonthlyReportResponse monthly(
            @RequestParam @Min(2000) int year,
            @RequestParam @Min(1) @Max(12) int month){
        return service.monthlyReport(year, month);
    }
    @GetMapping("/by-category")
    public List<CategoryTotalResponse> byCategory(
            @RequestParam LocalDate from,
            @RequestParam LocalDate to){
        return service.totalByCategory(from, to);
    }

}
