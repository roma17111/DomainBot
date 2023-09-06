package ru.bot.romanmessageapibot.controllers;

import lombok.RequiredArgsConstructor;

import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.bot.romanmessageapibot.repository.UserRepository;
import ru.bot.romanmessageapibot.service.BackOrderClient;
import ru.bot.romanmessageapibot.service.MessageBotService;
import ru.bot.romanmessageapibot.service.MessageService;

import javax.annotation.PostConstruct;

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
        messageService.registerBot();
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        String text = update.getMessage().getText();
        if (update.hasMessage() && update.getMessage().hasText()) {
            client.deleteAllDomains();
            client.runCheckingDomains();
            SendMessage message = SendMessage.builder()
                    .chatId(update.getMessage().getChatId())
                    .text("Hello world")
                    .build();
            execute(message);
        }

    }

    @Override
    public String getBotUsername() {
        return "Roman Bot";
    }

}
