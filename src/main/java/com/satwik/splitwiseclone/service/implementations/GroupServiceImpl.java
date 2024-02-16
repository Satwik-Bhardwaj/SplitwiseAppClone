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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public String createGroup(GroupDTO groupDTO, int userId) {

        // fetch the user first
        Optional<User> user = userRepository.findById(userId);

        // TODO : create user not found exception :: if user==null
        if(!user.isPresent()) return "%d user not found. Hence group can't be created.".formatted(userId);

        // create the group
        Group group = new Group();
        group.setGroupName(groupDTO.getGroupName());
        group.setUser(user.get());
        group.setDefaultGroup(false);

        // save the group
        groupRepository.save(group);

        return "Group created successfully!";
    }

    @Override
    public GroupListDTO findAllGroup(int userId) {

        List<Group> groupList = groupRepository.findByUserId(userId);

        Optional<User> user = userRepository.findById(userId);

        // TODO : add exception for user not found
        if (!user.isPresent()) return null;

        GroupListDTO groupListDTO = new GroupListDTO();
        groupListDTO.setOwner(user.get().getUsername());

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
    public String deleteGroupByGroupId(int groupId) {

        // TODO : add code to check the default group (default group can't be deleted)
        groupRepository.deleteById(groupId);
        return "Successfully deleted the group - %d.".formatted(groupId);

    }

    @Override
    public GroupDTO findGroupByGroupId(int groupId) {

        Group group = null;
        if(groupRepository.findById(groupId).isPresent())
            group = groupRepository.findById(groupId).get();

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
            expenseListDTO.setDescription(expenseListDTO.getDescription());
            expenseListDTO.setExpenseCreatedAt(expense.getDate());

            expenseDTOList.add(expenseListDTO);
        }

        groupDTO.setExpenses(expenseDTOList);

        return groupDTO;
    }

    @Override
    @Transactional
    public String updateGroup(GroupUpdateRequest groupUpdateRequest, int groupId) {

        Optional<Group> group = groupRepository.findById(groupId);

        if (!group.isPresent())
            return "Group is not present so can't be updated.";

        Group fetchedGroup = group.get();

        fetchedGroup.setGroupName(groupUpdateRequest.getGroupName());

        groupRepository.save(fetchedGroup);

        return "%d - Group update successfully!".formatted(fetchedGroup.getId());
    }
}
