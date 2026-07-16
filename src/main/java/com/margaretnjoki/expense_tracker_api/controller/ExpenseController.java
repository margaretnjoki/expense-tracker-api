package com.margaretnjoki.expense_tracker_api.controller;

import com.margaretnjoki.expense_tracker_api.dto.*;
import com.margaretnjoki.expense_tracker_api.model.Expense;
import com.margaretnjoki.expense_tracker_api.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/expenses")
public class ExpenseController {
    private final ExpenseService service;

    public ExpenseController(ExpenseService service) {
        this.service = service;
    }

    @GetMapping
    public PagedResponse<ExpenseResponse> list(
            @RequestParam(required = false) LocalDate from,
            @RequestParam(required = false) LocalDate to,
            Pageable pageable
    ) {

        return service.findAll(from, to, pageable);
    }

    @GetMapping("/filter")
    public List<ExpenseResponse> findByDate(@RequestParam LocalDate from, LocalDate to){
       return service.findExpensesBetween(from, to).stream().map(ExpenseResponse::from).toList();
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ExpenseResponse create(@Valid @RequestBody CreateExpenseRequest req){
        return  ExpenseResponse .from(service.create(req));
    }

    @GetMapping("/total")
    public TotalExpenseResponse total() {
        return new TotalExpenseResponse(service.getTotalSpent());
    }

    @GetMapping("/{id}")
    public ExpenseResponse getOne(@PathVariable UUID id) {
        return ExpenseResponse.from(service.findById(id));
    }

    @PutMapping("/{id}")
    public ExpenseResponse update(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateExpenseRequest request) {

        return ExpenseResponse.from(service.update(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id){
        service.delete(id);

    }

    @GetMapping("/reports/categories")
    public List<Object[]> categoryReport(
            @RequestParam BigDecimal min) {

        return service.findCategoriesWithTotalGreaterThan(min);
    }

    @GetMapping("/reports/summary")
    public SummaryReportResponse summary(
            @RequestParam LocalDate from,
            @RequestParam LocalDate to) {

        return service.getSummaryReport(from, to);
    }
}
