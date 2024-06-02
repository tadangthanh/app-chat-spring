package vnua.edu.appchat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import vnua.edu.appchat.dto.MessageDTO;
import vnua.edu.appchat.service.IMessageService;

@Controller
@RequiredArgsConstructor
public class ChatController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final IMessageService messageService;


    @MessageMapping("/private-message")
    public MessageDTO receiverPrivateMessage(@Payload MessageDTO message) {
        MessageDTO messageDTO = this.messageService.save(message);
        simpMessagingTemplate.convertAndSendToUser(String.valueOf(message.getReceiverId()), "/private", messageDTO); // /user/{username}/private
        return messageDTO;
    }
}
