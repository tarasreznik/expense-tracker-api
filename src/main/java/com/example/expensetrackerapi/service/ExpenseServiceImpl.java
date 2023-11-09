package com.example.expensetrackerapi.service;

import com.example.expensetrackerapi.entity.Expense;
import com.example.expensetrackerapi.exceptions.ResourceNotFoundException;
import com.example.expensetrackerapi.exceptions.ValidationException;
import com.example.expensetrackerapi.repository.ExpenseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseServiceImpl implements ExpenseService {
    private final ExpenseRepository expenseRepo;

    private final UserService userService;

    public ExpenseServiceImpl(ExpenseRepository expenseRepo, UserService userService) {
        this.expenseRepo = expenseRepo;
        this.userService = userService;
    }

    @Override
    public Page<Expense> getAllExpenses(Pageable page) {
        return expenseRepo.findByUserId(userService.getLoggedInUser().getId(), page);
    }

    @Override
    public Expense getExpenseById(Long id) {
        Optional<Expense> optionalExpense = expenseRepo.findByUserIdAndId(userService.getLoggedInUser().getId(), id);

        if (optionalExpense.isPresent()) {
            return optionalExpense.get();
        }

        throw new ResourceNotFoundException("Expense is not found for id: " + id);
    }

    @Override
    public List<Expense> readByCategory(String category, Pageable page) {
        return expenseRepo.findByUserIdAndCategory(userService.getLoggedInUser().getId(), category, page).toList();
    }

    @Override
    public List<Expense> readByName(String name, Pageable page) {
        return expenseRepo.findByUserIdAndNameContaining(userService.getLoggedInUser().getId(), name, page).toList();
    }

    @Override
    public List<Expense> readByDate(Date startDate, Date endDate, Pageable page) {
        if (startDate == null) {
            startDate = new Date(0);
        }
        if (endDate == null) {
            endDate = new Date(System.currentTimeMillis());
        }
        if (startDate.after(endDate)) {
            throw new ValidationException("Start date must be before end date");
        }
        return expenseRepo.findByUserIdAndDateBetween(userService.getLoggedInUser().getId(), startDate, endDate, page)
                .toList();
    }

    @Override
    public void deleteExpenseById(Long id) {
        Expense exp = getExpenseById(id);
        expenseRepo.delete(exp);
    }

    @Override
    public Expense saveExpanseDetails(Expense expense) {
        expense.setUser(userService.getLoggedInUser());
        return expenseRepo.save(expense);
    }

    @Override
    public Expense updateExpanseDetails(Long id, Expense expense) {
        Expense existingExpense = getExpenseById(id);
        existingExpense.setName(expense.getName() != null ? expense.getName() : existingExpense.getName());
        existingExpense.setDescription(expense.getDescription() != null ? expense.getDescription() : existingExpense.getDescription());
        existingExpense.setAmount(expense.getAmount() != null ? expense.getAmount() : existingExpense.getAmount());
        existingExpense.setCategory(expense.getCategory() != null ? expense.getCategory() : existingExpense.getCategory());
        existingExpense.setDate(expense.getDate() != null ? expense.getDate() : existingExpense.getDate());

        return expenseRepo.save(existingExpense);
    }


}
