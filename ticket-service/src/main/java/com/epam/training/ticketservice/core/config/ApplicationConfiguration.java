package com.epam.training.ticketservice.core.config;

import com.epam.training.ticketservice.core.user.UserService;
import com.epam.training.ticketservice.ui.command.AdminCommandAvailability;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {
    @Bean
    public AdminCommandAvailability adminCommandAvailability(UserService userService) {
        return new AdminCommandAvailability(userService);
    }
}
