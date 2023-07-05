package hotel.com.backend.Models.Request;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import hotel.com.backend.Models.Department;
import hotel.com.backend.Models.Enums.Role;
import hotel.com.backend.Models.Response.UserResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TestRequest {
    private Long id;
   
}
