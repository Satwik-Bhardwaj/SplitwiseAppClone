package com.satwik.splitwiseclone.persistence.dto.group;

import com.satwik.splitwiseclone.persistence.dto.expense.ExpenseListDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupDTO {

    private int groupId;

    private String groupName;

    private String owner;

    private List<ExpenseListDTO> expenses;

}
