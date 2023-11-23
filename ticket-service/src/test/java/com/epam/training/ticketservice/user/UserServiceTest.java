package com.epam.training.ticketservice.user;

import com.epam.training.ticketservice.core.user.UserService;
import com.epam.training.ticketservice.core.user.UserServiceImpl;
import com.epam.training.ticketservice.core.user.model.UserDto;
import com.epam.training.ticketservice.core.user.persistence.User;
import com.epam.training.ticketservice.core.user.persistence.UserRepository;
import com.epam.training.ticketservice.core.user.persistence.UserRole;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    @Mock
    private final UserRepository userRepository = mock(UserRepository.class);
    private final UserService underTest = new UserServiceImpl(userRepository);

    @Test
    public void testLoginShouldReturnUserDtoWhenCredentialsAreCorrect() {
        // Given
        String username = "user";
        String password = "password";
        User mockUser = new User("user", "password", UserRole.USER);
        when(userRepository.findByUsernameAndPassword(username, password)).thenReturn(Optional.of(mockUser));

        // When
        Optional<UserDto> result = underTest.login(username, password);

        // Then
        assertTrue(result.isPresent());
        assertEquals(username, result.get().username());
        assertEquals(UserRole.USER, result.get().role());
    }

    @Test
    public void testLoginShouldReturnEmptyOptionalWhenCredentialsAreIncorrect() {
        // Arrange
        String username = "user";
        String password = "wrongPassword";
        when(userRepository.findByUsernameAndPassword(username, password)).thenReturn(Optional.empty());

        // Act
        Optional<UserDto> result = underTest.login(username, password);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    public void testLogoutShouldReturnPreviousLoggedInUserWhenThereIsALoggedInUse() {
        // Arrange
        User user = new User("user","password",UserRole.USER);
        when(userRepository.findByUsernameAndPassword(anyString(), anyString())).thenReturn(Optional.empty());
        var expected = underTest.login("user", "password"); // Log in a user

        // Act
        Optional<UserDto> result = underTest.logout();

        // Assert
        assertEquals(expected, result);
    }

    @Test
    public void testLogoutShouldReturnEmptyOptionalWhenNotLoggedIn() {
        // Act
        Optional<UserDto> result = underTest.logout();

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    public void testDescribeShouldReturnLoggedInUserWhenThereIsALoggedInUser() {
        // Arrange
        User user = new User("user","password",UserRole.USER);
        when(userRepository.findByUsernameAndPassword(anyString(), anyString())).thenReturn(Optional.of(user));
        var expected = underTest.login("user", "password"); // Log in a user

        // Act
        Optional<UserDto> result = underTest.describe();

        // Assert
        assertTrue(result.isPresent());
        assertEquals(expected, result);
    }

    @Test
    public void testDescribeShouldReturnEmptyOptionalWhenNotLoggedIn() {
        // Act
        Optional<UserDto> result = underTest.describe();

        // Assert
        assertTrue(result.isEmpty());
    }
}
