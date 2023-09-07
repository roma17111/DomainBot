package ru.bot.romanmessageapibot.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;

import javax.persistence.Column;

@Component
public class BotClient extends DefaultAbsSender {
    protected BotClient(@Value("${bot.token}") String botToken) {
        super(new DefaultBotOptions(), botToken);
    }
}
