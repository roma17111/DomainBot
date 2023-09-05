package ru.bot.romanmessageapibot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.bot.romanmessageapibot.dto.DomainDto;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class BackOrderClient {

    public static final String url = "https://backorder.ru/json/?order=desc&expired=1&by=hotness&page=1&items=50";

    public List<DomainDto> readAllDomains() {
        try {
            return Collections.singletonList(new ObjectMapper().readValue(new URL(url), DomainDto.class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
