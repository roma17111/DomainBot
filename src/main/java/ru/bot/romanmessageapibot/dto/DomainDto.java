package ru.bot.romanmessageapibot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DomainDto {

    @JsonProperty(value = "domainname")
    String domainName;

    long hotness;
    long price;

    @JsonProperty(value = "x_value")
    long xValue;

    @JsonProperty(value = "yandex_tic")
    long yandexTic;

    long links;
    long visitors;
    String registrar;
    int old;

    @JsonProperty(value = "delete_date")
    Date deleteDate;

    boolean rkn;
    boolean judicial;
    boolean block;


}
