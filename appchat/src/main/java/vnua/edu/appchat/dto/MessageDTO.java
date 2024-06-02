package vnua.edu.appchat.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MessageDTO {
    private Long id;
    private String message;
    private String senderName;
    private String receiverName;
    private Long senderId;
    private Long receiverId;
}
