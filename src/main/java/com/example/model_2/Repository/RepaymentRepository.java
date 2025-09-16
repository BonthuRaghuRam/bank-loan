package com.example.model_2.Repository;

import com.example.model_2.Model.Customer;
import com.example.model_2.Model.LoanApplication;
import com.example.model_2.Model.Repayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RepaymentRepository extends JpaRepository<Repayment, Long> {


    @Query("SELECT r FROM Repayment r WHERE r.applicationId.applicationId = :applicationId AND r.paymentStatus = 'PENDING' AND amountDue > 0")
    Optional<Repayment> findByRemainingAmount(Integer applicationId);

    @Query("SELECT r FROM Repayment r WHERE r.applicationId.applicationId = :applicationId ORDER BY r.dueDate ASC")
    Optional<Repayment> findByDueDate(Integer applicationId);


    List<Repayment> findByApplicationId(LoanApplication application);
}



