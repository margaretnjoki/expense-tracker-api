package com.margaretnjoki.expense_tracker_api.controller;

import com.margaretnjoki.expense_tracker_api.dto.CreateExpenseRequest;
import com.margaretnjoki.expense_tracker_api.dto.ExpenseResponse;
import com.margaretnjoki.expense_tracker_api.dto.TotalExpenseResponse;
import com.margaretnjoki.expense_tracker_api.dto.UpdateExpenseRequest;
import com.margaretnjoki.expense_tracker_api.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public List<ExpenseResponse> list(){
        return service.findAll().stream().map(ExpenseResponse::from).toList();
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
}
