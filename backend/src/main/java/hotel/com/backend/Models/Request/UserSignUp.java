package hotel.com.backend.Models.Request;

import java.time.LocalDate;
import java.util.List;

import hotel.com.backend.Models.Enums.GenderType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSignUp {
    private String username;
    private String password;
    private String firstname;
    private String surename;


    
    
}
