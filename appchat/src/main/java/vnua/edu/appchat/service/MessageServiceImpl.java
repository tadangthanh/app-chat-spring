package vnua.edu.appchat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vnua.edu.appchat.dto.MessageDTO;
import vnua.edu.appchat.entity.Message;
import vnua.edu.appchat.map.MessageMapping;
import vnua.edu.appchat.repository.MessageRepo;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements IMessageService {
    private final MessageRepo messageRepo;
    private final MessageMapping messageMapping;
    @Override
    public MessageDTO save(MessageDTO message) {
        Message messageEntity = messageMapping.toEntity(message);
        messageEntity = messageRepo.saveAndFlush(messageEntity);
        return messageMapping.toDto(messageEntity);
    }

    @Override
    public List<MessageDTO> getAll() {
        List<Message> messages = messageRepo.findAll();
        return messages.stream().map(messageMapping::toDto).toList();
    }

    @Override
    public List<MessageDTO> findBySenderId(Long senderId) {
        List<Message> messages = messageRepo.findBySenderId(senderId);
        return messages.stream().map(messageMapping::toDto).toList();
    }

    @Override
    public List<MessageDTO> findByReceiverId(Long receiverId) {
        List<Message> messages = messageRepo.findByReceiverId(receiverId);
        return messages.stream().map(messageMapping::toDto).toList();
    }

    @Override
    public List<MessageDTO> findBySenderIdAndReceiverId(Long senderId, Long receiverId) {
        List<Message> messages1 = messageRepo.findBySenderIdAndReceiverId(senderId, receiverId);
        List<Message> messages2 = messageRepo.findBySenderIdAndReceiverId(receiverId, senderId);
        messages1.addAll(messages2);
        return messages1.stream().sorted(Comparator.comparing(Message::getId)) // Sắp xếp theo ID tăng dần
                .map(messageMapping::toDto)
                .collect(Collectors.toList());
    }

}
