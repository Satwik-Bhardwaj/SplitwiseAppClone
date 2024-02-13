package com.satwik.splitwiseclone.controller;

import com.satwik.splitwiseclone.persistence.dto.GroupDTO;
import com.satwik.splitwiseclone.service.interfaces.GroupService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/group")
public class GroupController {

    GroupService groupService;

    // create a group for a user
    @PostMapping("/create/{userId}")
    public ResponseEntity<String> createGroup(@PathVariable int userId, @RequestBody GroupDTO groupDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(groupService.createGroup(groupDTO, userId));
    }

    // delete a group in a user account
    @DeleteMapping("/delete/{}")
    public ResponseEntity<String> deleteGroup(@PathVariable int userId) {
        return ResponseEntity.ok(groupService.deleteGroupById())
    }

}
