package com.satwik.splitwiseclone.service.interfaces;

import com.satwik.splitwiseclone.persistence.dto.group.GroupDTO;
import com.satwik.splitwiseclone.persistence.dto.group.GroupListDTO;
import com.satwik.splitwiseclone.persistence.dto.group.GroupUpdateRequest;
import com.satwik.splitwiseclone.persistence.dto.user.UserDTO;

import java.util.List;
import java.util.UUID;

public interface GroupService {
    String createGroup(GroupDTO groupDTO) throws Exception;

    String deleteGroupByGroupId(UUID groupId) throws Exception;

    GroupDTO findGroupByGroupId(UUID groupId) throws Exception;

    String updateGroup(GroupUpdateRequest groupUpdateRequest, UUID groupId) throws Exception;

    GroupListDTO findAllGroup() throws Exception;

    String addGroupMembers(UUID groupId, UUID memberId) throws Exception;

    List<UserDTO> findMembers(UUID groupId) throws Exception;

    String deleteMembers(UUID groupId, UUID groupMemberId) throws Exception;
}
