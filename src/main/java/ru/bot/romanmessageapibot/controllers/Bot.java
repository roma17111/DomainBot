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
import ru.bot.romanmessageapibot.service.BackOrderClient;

@Service
@RequiredArgsConstructor
public class Bot extends TelegramLongPollingBot {

    private final BackOrderClient client;

    @Autowired
    public Bot(@Value("${bot.token}") String botToken, BackOrderClient client) {
        super(botToken);
        this.client = client;
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            System.out.println(client.readAllDomains());
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
