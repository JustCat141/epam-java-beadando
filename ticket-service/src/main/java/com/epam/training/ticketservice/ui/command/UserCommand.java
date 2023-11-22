package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

@ShellComponent
@AllArgsConstructor
public class UserCommand {
    private final UserService userService;

    @ShellMethod(key = "sign in privileged", value = "User login")
    public String login(String username, String password) {
        return userService.login(username,password)
                .map(userDto -> userDto.username() + " logged in successfully!")
                .orElse("Login failed due to incorrect credentials");
    }
}
