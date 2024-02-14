package com.satwik.splitwiseclone.controller;

import com.satwik.splitwiseclone.persistence.dto.GroupDTO;
import com.satwik.splitwiseclone.service.interfaces.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/group")
public class GroupController {

    @Autowired
    GroupService groupService;

    // create a group for a user
    // TODO : user id might not required after implementing spring security
    @PostMapping("/create")
    public ResponseEntity<String> createGroup(@RequestParam int userId, @RequestBody GroupDTO groupDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(groupService.createGroup(groupDTO, userId));
    }

    // delete a group in a user account
    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteGroup(@RequestParam int groupId) {
        return ResponseEntity.ok(groupService.deleteGroupById(groupId));
    }

    // get a group in a user account
    @GetMapping("/{groupId}")
    public ResponseEntity<GroupDTO> findGroup(@RequestParam int groupId) {
        GroupDTO groupDTO = groupService.findGroupById(groupId);
        return ResponseEntity.status(HttpStatus.FOUND).body(groupDTO);
    }

    // update a group in a user account
    @PutMapping("/update/{groupId}")
    public ResponseEntity<String> updateGroup(@RequestBody GroupDTO groupDTO, @PathVariable int groupId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(groupService.updateGroup(groupDTO, groupId));
    }

    // get all the groups of the user
    @PutMapping("/user/{userId}")
    public ResponseEntity<List<GroupDTO>> findAllGroup(@PathVariable int userId) {
        return ResponseEntity.status(HttpStatus.FOUND).body(groupService.findAllGroup(userId));
    }

}
