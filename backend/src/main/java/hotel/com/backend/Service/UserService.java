package hotel.com.backend.Service;

import hotel.com.backend.Models.Users;
import hotel.com.backend.Models.Request.UserSignIn;
import hotel.com.backend.Models.Request.UserSignUp;
import hotel.com.backend.Models.Response.UserResponse;

public interface UserService {
    UserResponse getUserResById(Long id);
    UserResponse getUseResByUsername(String username);
    Users getUserById(Long id);
    Users getUserByUsername(String username);
    UserResponse saveUser(UserSignUp userSignup);
    UserResponse signIn(UserSignIn userSignIn);
    UserResponse loadAuthUserRes();
    Users getAuthUser();
}
