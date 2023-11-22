package com.epam.training.ticketservice.core.user.model;

import com.epam.training.ticketservice.core.user.persistence.UserRole;

public record UserDto(String username, UserRole role) { }
