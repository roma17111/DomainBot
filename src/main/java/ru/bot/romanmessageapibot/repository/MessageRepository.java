package ru.bot.romanmessageapibot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bot.romanmessageapibot.entity.MessageUser;

@Repository
public interface MessageRepository extends JpaRepository<MessageUser,Long> {
}
