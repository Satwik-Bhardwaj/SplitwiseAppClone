package com.satwik.splitwiseclone.persistence.dto.group;

import com.satwik.splitwiseclone.persistence.dto.expense.ExpenseListDTO;
import com.satwik.splitwiseclone.persistence.models.GroupMembers;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupDTO {

    private UUID groupId;

    @NotBlank(message = "Set a valid group name")
    private String groupName;

    private String owner;

    private List<ExpenseListDTO> expenses;

    private List<GroupMemberDTO> groupMembers;

}
