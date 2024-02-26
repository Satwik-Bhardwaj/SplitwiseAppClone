package com.satwik.splitwiseclone.controller;

import com.satwik.splitwiseclone.persistence.dto.group.GroupDTO;
import com.satwik.splitwiseclone.persistence.dto.group.GroupListDTO;
import com.satwik.splitwiseclone.persistence.dto.group.GroupUpdateRequest;
import com.satwik.splitwiseclone.service.interfaces.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/group")
public class GroupController {

    @Autowired
    GroupService groupService;

    // create a group for a user
    @PostMapping("/create")
    public ResponseEntity<String> createGroup(@RequestBody GroupDTO groupDTO) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID userId = UUID.fromString(authentication.getName());

        try {
            return ResponseEntity.status(HttpStatus.OK).body(groupService.createGroup(groupDTO, userId));
        } catch (AccessDeniedException e) {
            throw new RuntimeException(e);
        }
    }

    // delete a group in a user account
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteGroup(@RequestParam UUID groupId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID userId = UUID.fromString(authentication.getName());

        try {
            return ResponseEntity.ok(groupService.deleteGroupByGroupId(groupId, userId));
        } catch (AccessDeniedException e) {
            throw new RuntimeException(e);
        }
    }

    // get a group in a user account
    @GetMapping("/{groupId}")
    public ResponseEntity<GroupDTO> findGroup(@PathVariable UUID groupId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID userId = UUID.fromString(authentication.getName());

        GroupDTO groupDTO = null;
        try {
            groupDTO = groupService.findGroupByGroupId(groupId, userId);
        } catch (AccessDeniedException e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.status(HttpStatus.FOUND).body(groupDTO);
    }

    // update a group in a user account
    @PutMapping("/update/{groupId}")
    public ResponseEntity<String> updateGroup(@RequestBody GroupUpdateRequest groupUpdateRequest, @PathVariable UUID groupId) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID userId = UUID.fromString(authentication.getName());

        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(groupService.updateGroup(groupUpdateRequest, groupId, userId));
        } catch (AccessDeniedException e) {
            throw new RuntimeException(e);
        }
    }

    // get all the groups of the user
    @GetMapping("/user")
    public ResponseEntity<GroupListDTO> findAllGroup() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UUID userId = UUID.fromString(authentication.getName());

        try {
            return ResponseEntity.status(HttpStatus.FOUND).body(groupService.findAllGroup(userId));
        } catch (AccessDeniedException e) {
            throw new RuntimeException(e);
        }
    }

}
