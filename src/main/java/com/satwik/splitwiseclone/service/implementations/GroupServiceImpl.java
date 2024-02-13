package com.satwik.splitwiseclone.service.implementations;

import com.satwik.splitwiseclone.persistence.dto.GroupDTO;
import com.satwik.splitwiseclone.persistence.models.Group;
import com.satwik.splitwiseclone.persistence.models.User;
import com.satwik.splitwiseclone.repository.GroupRepository;
import com.satwik.splitwiseclone.repository.UserRepository;
import com.satwik.splitwiseclone.service.interfaces.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    GroupRepository groupRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public String createGroup(GroupDTO groupDTO, int userId) {

        // fetch the user first
        Optional<User> user = userRepository.findById(userId);

        // TODO : create user not found exception :: if user==null
        if(!user.isPresent()) return "%d user not found. Hence group can't be created.".formatted(userId);

        // create the group
        Group group = new Group();
        group.setGroupName(groupDTO.getGroupName());
        group.setUser(user.get());

        // save the group
        groupRepository.save(group);

        return "Group created successfully!";
    }

    @Override
    public List<GroupDTO> findAllGroup(int userId) {

        groupRepository.findByUserId(userId);

    }

    @Override
    public GroupDTO findGroupById(int groupId, int userId) {
        return null;
    }

    @Override
    public String deleteGroupById(int groupId, int userId) {
        return null;
    }

    @Override
    public String maintainNonGroup() {
        return null;
    }
}
