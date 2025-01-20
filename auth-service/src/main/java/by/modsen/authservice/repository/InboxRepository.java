package by.modsen.authservice.repository;

import by.modsen.authservice.entity.InboxMessage;
import by.modsen.authservice.entity.InboxStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InboxRepository extends JpaRepository<InboxMessage, Long> {
    List<InboxMessage> findByStatusNot(InboxStatus status);
}