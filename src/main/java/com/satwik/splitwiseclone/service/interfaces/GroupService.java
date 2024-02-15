package com.satwik.splitwiseclone.service.interfaces;

import com.satwik.splitwiseclone.persistence.dto.group.GroupDTO;
import com.satwik.splitwiseclone.persistence.dto.group.GroupListDTO;
import com.satwik.splitwiseclone.persistence.dto.group.GroupUpdateRequest;

public interface GroupService {
    String createGroup(GroupDTO groupDTO, int userId);

    String deleteGroupByGroupId(int groupId);

    GroupDTO findGroupByGroupId(int groupId);

    String updateGroup(GroupUpdateRequest groupUpdateRequest, int groupId);

    GroupListDTO findAllGroup(int userId);

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
