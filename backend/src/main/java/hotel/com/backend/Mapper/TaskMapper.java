package hotel.com.backend.Mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import hotel.com.backend.Models.Task;
import hotel.com.backend.Models.Response.TaskResponse;
import hotel.com.backend.Models.Response.UserResponse;

@Component
public class TaskMapper {
    @Autowired
    UserMapper userMapper;
    @Autowired
    ModelMapper modelMapper;

    public TaskResponse mapTaskResponse(Task task) {
        TaskResponse res = modelMapper.map(task, TaskResponse.class);
        res.setCreator(modelMapper.map(res.getCreator(), UserResponse.class));
        if(res.getCompletor() != null) {
            res.setCompletor(modelMapper.map(res.getCompletor(), UserResponse.class));
        }
        return res;
    }
}
