package hotel.com.backend.Models;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

import hotel.com.backend.Models.Enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "Task")
@Table(name = "task")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "location")
    private String location;

    @Column(name = "description")
    private String description;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id", referencedColumnName = "id")
    private Department department;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "creator_id", referencedColumnName = "id")
    private Users creator;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "completor_id", referencedColumnName = "id")
    private Users completor;

    @Column(name = "is_cancelled")
    private boolean isCancelled;

    @Column(name = "is_completed")
    private boolean isCompleted;

     @Column(name = "is_urgent")
    private boolean isUrgent;

     @Column(name = "in_progress")
    private boolean inProgress;

    @CreationTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "date_created", nullable = false)
    private LocalDateTime date_created;

    @UpdateTimestamp
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    @Column(name = "date_updated", nullable = false)
    private LocalDateTime dateUpdated;

    public Task(String name, String location, String description, Department department, Users creator) {
        this.name = name;
        this.location = location;
        this.description = description;
        this.department = department;
        this.creator = creator;
        this.isCancelled = false;
        this.isCompleted = false;
        this.isUrgent = false;
        this.inProgress = false;
    }


    
}
