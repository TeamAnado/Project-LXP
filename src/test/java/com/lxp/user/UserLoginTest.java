package com.lxp.user;

import com.lxp.user.dao.UserDao;
import com.lxp.user.dao.vo.UserAuthInfo;
import com.lxp.user.model.User;
import com.lxp.user.presentation.controller.request.UserLoginRequest;
import com.lxp.user.presentation.controller.response.UserResponse;
import com.lxp.user.security.PasswordEncoder;
import com.lxp.user.service.UserService;
import com.lxp.user.service.validator.UserValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserLoginTest {

    @Mock
    private UserDao mockUserDao;
    @Mock
    private PasswordEncoder mockEncoder;
    @Mock
    private UserValidator validator;
    @InjectMocks
    private UserService userService;

    @Test
    public void 로그인_성공_로직() {
        String username = "test";
        String email = "test@test.com";
        String password = "test123@1A";
        String encode = mockEncoder.encode(password);

        User user = new User(1L, username, email, encode, LocalDateTime.now(), LocalDateTime.now());
        UserAuthInfo authInfo = new UserAuthInfo(user.getId(), user.getEmail(), encode);

        doNothing().when(validator).validateEmail(anyString());
        when(mockUserDao.findByEmail(eq(email))).thenReturn(Optional.of(authInfo));

        UserLoginRequest request = new UserLoginRequest(email, password);
        UserResponse login = userService.login(request.to()).toResponse();

        verify(mockUserDao, times(1)).findByEmail(email);
        assertThat(login.id()).isEqualTo(user.getId());
    }

}
