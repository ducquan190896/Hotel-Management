package hotel.com.backend.Models.Response;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import hotel.com.backend.Models.Department;
import hotel.com.backend.Models.Enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaskResponse {
    private Long id;
    private String name;
    private String location;
    private String description;
    private Department department;
    private UserResponse creator;
    private UserResponse completor;
    private boolean isCancelled;
    private boolean isCompleted;
    private boolean isUrgent;
    private boolean inProgress;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dateCreated;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime dateUpdated;
}
