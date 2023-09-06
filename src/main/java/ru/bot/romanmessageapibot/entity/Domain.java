package ru.bot.romanmessageapibot.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import javax.persistence.*;
import java.util.Date;

//@Entity
@Table(name = "daily_domains")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Domain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "domain_id")
    long domainId;

    @Column(name = "domain_name", nullable = false, length = 1000)
    String domainName;

    @Column(name = "hotness", nullable = false)
    long hotness;

    @Column(name = "price", nullable = false)
    long price;

    @Column(name = "x_value", nullable = false)
    long xValue;

    @Column(name = "yandex_tic", nullable = false)
    long yandexTic;

    @Column(name = "links", nullable = false)
    long links;
    @Column(name = "visitors", nullable = false)
    long visitors;
    @Column(name = "registrar", nullable = false)
    String registrar;
    @Column(name = "old", nullable = false)
    int old;

    @Column(name = "delete_date", nullable = false)
    Date deleteDate;

    @Column(name = "rkn", nullable = false)
    boolean rkn;

    @Column(name = "judicial", nullable = false)
    boolean judicial;

    @Column(name = "block", nullable = false)
    boolean block;
}
