package com.satwik.splitwiseclone.controller;

import com.satwik.splitwiseclone.persistence.dto.group.GroupDTO;
import com.satwik.splitwiseclone.persistence.dto.group.GroupListDTO;
import com.satwik.splitwiseclone.persistence.dto.group.GroupUpdateRequest;
import com.satwik.splitwiseclone.persistence.dto.user.UserDTO;
import com.satwik.splitwiseclone.service.interfaces.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/group")
public class GroupController {

    @Autowired
    GroupService groupService;

    // create a group for a user
    @PostMapping("/create")
    public ResponseEntity<String> createGroup(@RequestBody GroupDTO groupDTO) {

        try {
            return ResponseEntity.status(HttpStatus.OK).body(groupService.createGroup(groupDTO));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // delete a group in a user account
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteGroup(@RequestParam UUID groupId) {

        try {
            return ResponseEntity.ok(groupService.deleteGroupByGroupId(groupId));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // get a group in a user account
    @GetMapping("/{groupId}")
    public ResponseEntity<GroupDTO> findGroup(@PathVariable UUID groupId) {

        GroupDTO groupDTO;
        try {
            groupDTO = groupService.findGroupByGroupId(groupId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return ResponseEntity.status(HttpStatus.FOUND).body(groupDTO);
    }

    // update a group in a user account
    @PutMapping("/update/{groupId}")
    public ResponseEntity<String> updateGroup(@RequestBody GroupUpdateRequest groupUpdateRequest, @PathVariable UUID groupId) {

        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(groupService.updateGroup(groupUpdateRequest, groupId));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // get all the groups of the user
    @GetMapping("/user")
    public ResponseEntity<GroupListDTO> findAllGroup() {

        try {
            return ResponseEntity.status(HttpStatus.FOUND).body(groupService.findAllGroup());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // add members to the group
    @PostMapping("/add-member/{groupId}")
    public ResponseEntity<String> addGroupMembers(@PathVariable UUID groupId, @RequestParam UUID memberId) {

        try {
            return ResponseEntity.status(HttpStatus.OK).body(groupService.addGroupMembers(groupId, memberId));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // get all the members of the group
    @GetMapping("/{groupId}/members")
    public ResponseEntity<List<UserDTO>> findMembers(@PathVariable UUID groupId) {

        try {
            return ResponseEntity.status(HttpStatus.OK).body(groupService.findMembers(groupId));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // remove all the members of the group
    @DeleteMapping("/remove-member/{groupMemberId}")
    public ResponseEntity<String> deleteMembers(@PathVariable UUID groupMemberId, @RequestParam UUID groupId) {

        try {
            return ResponseEntity.status(HttpStatus.OK).body(groupService.deleteMembers(groupId, groupMemberId));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
