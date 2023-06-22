package  hotel.com.backend.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import  hotel.com.backend.Mapper.UserMapper;
import  hotel.com.backend.Repository.UserRepos;
import  hotel.com.backend.Security.Filter.AuthenticationFilter;
import  hotel.com.backend.Security.Filter.ExceptionFilter;
import  hotel.com.backend.Security.Filter.JWTAuthorizationFilter;
import lombok.AllArgsConstructor;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@Configuration
public class SecurityConfig {
    @Autowired
    CustomAuthenticationManager authenticationManager;
    @Autowired
    UserRepos userRepos;
    @Autowired
    ExceptionFilter exceptionFilter;
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    UserMapper userMapper;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(authenticationManager, userRepos,  userMapper);
        authenticationFilter.setFilterProcessesUrl("/api/login");

        http.csrf().disable()
        .cors()
        .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http
        .authorizeHttpRequests()
        .requestMatchers("/api/users/signIn").permitAll()
        .requestMatchers("/api/users/signup").permitAll()
        .requestMatchers("/api/images/**").permitAll()
        .anyRequest().authenticated()
        .and()
        .addFilterBefore(exceptionFilter, authenticationFilter.getClass())
        .addFilter(authenticationFilter)
        .addFilterAfter(new JWTAuthorizationFilter(), authenticationFilter.getClass());

        http.logout().permitAll()
        .addLogoutHandler((request, response, auth) -> {
            SecurityContextHolder.getContext().setAuthentication(null);
            response.setHeader("Authorization", "");
        });

        return http.build();
    }

    @Bean 
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedMethods("*");
            }
        };
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> {
            web
            .ignoring()
            .requestMatchers( "/api/users/signIn")
            .requestMatchers("socket/**")
            ;
        };
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
