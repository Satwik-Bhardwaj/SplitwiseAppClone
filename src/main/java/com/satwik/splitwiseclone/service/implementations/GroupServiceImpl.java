package com.satwik.splitwiseclone.service.implementations;

import com.satwik.splitwiseclone.persistence.dto.ExpenseDTO;
import com.satwik.splitwiseclone.persistence.dto.GroupDTO;
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
        group.setGroupName(groupDTO.getGroup_name());
        group.setUser(user.get());

        // save the group
        groupRepository.save(group);

        return "Group created successfully!";
    }

    @Override
    public List<GroupDTO> findAllGroup(int userId) {

        return null;

    }

    @Override
    @Transactional
    public String deleteGroupById(int groupId) {

        groupRepository.deleteById(groupId);
        return "Successfully deleted the group - %d.".formatted(groupId);

    }

    @Override
    public GroupDTO findGroupById(int groupId) {

        Group group = null;
        if(groupRepository.findById(groupId).isPresent())
            group = groupRepository.findById(groupId).get();

        GroupDTO resultDTO = new GroupDTO();
        resultDTO.setGroup_name(group.getGroupName());
        resultDTO.setOwner(group.getUser().getUsername());

        List<Expense> expenseList = expenseRepository.findByGroupId(groupId);
        List<ExpenseDTO> expenseDTOList = new ArrayList<>();
        for (Expense expense : expenseList) {
            // mapping expense to expense DTO

            ExpenseDTO expenseDTO = new ExpenseDTO();

            expenseDTO.setPayer_name(expense.getUser().getUsername());
            expenseDTO.setAmount(expense.getAmount());
            expenseDTO.setDescription(expense.getDescription());
            expenseDTO.setDate(expense.getDate());
            // TODO : add payees DTO
            expenseDTOList.add(expenseDTO);
        }

        resultDTO.setExpenses(expenseDTOList);

        return resultDTO;
    }

    @Override
    @Transactional
    public String updateGroup(GroupDTO groupDTO, int groupId) {
        return null;
    }
}
