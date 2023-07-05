package hotel.com.backend.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import hotel.com.backend.Mapper.TaskMapper;
import hotel.com.backend.Models.Request.PickTaskRequest;
import hotel.com.backend.Models.Request.TaskRequest;
import hotel.com.backend.Models.Request.TestRequest;
import hotel.com.backend.Models.Response.TaskResponse;
import hotel.com.backend.Service.TaskService;



@RestController
@RequestMapping("")
public class SocketController {
  
    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;
    @Autowired
    TaskService taskService;
    @Autowired
    TaskMapper taskMapper;
    @Autowired
    SocketService socketService;

    // @MessageMapping("/test")
    // public void testSocket(@Payload String message) {
    //     System.out.println(message + " , successfully test it");
    //     simpMessagingTemplate.convertAndSend("/testTopic", message);
    // }

    @MessageMapping("/task/pick")
    public void pickTask(@Payload PickTaskRequest request) {
        System.out.println(request.getId() + " , successfully test it");
        socketService.authenticateMessageToken(request.getToken());
        TaskResponse res = taskMapper.mapTaskResponse(taskService.takeChargeInTask(request.getId()));
        simpMessagingTemplate.convertAndSend("/tasks/all", res);
    }


}
