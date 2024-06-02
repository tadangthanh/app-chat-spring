package vnua.edu.appchat.map;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import vnua.edu.appchat.dto.MessageDTO;
import vnua.edu.appchat.entity.Message;
import vnua.edu.appchat.repository.MessageRepo;
import vnua.edu.appchat.repository.UserRepo;

@RequiredArgsConstructor
@Component
public class MessageMapping implements Mapping<Message, MessageDTO> {
    private final MessageRepo messageRepo;
    private final UserRepo userRepo;
    private final ModelMapper modelMapper;

    @Override
    public Message toEntity(MessageDTO dto) {
        Message message = modelMapper.map(dto, Message.class);
        message.setSender(userRepo.findById(dto.getSenderId()).orElseThrow(() -> new RuntimeException("User not found")));
        message.setReceiver(userRepo.findById(dto.getReceiverId()).orElseThrow(() -> new RuntimeException("User not found")));
        return message;
    }

    @Override
    public MessageDTO toDto(Message entity) {
        MessageDTO messageDTO = modelMapper.map(entity, MessageDTO.class);
        messageDTO.setSenderId(entity.getSender().getId());
        messageDTO.setSenderName(entity.getSender().getUsername());
        messageDTO.setReceiverName(entity.getReceiver().getUsername());
        messageDTO.setReceiverId(entity.getReceiver().getId());
        return messageDTO;
    }

    @Override
    public Message updateFromDTO(MessageDTO dto) {
        Message messageExisting = messageRepo.findById(dto.getId()).orElseThrow(() -> new RuntimeException("Message not found"));
        messageExisting.setMessage(dto.getMessage());
        messageExisting.setSender(userRepo.findById(dto.getSenderId()).orElseThrow(() -> new RuntimeException("User not found")));
        messageExisting.setReceiver(userRepo.findById(dto.getReceiverId()).orElseThrow(() -> new RuntimeException("User not found")));
        return messageExisting;
    }
}
