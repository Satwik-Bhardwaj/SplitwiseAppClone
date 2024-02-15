package com.satwik.splitwiseclone.service.implementations;

import com.satwik.splitwiseclone.persistence.dto.user.PhoneDTO;
import com.satwik.splitwiseclone.persistence.dto.user.RegisterUserRequest;
import com.satwik.splitwiseclone.persistence.dto.user.UserDTO;
import com.satwik.splitwiseclone.persistence.models.Group;
import com.satwik.splitwiseclone.persistence.models.User;
import com.satwik.splitwiseclone.repository.GroupRepository;
import com.satwik.splitwiseclone.repository.UserRepository;
import com.satwik.splitwiseclone.service.interfaces.UserService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    GroupRepository groupRepository;

    // for save and update
    @Override
    @Transactional
    public String saveUser(RegisterUserRequest request) {

        User user = new User();

        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setCountryCode(request.getPhone().getCountryCode());
        user.setPhoneNumber(request.getPhone().getPhoneNumber());

        // TODO : encrypt the password after adding the security
        user.setPassword(request.getPassword());

        // saving the user and getting the saved entity
        user = userRepository.save(user);

        // creating the non group expenses group
        Group group = new Group();
        group.setGroupName("Non Grouped Expenses");
        group.setUser(user);

        // save the group
        groupRepository.save(group);

        return userRepository.save(user).toString();
    }

    @Override
    public UserDTO findUserById(int userId) {

        User user = null;
        if(userRepository.findById(userId).isPresent())
            user = userRepository.findById(userId).get();

        // TODO : if user==null return exception :: create new exception

        UserDTO userResult = new UserDTO(
                user.getUsername(),
                user.getEmail(),
                new PhoneDTO(user.getCountryCode(), user.getPhoneNumber())
        );

        return userResult;
    }


    @Override
    @Transactional
    public String deleteUser(int userId) {
        userRepository.deleteById(userId);

        return "%s - user deleted.".formatted(userId);
    }

    @Override
    @Transactional
    public String updateUser(int userId, RegisterUserRequest request) {

        Optional<User> user = userRepository.findById(userId);

        if(!user.isPresent())
            return "User doesn't exists so can't be update.";


        User fetchedUser = user.get();

        // setting user id separately

        fetchedUser.setUsername(request.getUsername());
        fetchedUser.setEmail(request.getEmail());
        fetchedUser.setCountryCode(request.getPhone().getCountryCode());
        fetchedUser.setPhoneNumber(request.getPhone().getPhoneNumber());

        // TODO : encrypt the password after adding the security
        fetchedUser.setPassword(request.getPassword());

        userRepository.save(fetchedUser);

        return request.getUsername() + " updated successfully.";
    }

}
