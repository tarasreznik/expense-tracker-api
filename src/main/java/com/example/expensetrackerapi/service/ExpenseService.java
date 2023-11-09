package com.example.expensetrackerapi.service;

import com.example.expensetrackerapi.entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Date;
import java.util.List;

public interface ExpenseService {

    Page<Expense> getAllExpenses(Pageable page);

    List<Expense> readByCategory(String category, Pageable page);

    List<Expense> readByName(String name, Pageable page);

    List<Expense> readByDate(Date startDate, Date endDate, Pageable page);

    Expense getExpenseById(Long id);


    void deleteExpenseById(Long id);

    Expense saveExpanseDetails(Expense expense);

    Expense updateExpanseDetails(Long id, Expense expense);


}
