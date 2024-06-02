package vnua.edu.appchat.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vnua.edu.appchat.dto.MessageDTO;
import vnua.edu.appchat.entity.Message;
import vnua.edu.appchat.entity.User;
import vnua.edu.appchat.map.MessageMapping;
import vnua.edu.appchat.repository.MessageRepo;
import vnua.edu.appchat.repository.UserRepo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements IMessageService {
    private final MessageRepo messageRepo;
    private final MessageMapping messageMapping;
    private final UserRepo userRepo;

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
        User sender = userRepo.findById(senderId).orElseThrow();
        User receiver = userRepo.findById(receiverId).orElseThrow();

        // Kết hợp tất cả tin nhắn từ cả hai sender và receiver
        List<Message> messages = new ArrayList<>(sender.getReceivedMessages());
        messages.addAll(receiver.getReceivedMessages());

        // Sắp xếp danh sách tin nhắn theo ID tăng dần

        return messages.stream()
                .sorted(Comparator.comparing(Message::getId)) // Sắp xếp theo ID tăng dần
                .map(messageMapping::toDto)
                .collect(Collectors.toList());
    }

}
