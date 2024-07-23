package com.satwik.splitwiseclone.controller;

import com.satwik.splitwiseclone.configuration.security.LoggedInUser;
import com.satwik.splitwiseclone.persistence.dto.group.GroupDTO;
import com.satwik.splitwiseclone.persistence.dto.group.GroupListDTO;
import com.satwik.splitwiseclone.persistence.dto.group.GroupUpdateRequest;
import com.satwik.splitwiseclone.persistence.dto.user.UserDTO;
import com.satwik.splitwiseclone.service.interfaces.GroupService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api/v1/group")
public class GroupController {

    @Autowired
    GroupService groupService;

    @Autowired
    LoggedInUser loggedInUser;

    // create a group for a user
    @PostMapping("/create")
    public ResponseEntity<String> createGroup(@Valid @RequestBody GroupDTO groupDTO) {
        log.info("Post Endpoint: create group request: {}", groupDTO);
        String response = groupService.createGroup(groupDTO);
        log.info("Post Endpoint: create group response: {}", response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // delete a group in a user account
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteGroup(@RequestParam UUID groupId) {
        log.info("Delete Endpoint: delete group with id: {}", groupId);
        String response = groupService.deleteGroupByGroupId(groupId);
        log.info("Delete Endpoint: delete group response: {}", response);
        return ResponseEntity.ok(response);
    }

    // get a group in a user account
    @GetMapping("/{groupId}")
    public ResponseEntity<GroupDTO> findGroup(@PathVariable UUID groupId) {
        log.info("Get Endpoint: find group with id: {}", groupId);
        GroupDTO groupDTO = groupService.findGroupByGroupId(groupId);
        log.info("Get Endpoint: find group response: {}", groupDTO);
        return ResponseEntity.status(HttpStatus.OK).body(groupDTO);
    }

    // update a group in a user account
    @PutMapping("/update/{groupId}")
    public ResponseEntity<String> updateGroup(@RequestBody GroupUpdateRequest groupUpdateRequest, @PathVariable UUID groupId) {
        log.info("Put Endpoint: update group with request: {} and request: {}", groupUpdateRequest, groupId);
        String response = groupService.updateGroup(groupUpdateRequest, groupId);
        log.info("Put Endpoint: update group with response: {}", response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // get all the groups of the user
    @GetMapping("")
    public ResponseEntity<GroupListDTO> findAllGroup() {
        log.info("Get Endpoint: find all group of the user");
        GroupListDTO response = groupService.findAllGroup();
        log.info("Get Endpoint: find all group of the user with response: {}", response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // add members to the group
    @PostMapping("/add-member/{groupId}")
    public ResponseEntity<String> addGroupMembers(@PathVariable UUID groupId, @RequestParam UUID memberId) {
        log.info("Post Endpoint: add member with memberId: {} to the group with groupId: {}", memberId, groupId);
        String response = groupService.addGroupMembers(groupId, memberId);
        log.info("Post Endpoint: add member with response: {}", response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // get all the members of the group
    @GetMapping("/{groupId}/members")
    public ResponseEntity<List<UserDTO>> findMembers(@PathVariable UUID groupId) {
        log.info("Get Endpoint: find members of group with groupId: {}", groupId);
        List<UserDTO> response = groupService.findMembers(groupId);
        log.info("Get Endpoint: find members of group with response: {}", response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // remove all the members of the group
    @DeleteMapping("/remove-member/{groupMemberId}")
    public ResponseEntity<String> deleteMembers(@PathVariable UUID groupMemberId, @RequestParam UUID groupId) {
        log.info("Delete Endpoint: delete a member with memberId: {} and groupId: {}", groupMemberId, groupId);
        String response = groupService.deleteMembers(groupId, groupMemberId);
        log.info("Delete Endpoint: delete a member with response: {}", response);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
