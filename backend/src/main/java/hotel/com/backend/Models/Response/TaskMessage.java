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
public class TaskMessage {
    private Long id;
    private String message;
}
