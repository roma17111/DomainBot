package ru.bot.romanmessageapibot.controllers;

import lombok.RequiredArgsConstructor;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StopWatch;
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
@Slf4j
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

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        String text = update.getMessage().getText();
        if (update.hasMessage() && update.getMessage().hasText()) {
            switch (text) {
                case "/start" -> {
                    registerAndCheckUser(update);
                }
                case "/info" -> {
                    getInfo(update);
                }
                default -> {
                    getDefaultCommand(update,text);
                }
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "Domain Bot";
    }


    public void registerAndCheckUser(Update update) {
        long chatId = update.getMessage().getChatId();
        String hello = String.format("\uD83D\uDC4B Привет %s, Это бот по сбору информации о доменах.\n " +
                        "Раз в день бот будет тебе присылать обновлённую информацию о количестве доменов.\n",
                update.getMessage().getChat().getFirstName());
        String info = client.getInfo();
        BotUser user = userRepository.findByChatId(chatId);
        if (user == null) {
            user = BotUser.builder()
                    .lastMessageAt(LocalDateTime.now())
                    .chatId(chatId)
                    .build();
            userRepository.save(user);
            messageService.saveMessage(user, "/start", hello + info);
            messageService.sendMessage(chatId, hello + info);
            log.info("Пользователь " + user + " зарегистрирован. ");
        } else {
            messageService.saveMessage(user, "/start", info);
            messageService.sendMessage(chatId, info);
        }

    }

    private void getInfo(Update update) {
        long chatId = update.getMessage().getChatId();
        BotUser user = userRepository.findByChatId(chatId);
        messageService.sendMessage(chatId, client.getInfo());
        messageService.saveMessage(user,
                "info", client.getInfo());
    }

    private void getDefaultCommand(Update update, String text) {
        long chatId = update.getMessage().getChatId();
        messageService.saveMessage(userRepository.findByChatId(chatId),
                "info", "❌ Я не знаю такую команду\uD83E\uDD15");
        messageService.sendMessage(chatId,"❌ Я не знаю такую команду\uD83E\uDD15");
    }
}
