package vnua.edu.appchat.service;

import vnua.edu.appchat.dto.MessageDTO;

import java.util.List;

public interface IMessageService {
    MessageDTO save(MessageDTO messageDTO);
    List<MessageDTO> getAll();
    List<MessageDTO> findBySenderId(Long senderId);
    List<MessageDTO> findByReceiverId(Long receiverId);
    List<MessageDTO> findBySenderIdAndReceiverId(Long senderId, Long receiverId);
}
