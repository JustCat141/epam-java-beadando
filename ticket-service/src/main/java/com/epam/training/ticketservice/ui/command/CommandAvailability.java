package com.epam.training.ticketservice.ui.command;

import com.epam.training.ticketservice.core.user.UserService;
import com.epam.training.ticketservice.core.user.persistence.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.Availability;

public abstract class CommandAvailability {
    @Autowired
    private UserService userService;

    protected Availability isAdmin() {
        var user = userService.describe();
        return user.isPresent() && user.get().role().equals(UserRole.ADMIN)
                ? Availability.available()
                : Availability.unavailable("You must be an administrator to execute this command!");
    }

    private Availability isLoggedIn() {
        var user = userService.describe();
        return user.isPresent()
                ? Availability.available()
                : Availability.unavailable("You must be logged in to execute this command!");
    }
}
