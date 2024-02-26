package com.satwik.splitwiseclone.service.implementations;

import com.satwik.splitwiseclone.persistence.dto.expense.ExpenseListDTO;
import com.satwik.splitwiseclone.persistence.dto.group.GroupDTO;
import com.satwik.splitwiseclone.persistence.dto.group.GroupListDTO;
import com.satwik.splitwiseclone.persistence.dto.group.GroupListDTOWithin;
import com.satwik.splitwiseclone.persistence.dto.group.GroupUpdateRequest;
import com.satwik.splitwiseclone.persistence.models.Expense;
import com.satwik.splitwiseclone.persistence.models.Group;
import com.satwik.splitwiseclone.persistence.models.User;
import com.satwik.splitwiseclone.repository.ExpenseRepository;
import com.satwik.splitwiseclone.repository.GroupRepository;
import com.satwik.splitwiseclone.repository.UserRepository;
import com.satwik.splitwiseclone.service.interfaces.GroupService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ExpenseRepository expenseRepository;

    @Override
    @Transactional
    public String createGroup(GroupDTO groupDTO, UUID userId) throws AccessDeniedException {

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        // create the group
        Group group = new Group();
        group.setGroupName(groupDTO.getGroupName());
        group.setUser(user);
        group.setDefaultGroup(false);

        // save the group
        groupRepository.save(group);

        return "Group created successfully!";
    }

    @Override
    public GroupListDTO findAllGroup(UUID userId) throws AccessDeniedException {

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        List<Group> groupList = groupRepository.findByUserId(user.getId());

        GroupListDTO groupListDTO = new GroupListDTO();
        groupListDTO.setOwner(user.getUsername());

        List<GroupListDTOWithin> groupListDTOS = new ArrayList<>();

        // mapping each group list to group dto list
        for (Group group : groupList) {
            groupListDTOS.add(new GroupListDTOWithin(group.getId(), group.getGroupName()));
        }
        groupListDTO.setGroups(groupListDTOS);

        return groupListDTO;
    }

    @Override
    @Transactional
    public String deleteGroupByGroupId(UUID groupId, UUID userId) throws AccessDeniedException {

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new RuntimeException("Group not found"));

        if (user == null || user.getId() != group.getUser().getId()) throw new AccessDeniedException("Access Denied");

        // TODO : add code to check the default group (default group can't be deleted)
        groupRepository.deleteById(groupId);
        return "Successfully deleted the group - %d.".formatted(groupId);

    }

    @Override
    public GroupDTO findGroupByGroupId(UUID groupId, UUID userId) throws AccessDeniedException {

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new RuntimeException("Group not found"));

        if(user == null || user.getId() != group.getUser().getId()) throw new AccessDeniedException("Access Denied");

        GroupDTO groupDTO = new GroupDTO();

        groupDTO.setGroupId(group.getId());
        groupDTO.setGroupName(group.getGroupName());
        groupDTO.setOwner(group.getUser().getUsername());

        List<Expense> expenseList = expenseRepository.findByGroupId(groupId);
        List<ExpenseListDTO> expenseDTOList = new ArrayList<>();

        for (Expense expense : expenseList) {

            // mapping expense to expense DTO
            ExpenseListDTO expenseListDTO = new ExpenseListDTO();
            expenseListDTO.setAmount(expense.getAmount());
            expenseListDTO.setDescription(expense.getDescription());
            expenseListDTO.setExpenseCreatedAt(String.valueOf(expense.getCreatedOn()));
            expenseDTOList.add(expenseListDTO);
        }
        groupDTO.setExpenses(expenseDTOList);

        return groupDTO;
    }

    @Override
    @Transactional
    public String updateGroup(GroupUpdateRequest groupUpdateRequest, UUID groupId, UUID userId) throws AccessDeniedException {

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new RuntimeException("Group not found"));

        if(user == null || user.getId() != group.getUser().getId()) throw new AccessDeniedException("Access Denied");

        group.setGroupName(groupUpdateRequest.getGroupName());
        groupRepository.save(group);

        return "%d - Group update successfully!".formatted(group.getId());
    }
}
