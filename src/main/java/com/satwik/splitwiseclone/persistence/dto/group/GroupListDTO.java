package com.satwik.splitwiseclone.persistence.dto.group;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupListDTO {

    private String groupName;

    private String owner;

    public List<GroupListDTOWithin> groups;

}

