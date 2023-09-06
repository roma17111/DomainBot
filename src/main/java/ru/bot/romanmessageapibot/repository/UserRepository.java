package ru.bot.romanmessageapibot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bot.romanmessageapibot.entity.BotUser;

@Repository
public interface UserRepository extends JpaRepository<BotUser,Long> {

    BotUser findByChatId(long chatId);
}
