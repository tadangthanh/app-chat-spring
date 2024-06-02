package vnua.edu.appchat.map;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import vnua.edu.appchat.dto.UserDTO;
import vnua.edu.appchat.entity.User;
import vnua.edu.appchat.repository.UserRepo;

@Component
@RequiredArgsConstructor
public class UserMapping implements Mapping<User, UserDTO> {
private final UserRepo userRepo;
private final ModelMapper modelMapper;
    @Override
    public User toEntity(UserDTO dto) {
        return modelMapper.map(dto, User.class);
    }

    @Override
    public UserDTO toDto(User entity) {
        return modelMapper.map(entity, UserDTO.class);
    }

    @Override
    public User updateFromDTO(UserDTO dto) {
        User userExisting= userRepo.findById(dto.getId()).orElse(null);
        modelMapper.map(dto, userExisting);
        return userExisting;
    }
}
