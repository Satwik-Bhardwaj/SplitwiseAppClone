package com.satwik.splitwiseclone.persistence.dto;

import com.satwik.splitwiseclone.persistence.models.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupDTO {

    private String groupName;

}
