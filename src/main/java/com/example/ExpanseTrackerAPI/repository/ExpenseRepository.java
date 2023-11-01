package com.example.ExpanseTrackerAPI.repository;

import com.example.ExpanseTrackerAPI.entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    Optional<Expense> findById(Long id);

    List<Expense> findAll();

    Page<Expense> findExpenseByCategory(String category, Pageable page);

    //SELECT * FROM tbl_expenses WHERE name LIKE '%keyword%'
    Page<Expense> findByNameContaining(String keyword, Pageable page);

    // SELECT * FROM tbl_expenses WHERE data BETWEEN 'startDate' AND 'endDate'
    Page<Expense> findByDateBetween(Date startDate, Date endDate, Pageable page);
    void deleteById(Long id);

}
