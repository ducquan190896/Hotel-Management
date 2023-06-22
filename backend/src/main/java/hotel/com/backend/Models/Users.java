package hotel.com.backend.Models;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import hotel.com.backend.Models.Enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Users")
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @NotBlank(message = "username cannot be blank")
    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @NotBlank(message = "password cannot be blank")
    @Column(name = "password", nullable = false)
    private String password;

    @NotBlank(message = "firstname cannot be blank")
    @Column(name = "firstname", nullable = false)
    private String firstname;

    @NotBlank(message = "surename cannot be blank")
    @Column(name = "surename", nullable = false)
    private String surename;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "roles")
    private List<Role> roles = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department department;

    @JsonIgnore
    @OneToMany(mappedBy = "creator", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Task> tasksCreated = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "completor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Task> tasksCompleted = new ArrayList<>();

    public Users(String username, String password, String firstname, String surename) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.surename = surename;
    }

    public Users( String username, String password, String firstname,String surename, Department department) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.surename = surename;
        this.department = department;
    }

    
   

    
}

