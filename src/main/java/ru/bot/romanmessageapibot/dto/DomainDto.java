package ru.bot.romanmessageapibot.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
    String old;

    @JsonProperty(value = "delete_date")
    LocalDate deleteDate;

    boolean rkn;
    boolean judicial;
    boolean block;


}
