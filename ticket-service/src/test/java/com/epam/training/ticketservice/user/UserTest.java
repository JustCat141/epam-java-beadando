package com.epam.training.ticketservice.user;

import com.epam.training.ticketservice.core.user.persistence.User;
import com.epam.training.ticketservice.core.user.persistence.UserRole;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTest {

    @Test
    void testUserShouldReturnDto() {
        var user = new User("asd","asd", UserRole.USER);

        var userDto = user.asDto();

        assertEquals(userDto.username(),user.getUsername());
        assertEquals(userDto.role(),user.getRole());
    }
}
