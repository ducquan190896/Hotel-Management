package  hotel.com.backend.Security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import  hotel.com.backend.Exception.BadResultException;
import  hotel.com.backend.Exception.EntityNotFoundException;
import  hotel.com.backend.Models.Users;
import  hotel.com.backend.Repository.UserRepos;
import  hotel.com.backend.Service.Implementation.UserServiceIml;

@Component
public class CustomAuthenticationManager implements AuthenticationManager {
    
    @Autowired
    UserServiceIml userServiceIml;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        UserDetails users = userServiceIml.loadUserByUsername(username);

       if(!new BCryptPasswordEncoder().matches(password, users.getPassword())) {
        throw new BadCredentialsException("the password is wrong");
       }
       Authentication authentication2 = new UsernamePasswordAuthenticationToken(users, users.getPassword(), users.getAuthorities());

       return authentication2;
    }
}
