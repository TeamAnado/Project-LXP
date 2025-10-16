package user;

import com.lxp.user.User;
import com.lxp.user.dao.UserDao;
import com.lxp.user.dto.UserSaveRequest;
import com.lxp.user.dto.UserSaveResponse;
import com.lxp.user.service.UserService;
import com.lxp.user.validator.PasswordEncoder;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class UserSaveTest {

    private UserDao mockUserDao = mock(UserDao.class);
    private PasswordEncoder passwordEncoder = new PasswordEncoder();
    private UserService userService = new UserService(passwordEncoder, mockUserDao);

    @Test
    public void 회원가입_성공_로직() {
        String username = "test";
        String email = "test@test.com";
        String password = "test123@1";

        UserSaveRequest request = new UserSaveRequest(username, email, password);
        UserSaveResponse response = userService.register(request);

        verify(mockUserDao, times(1)).save(any(User.class));

        assertAll(
            () -> assertThat(response.email()).isEqualTo(email),
            () -> assertThat(response.name()).isEqualTo(username)
        );
    }
}
