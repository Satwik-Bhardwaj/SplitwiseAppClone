package com.satwik.splitwiseclone.persistence.dto.expense;

import com.satwik.splitwiseclone.persistence.dto.user.PayeeDTO;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExpenseDTO {

    private String payerName;

    private double amount;

    private String description;

    private String date;

    private List<PayeeDTO> payees;

}
