package com.example.model_2.Model;

import jakarta.persistence.*;
        import lombok.*;

@Entity
@Table(name = "LoanProduct")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loanProductId")
    private Integer loanProductId;

    private String productName;

    private Double interestRate;

    private Double minAmount;

    private Double maxAmount;

    private Integer tenure;
}
