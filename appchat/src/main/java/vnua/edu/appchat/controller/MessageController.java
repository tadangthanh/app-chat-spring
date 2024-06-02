package vnua.edu.appchat.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vnua.edu.appchat.dto.MessageDTO;
import vnua.edu.appchat.service.IMessageService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/messages")
public class MessageController {
    private final IMessageService messageService;

    @GetMapping
    public List<MessageDTO> getAll() {
        return this.messageService.getAll();
    }

    @GetMapping("/sender/{senderId}")
    public List<MessageDTO> getSentMessages(@PathVariable Long senderId) {
        return this.messageService.findBySenderId(senderId);
    }

    @GetMapping("/receiver/{receiverId}")
    public List<MessageDTO> getReceivedMessages(@PathVariable Long receiverId) {
        return this.messageService.findByReceiverId(receiverId);
    }
    @GetMapping("/sender/{senderId}/receiver/{receiverId}")
    public List<MessageDTO> getMessages(@PathVariable Long senderId, @PathVariable Long receiverId) {
        return this.messageService.findBySenderIdAndReceiverId(senderId, receiverId);
    }

}
