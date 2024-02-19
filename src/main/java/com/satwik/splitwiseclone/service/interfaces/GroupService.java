package com.satwik.splitwiseclone.service.interfaces;

import com.satwik.splitwiseclone.persistence.dto.group.GroupDTO;
import com.satwik.splitwiseclone.persistence.dto.group.GroupListDTO;
import com.satwik.splitwiseclone.persistence.dto.group.GroupUpdateRequest;

import java.nio.file.AccessDeniedException;

public interface GroupService {
    String createGroup(GroupDTO groupDTO, int userId) throws AccessDeniedException;

    String deleteGroupByGroupId(int groupId, int userId) throws AccessDeniedException;

    GroupDTO findGroupByGroupId(int groupId, int userId) throws AccessDeniedException;

    String updateGroup(GroupUpdateRequest groupUpdateRequest, int groupId, int userId) throws AccessDeniedException;

    GroupListDTO findAllGroup(int userId) throws AccessDeniedException;

}
