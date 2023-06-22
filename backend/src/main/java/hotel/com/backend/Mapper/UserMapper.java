package hotel.com.backend.Mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;

import hotel.com.backend.Models.Users;
import hotel.com.backend.Models.Response.UserResponse;

@Component
public class UserMapper {

    @Autowired
    ModelMapper modelMapper;

    // public UserResponse mapUserToResponse(Users user) {
    //     List<String> images = new ArrayList<>();
    //     if(user.getAvatarUrls().size() > 0) {
    //         images = user.getAvatarUrls();
    //     } 
    //     UserResponse res = new UserResponse(user.getId(), user.getUsername(), user.getFirstname(), user.getSurename(), user.getGender(), user.getSuspended(), user.getDescription(), images, user.getRoles(), user.getBirth(),user.getLongitude(), user.getLatitude(), user.getPreference(), user.getInterest());
    //     // UserResponse res = new UserResponse(user.getId(), user.getUsername(), user.getFirstname(), user.getSurename(), user.getGender(), user.getSuspended(), user.getDescription(), images, user.getRoles(), user.getBirth(),user.getLongitude(), user.getLatitude());
    //     return res;
    // }
    public UserResponse mapUserToResponse(Users user) {
      UserResponse res = modelMapper.map(user, UserResponse.class);
      return res;
    }
}
