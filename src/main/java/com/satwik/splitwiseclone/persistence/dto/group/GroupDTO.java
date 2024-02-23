package com.satwik.splitwiseclone.persistence.dto.group;

import com.satwik.splitwiseclone.persistence.dto.expense.ExpenseListDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupDTO {

    @NotNull
    @NotBlank
    private int groupId;

    @NotNull
    private String groupName;

    private String owner;

    private List<ExpenseListDTO> expenses;

}
