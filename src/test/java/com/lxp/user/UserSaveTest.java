package com.lxp.user;

import com.lxp.user.dao.UserDao;
import com.lxp.user.model.User;
import com.lxp.user.presentation.controller.request.UserSaveRequest;
import com.lxp.user.presentation.controller.response.UserSaveResponse;
import com.lxp.user.security.PasswordEncoder;
import com.lxp.user.service.UserService;
import com.lxp.user.service.validator.UserValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserSaveTest {

    @Mock
    private UserDao mockUserDao;
    @Mock
    private PasswordEncoder mockEncoder;
    @Mock
    private UserValidator validator;
    @InjectMocks
    private UserService userService;

    @Test
    public void 회원가입_성공_로직() {
        String username = "test";
        String email = "test@test.com";
        String password = "test123@1A";
        String encode = mockEncoder.encode(password);

        UserSaveRequest request = new UserSaveRequest(username, email, password);

        when(mockEncoder.encode(eq(password))).thenReturn(encode);

        UserSaveResponse response = userService.register(request.to());

        verify(mockUserDao, times(1)).save(any(User.class));
        assertAll(
            () -> assertThat(response.email()).isEqualTo(email),
            () -> assertThat(response.name()).isEqualTo(username)
        );
    }

}
