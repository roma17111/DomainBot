package ru.bot.romanmessageapibot.controllers;

import lombok.RequiredArgsConstructor;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.bot.romanmessageapibot.entity.BotUser;
import ru.bot.romanmessageapibot.repository.UserRepository;
import ru.bot.romanmessageapibot.service.BackOrderClient;
import ru.bot.romanmessageapibot.service.MessageService;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class Bot extends TelegramLongPollingBot {

    private final BackOrderClient client;
    private final MessageService messageService;
    private final UserRepository userRepository;

    @Autowired
    public Bot(@Value("${bot.token}") String botToken,
               BackOrderClient client,
               MessageService messageService,
               UserRepository userRepository) {
        super(botToken);
        this.client = client;
        this.messageService = messageService;
        this.userRepository = userRepository;
    }

    @PostConstruct
    public void init() {
        messageService.registerBot(this);
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        String text = update.getMessage().getText();
        if (update.hasMessage() && update.getMessage().hasText()) {
            client.runCheckingDomains();
            messageService.sendMessage(update.getMessage().getChatId(),client.getInfo());
        }

    }

    @Override
    public String getBotUsername() {
        return "Roman Bot";
    }


    public void registerAndCheckUser(Update update) {
        long chatId = update.getMessage().getChatId();
        String hello = String.format("Привет %s, Это бот по сбору информации о доменах.\n",
                update.getMessage().getChat().getFirstName());
        String info = client.getInfo();
        BotUser user = userRepository.findByChatId(chatId);
        if (user == null) {

            user = BotUser.builder()
                    .lastMessageAt(LocalDateTime.now())
                    .chatId(chatId)
                    .build();
            messageService.saveMessage(user, "/start", hello + info);
            userRepository.save(user);
            messageService.sendMessage(chatId, hello + info);
        } else {
            messageService.saveMessage(user, "/start", hello + info);
            messageService.sendMessage(chatId, hello + info);
        }

    }
}
