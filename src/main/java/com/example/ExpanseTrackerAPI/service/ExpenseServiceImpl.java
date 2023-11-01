package com.example.ExpanseTrackerAPI.service;

import com.example.ExpanseTrackerAPI.entity.Expense;
import com.example.ExpanseTrackerAPI.exceptions.ResourceNotFoundException;
import com.example.ExpanseTrackerAPI.exceptions.ValidationException;
import com.example.ExpanseTrackerAPI.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ExpenseServiceImpl implements ExpenseService{

    @Autowired
    private ExpenseRepository expenseRepo;
    @Override
    public Page<Expense> getAllExpenses(Pageable page) {
        return expenseRepo.findAll(page);
    }

    @Override
    public Expense getExpenseById(Long id) {

        return expenseRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Expense is not found for id: " + id));

//        Optional<Expense> optionalExpense = expenseRepo.findById(id);
//
//        if(optionalExpense.isPresent()){
//            return optionalExpense.get();
//        }
//
//        throw new ResourceNotFoundException("Expense is not found for id: " + id);
    }

    @Override
    public List<Expense> readByCategory(String category, Pageable page) {
        return expenseRepo.findExpenseByCategory(category, page).toList();
    }

    @Override
    public List<Expense> readByName(String name, Pageable page){
        return expenseRepo.findByNameContaining(name, page).toList();
    }

    @Override
    public List<Expense> readByDate(Date startDate, Date endDate, Pageable page){
        if(startDate == null){
            startDate = new Date(0);
        }
        if(endDate == null){
            endDate = new Date(System.currentTimeMillis());
        }
        if(startDate.after(endDate)){
            throw new ValidationException("Start date must be before end date");
        }
        return expenseRepo.findByDateBetween(startDate, endDate, page).toList();
    }

    @Override
    public void deleteExpenseById(Long id) {
        Expense exp = getExpenseById(id);
        expenseRepo.delete(exp);
    }

    @Override
    public Expense saveExpanseDetails(Expense expense) {
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
