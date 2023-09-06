package ru.bot.romanmessageapibot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.bot.romanmessageapibot.entity.BotUser;
import ru.bot.romanmessageapibot.entity.MessageUser;
import ru.bot.romanmessageapibot.repository.MessageRepository;
import ru.bot.romanmessageapibot.repository.UserRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageService {

    private final BackOrderClient client;
    private final UserRepository userRepository;
    private final MessageBotService messageBotService;
    private final MessageRepository messageRepository;


    private TelegramLongPollingBot bot;


    public void registerBot() {
        this.bot = bot;
    }

    public void sendMessage(long chatId, String text) {
        SendMessage message = SendMessage.builder()
                .chatId(chatId)
                .text("Hello world")
                .build();
        try {
            bot.execute(message);
        } catch (TelegramApiException e) {
            log.error(e.getMessage(),e);
        }
    }

    @Scheduled(cron = "0 00 03 * * *")
    @Async
    public void updateDomains() {
        client.deleteAllDomains();
        client.runCheckingDomains();
    }

    @Scheduled(cron = "0 00 07 * * *")
    @Async
    public void sendInfoToUser() {
        userRepository.findAll().forEach(el -> {
            el.setLastMessageAt(LocalDateTime.now());
            saveMessage(el,"", client.getInfo());
            userRepository.save(el);
           sendMessage(el.getChatId(), client.getInfo());
        });
    }

    public void saveMessage(BotUser user, String request, String response) {
        MessageUser message = MessageUser.builder()
                .user(user)
                .request(request)
                .response(response)
                .build();
        messageRepository.save(message);

    }
}
