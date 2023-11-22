package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.user.UserService;
import com.epam.training.ticketservice.core.user.model.UserDto;
import com.epam.training.ticketservice.core.user.persistence.User;
import com.epam.training.ticketservice.core.user.persistence.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.util.Optional;

@ShellComponent
@AllArgsConstructor
public class UserCommand {
    private final UserService userService;
    //private final AdminCommandAvailability adminCommandAvailability;

    //@ShellMethodAvailability("isAvailableCommand")
    @ShellMethod(key = "sign in privileged", value = "User login")
    public String login(String username, String password) {
        return userService.login(username,password)
                .map(userDto -> "Signed in with privileged account " + userDto.username())
                .orElse("Login failed due to incorrect credentials");
    }

    //@ShellMethodAvailability("isAvailableCommand")
    @ShellMethod(key = "sign out", value = "User logout")
    public String logout() {
        return userService.logout()
                .map(userDto -> userDto.username() + " has logged out!")
                .orElse("You are not signed in");
    }

    @ShellMethod(key = "describe account", value = "Prints info of current account")
    public String describe() {
        return userService.describe()
                .map(userDto -> "Signed in with privileged account " + userDto.username())
                .orElse("You are not signed in");
    }

    private Availability isAvailableCommand() {
        Optional<UserDto> user = userService.describe();
        return user.isPresent() && user.get().role() == UserRole.ADMIN
                ? Availability.available()
                : Availability.unavailable("You are not an admin!");
    }
}