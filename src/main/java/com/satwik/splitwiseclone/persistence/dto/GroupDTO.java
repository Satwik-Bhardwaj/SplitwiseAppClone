package com.satwik.splitwiseclone.persistence.dto;

import com.satwik.splitwiseclone.persistence.models.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupDTO {

    private String group_name;

    private String owner;

    private List<ExpenseDTO> expenses;

}
