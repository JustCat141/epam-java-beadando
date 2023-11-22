package com.epam.training.ticketservice.core.user;

import com.epam.training.ticketservice.core.user.model.UserDto;
import com.epam.training.ticketservice.core.user.persistence.User;
import com.epam.training.ticketservice.core.user.persistence.UserRepository;
import com.epam.training.ticketservice.core.user.persistence.UserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private UserDto loggedInUser = null;

    @Override
    public Optional<UserDto> login(String username, String password) {
        Optional<User> user = userRepository.findByUsernameAndPassword(username,password);

        if (user.isEmpty()) {
            return Optional.empty();
        }
        loggedInUser = new UserDto(user.get().getUsername(),user.get().getRole());
        return describe();
    }

    @Override
    public Optional<UserDto> logout() {
        Optional<UserDto> prevLoggedInUser = describe();
        loggedInUser = null;
        return prevLoggedInUser;
    }

    @Override
    public Optional<UserDto> describe() {
        return Optional.ofNullable(loggedInUser);
    }

    @Override
    public void registerUser(String username, String password) {
        var user = new User(username, password, UserRole.USER);
        userRepository.save(user);
    }
}
