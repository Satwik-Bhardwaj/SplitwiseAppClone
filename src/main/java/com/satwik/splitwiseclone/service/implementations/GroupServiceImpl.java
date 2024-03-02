package com.satwik.splitwiseclone.service.implementations;

import com.satwik.splitwiseclone.persistence.dto.expense.ExpenseListDTO;
import com.satwik.splitwiseclone.persistence.dto.group.*;
import com.satwik.splitwiseclone.persistence.dto.user.PhoneDTO;
import com.satwik.splitwiseclone.persistence.dto.user.UserDTO;
import com.satwik.splitwiseclone.persistence.models.Expense;
import com.satwik.splitwiseclone.persistence.models.Group;
import com.satwik.splitwiseclone.persistence.models.GroupMembers;
import com.satwik.splitwiseclone.persistence.models.User;
import com.satwik.splitwiseclone.repository.ExpenseRepository;
import com.satwik.splitwiseclone.repository.GroupMembersRepository;
import com.satwik.splitwiseclone.repository.GroupRepository;
import com.satwik.splitwiseclone.repository.UserRepository;
import com.satwik.splitwiseclone.service.interfaces.GroupService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private GroupMembersRepository groupMembersRepository;


    @Override
    @Transactional
    public String createGroup(GroupDTO groupDTO) {

        User user = authorizationService.getAuthorizedUser();

        Group group = new Group();
        group.setGroupName(groupDTO.getGroupName());
        group.setUser(user);
        group.setDefaultGroup(false);
        groupRepository.save(group);

        return "Group created successfully!";
    }

    @Override
    public GroupListDTO findAllGroup() {

        User user = authorizationService.getAuthorizedUser();
        List<Group> groupList = groupRepository.findByUserId(user.getId());
        GroupListDTO groupListDTO = new GroupListDTO();
        groupListDTO.setOwner(user.getUsername());

        List<GroupListDTOWithin> groupListDTOS = new ArrayList<>();
        for (Group group : groupList) {
            groupListDTOS.add(new GroupListDTOWithin(group.getId(), group.getGroupName()));
        }
        groupListDTO.setGroups(groupListDTOS);

        return groupListDTO;
    }

    @Transactional
    @Override
    public String addGroupMembers(UUID groupId, UUID memberId) throws Exception {

        authorizationService.checkAuthorizationOnGroup(groupId);

        Group group = groupRepository.findById(groupId).orElseThrow(() -> new RuntimeException("Group not found!"));
        User member = userRepository.findById(memberId).orElseThrow(() -> new RuntimeException("User not found to add as member!"));
        GroupMembers groupMembers = new GroupMembers();
        groupMembers.setMember(member);
        groupMembers.setGroup(group);
        groupMembersRepository.save(groupMembers);

        return "User - " + memberId + " successfully added as member of the group.";
    }

    @Override
    public List<UserDTO> findMembers(UUID groupId) throws Exception {

        authorizationService.checkAuthorizationOnGroup(groupId);

        Group group = groupRepository.findById(groupId).orElseThrow(() -> new RuntimeException("Group not found!"));
        List<GroupMembers> groupMembersList = groupMembersRepository.findByGroupId(group.getId());
        List<UserDTO> userDTOS = new ArrayList<>();
        for (GroupMembers groupMembers : groupMembersList) {
            UserDTO userDTO = new UserDTO();
            userDTO.setEmail(groupMembers.getMember().getEmail());
            userDTO.setUsername(groupMembers.getMember().getUsername());
            userDTO.setPhone(new PhoneDTO(groupMembers.getMember().getCountryCode(), groupMembers.getMember().getPhoneNumber()));
            userDTOS.add(userDTO);
        }

        return userDTOS;
    }

    @Transactional
    @Override
    public String deleteMembers(UUID groupId, UUID groupMemberId) throws Exception {

        authorizationService.checkAuthorizationOnGroup(groupId);

        groupMembersRepository.deleteById(groupMemberId);

        return "Member successfully removed from the group!";
    }

    @Override
    @Transactional
    public String deleteGroupByGroupId(UUID groupId) throws Exception {
        
        authorizationService.checkAuthorizationOnGroup(groupId);
        
        // TODO : add code to check the default group (default group can't be deleted)
        groupRepository.deleteById(groupId);
        return "Successfully deleted the group - %s.".formatted(groupId);

    }

    @Override
    public GroupDTO findGroupByGroupId(UUID groupId) throws Exception {

        Group group = authorizationService.checkAuthorizationOnGroup(groupId);
        GroupDTO groupDTO = new GroupDTO();
        groupDTO.setGroupId(group.getId());
        groupDTO.setGroupName(group.getGroupName());
        groupDTO.setOwner(group.getUser().getUsername());

        List<Expense> expenseList = expenseRepository.findByGroupId(groupId);
        List<ExpenseListDTO> expenseDTOList = new ArrayList<>();
        for (Expense expense : expenseList) {
            ExpenseListDTO expenseListDTO = new ExpenseListDTO();
            expenseListDTO.setAmount(expense.getAmount());
            expenseListDTO.setDescription(expense.getDescription());
            expenseListDTO.setExpenseCreatedAt(String.valueOf(expense.getCreatedOn()));
            expenseDTOList.add(expenseListDTO);
        }
        groupDTO.setExpenses(expenseDTOList);

        List<GroupMemberDTO> groupMemberDTOS = getGroupMemberDTOS(group);
        groupDTO.setGroupMembers(groupMemberDTOS);

        return groupDTO;
    }

    private static List<GroupMemberDTO> getGroupMemberDTOS(Group group) {
        List<GroupMembers> groupMembers = group.getGroupMembers();
        List<GroupMemberDTO> groupMemberDTOS = new ArrayList<>();
        for (GroupMembers groupMember : groupMembers) {
            User user1 = groupMember.getMember();
            GroupMemberDTO groupMemberDTO = new GroupMemberDTO();
            groupMemberDTO.setGroupMemberId(groupMember.getId());
            groupMemberDTO.setMemberId(user1.getId());
            groupMemberDTO.setEmail(user1.getEmail());
            groupMemberDTO.setUsername(user1.getUsername());
            groupMemberDTOS.add(groupMemberDTO);
        }
        return groupMemberDTOS;
    }

    @Override
    @Transactional
    public String updateGroup(GroupUpdateRequest groupUpdateRequest, UUID groupId) throws Exception {

        authorizationService.checkAuthorizationOnGroup(groupId);
        
        Group group = groupRepository.findById(groupId).orElseThrow(() -> new RuntimeException("Group not found"));
        group.setGroupName(groupUpdateRequest.getGroupName());
        groupRepository.save(group);

        return "%s - Group update successfully!".formatted(group.getId());
    }
}
