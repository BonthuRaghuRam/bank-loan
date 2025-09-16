package com.example.model_2.Model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "Repayment")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Repayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "repaymentId")
    private Integer repaymentId;

    @ManyToOne
    @JoinColumn(name = "applicationId", nullable = false)
    private LoanApplication applicationId;

    @Column(name = "dueDate")
    private LocalDate dueDate;


    private Double amountDue;

    private LocalDate paymentDate;


    @Enumerated(EnumType.STRING)
    private PaymentStatus paymentStatus;


    public enum PaymentStatus {
        PENDING, COMPLETED
    }

}
