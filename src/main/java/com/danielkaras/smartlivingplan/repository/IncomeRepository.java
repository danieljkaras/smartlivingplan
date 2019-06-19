package com.danielkaras.smartlivingplan.repository;

import com.danielkaras.smartlivingplan.model.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {

    List<Income> findIncomeByUserIdEqualsAndIncomeDateBetween(Long userId, Date startDate, Date endDate);

}
