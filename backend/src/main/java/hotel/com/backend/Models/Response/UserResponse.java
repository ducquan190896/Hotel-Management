package hotel.com.backend.Models.Response;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import hotel.com.backend.Models.Enums.GenderType;
import hotel.com.backend.Models.Enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String username;
    private String firstname;
    private String surename;
    private List<Role> roles;
 
 
 
}
