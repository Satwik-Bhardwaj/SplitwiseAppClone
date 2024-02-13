package com.satwik.splitwiseclone.service.interfaces;

import com.satwik.splitwiseclone.persistence.dto.ExpenseDTO;
import com.satwik.splitwiseclone.persistence.dto.GroupDTO;

import java.util.List;

public interface GroupService {

    // create new group
    String createGroup(GroupDTO groupDTO, int userId);

    // find all Group
    List<GroupDTO> findAllGroup(int userId);

    // find new Group
    GroupDTO findGroupById(int groupId, int userId);

    // delete a Group
    String deleteGroupById(int groupId, int userId);

    // maintain default non-group group
    String maintainNonGroup();
}
