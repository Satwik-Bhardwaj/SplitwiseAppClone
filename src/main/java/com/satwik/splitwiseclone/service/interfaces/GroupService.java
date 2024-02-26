package com.satwik.splitwiseclone.service.interfaces;

import com.satwik.splitwiseclone.persistence.dto.group.GroupDTO;
import com.satwik.splitwiseclone.persistence.dto.group.GroupListDTO;
import com.satwik.splitwiseclone.persistence.dto.group.GroupUpdateRequest;

import java.nio.file.AccessDeniedException;
import java.util.UUID;

public interface GroupService {
    String createGroup(GroupDTO groupDTO, UUID userId) throws AccessDeniedException;

    String deleteGroupByGroupId(UUID groupId, UUID userId) throws AccessDeniedException;

    GroupDTO findGroupByGroupId(UUID groupId, UUID userId) throws AccessDeniedException;

    String updateGroup(GroupUpdateRequest groupUpdateRequest, UUID groupId, UUID userId) throws AccessDeniedException;

    GroupListDTO findAllGroup(UUID userId) throws AccessDeniedException;

}
