package com.satwik.splitwiseclone.service.interfaces;

import com.satwik.splitwiseclone.persistence.dto.ExpenseDTO;
import com.satwik.splitwiseclone.persistence.dto.GroupDTO;

import java.util.List;

public interface GroupService {
    String createGroup(GroupDTO groupDTO, int userId);

    String deleteGroupById(int groupId);

    GroupDTO findGroupById(int groupId);

    String updateGroup(GroupDTO groupDTO, int groupId);

    List<GroupDTO> findAllGroup(int userId);

//    // create new group
//    String createGroup(GroupDTO groupDTO, int userId);
//
//    // find all Group
//    List<GroupDTO> findAllGroup(int userId);
//
//    // find new Group
//    GroupDTO findGroupById(int groupId, int userId);
//
//    // delete a Group
//    String deleteGroupById(int groupId);
//
//    // maintain default non-group group
//    String maintainNonGroup();
}
