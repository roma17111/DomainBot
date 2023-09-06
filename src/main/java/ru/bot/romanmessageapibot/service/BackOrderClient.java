package ru.bot.romanmessageapibot.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.bot.romanmessageapibot.dto.DomainDto;
import ru.bot.romanmessageapibot.exceptions.IncorrectUrlException;

import java.io.IOException;
import java.net.URL;
import java.util.List;

@Service
@Slf4j
public class BackOrderClient {

    public static final String url = "https://backorder.ru/json/?order=desc&expired=1&by=hotness&page=1&items=50";

    public List<DomainDto> readAllDomains() {
        try {
            return new ObjectMapper().readValue(new URL(url), new TypeReference<List<DomainDto>>() {});
        } catch (IOException e) {
            log.error(e.getMessage(),e);
            log.error("Ошибка парсинка url адреса");
            throw new IncorrectUrlException("Ошибка парсинга url адреса \n" + e.getMessage());
        }
    }
}
