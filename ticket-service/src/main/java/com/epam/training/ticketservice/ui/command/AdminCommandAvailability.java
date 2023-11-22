package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.user.UserService;
import com.epam.training.ticketservice.core.user.persistence.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.Availability;

@AllArgsConstructor
public class AdminCommandAvailability {
    private final UserService userService;

    private Availability isAvailableCommand() {
        var user = userService.describe();
        return user.isPresent() && user.get().role().equals(UserRole.ADMIN)
                ? Availability.available()
                : Availability.unavailable("You must be an administrator to execute this command!");
    }
}
