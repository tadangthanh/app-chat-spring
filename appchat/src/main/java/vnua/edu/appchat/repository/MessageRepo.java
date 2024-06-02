package vnua.edu.appchat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vnua.edu.appchat.entity.Message;

import java.util.List;

@Repository
public interface MessageRepo extends JpaRepository<Message, Long>{
    List<Message> findBySenderId(Long senderId);
    List<Message> findByReceiverId(Long receiverId);

}
