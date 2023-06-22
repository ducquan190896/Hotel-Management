package hotel.com.backend.Service.Implementation;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import hotel.com.backend.Exception.BadResultException;
import hotel.com.backend.Exception.EntityExistingException;
import hotel.com.backend.Exception.EntityNotFoundException;
import hotel.com.backend.Mapper.UserMapper;
import hotel.com.backend.Models.Users;
import hotel.com.backend.Models.Enums.Role;
import hotel.com.backend.Models.Request.UserSignIn;
import hotel.com.backend.Models.Request.UserSignUp;
import hotel.com.backend.Models.Response.UserResponse;
import hotel.com.backend.Repository.UserRepos;
import hotel.com.backend.Security.SecurityConstant;
import hotel.com.backend.Service.UserService;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class UserServiceIml implements UserService, UserDetailsService {
    
    @Autowired
    UserRepos userRepos;
    @Autowired
    UserMapper userMapper;
    @Autowired
    HttpServletResponse response;
    

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Optional<Users> entity = userRepos.findByUsername(username);
       if(!entity.isPresent()) {
        throw new EntityNotFoundException("the username not found");
       }
       Users users = entity.get();
       List<SimpleGrantedAuthority> authorities = users.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
       User user = new User(username, users.getPassword(), authorities);
       return user;
    }

    @Override
    public UserResponse getUserResById(Long id) {
        Optional<Users> entity = userRepos.findById(id);
        if(!entity.isPresent()) {
         throw new EntityNotFoundException("the username not found");
        }
        Users users = entity.get();
        UserResponse res = userMapper.mapUserToResponse(users);
        return res;
    }

    @Override
    public UserResponse getUseResByUsername(String username) {
        Optional<Users> entity = userRepos.findByUsername(username);
        if(!entity.isPresent()) {
         throw new EntityNotFoundException("the username not found");
        }
        Users users = entity.get();
        UserResponse res = userMapper.mapUserToResponse(users);
        return res;
    }
    @Override
    public Users getUserByUsername(String username) {
        Optional<Users> entity = userRepos.findByUsername(username);
        if(!entity.isPresent()) {
         throw new EntityNotFoundException("the username not found");
        }
        Users users = entity.get();
        return users;
    }
    @Override
    public Users getUserById(Long id) {
        Optional<Users> entity = userRepos.findById(id);
        if(!entity.isPresent()) {
         throw new EntityNotFoundException("the username not found");
        }
        Users users = entity.get();
        return users;
    }

    @Override
    public UserResponse saveUser(UserSignUp signUp) {
        Optional<Users> entity = userRepos.findByUsername(signUp.getUsername());
        if(entity.isPresent()) {
         throw new EntityExistingException("the username exists");
        }
        Users user = new Users(signUp.getUsername(), new BCryptPasswordEncoder().encode(signUp.getPassword()), signUp.getFirstname(), signUp.getSurename());
        user.getRoles().add(Role.USER);
        userRepos.save(user);

        List<String> claims = user.getRoles().stream().map(auth -> auth.getName()).collect(Collectors.toList());
        String token = JWT.create()
        .withSubject(user.getUsername())
        .withClaim("authorities", claims)
        .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstant.expire_time))
        .sign(Algorithm.HMAC512(SecurityConstant.private_key));
        
      
        response.setStatus(HttpServletResponse.SC_OK); 
        response.setHeader("Authorization", SecurityConstant.authorization + token);

        return userMapper.mapUserToResponse(user);
    
    }

    @Override
    public UserResponse signIn(UserSignIn userSignIn) {
       
        Optional<Users> entity = userRepos.findByUsername(userSignIn.getUsername());
        if(!entity.isPresent()) {
           throw new EntityNotFoundException("the username not found");
        }
       Users user = entity.get();
       if(!new BCryptPasswordEncoder().matches(userSignIn.getPassword(), user.getPassword())) {
        throw new BadCredentialsException("the password is wrong");
       }
       

       List<String> claims = user.getRoles().stream().map(auth -> auth.getName()).collect(Collectors.toList());
        String token = JWT.create()
        .withSubject(userSignIn.getUsername())
        .withClaim("authorities", claims)
        .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstant.expire_time))
        .sign(Algorithm.HMAC512(SecurityConstant.private_key));
        
      
        response.setStatus(HttpServletResponse.SC_OK); 
        response.setHeader("Authorization", SecurityConstant.authorization + token);
        
      
        UserResponse res = userMapper.mapUserToResponse(user);
        System.out.println(res);
        return res;
    
    }

   

    @Override
    public Users getAuthUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Users user = getUserByUsername(username);
        return user;
    }
    @Override
    public UserResponse loadAuthUserRes() {
        Users authUser = getAuthUser();
        UserResponse res = userMapper.mapUserToResponse(authUser);
        return res;
        
    }
}
