package com.satwik.splitwiseclone.persistence.dto.group;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupListDTOWithin {

    @NotNull
    @NotBlank
    private int groupId;

    @NotNull
    private String groupName;

}